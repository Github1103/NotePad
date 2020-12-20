package com.example.notepad

import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(){

    private var searchList = ArrayList<Note>()

    private var noteList = NotePad.noteList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //初始化Note
        initNotes()
        //通过列表显示瀑布流
        setRecyclerViewShow(noteList)
        //初始化查询摁扭
        searchAction()
        //悬浮摁扭插入
        fabAction()
        //数据删除
        deleteData()
    }

    private fun fabAction(){
        //摁扭点击后，跳转到EditNote的activity，进入编辑界面
        fab.setOnClickListener{
            val intent = Intent(this, EditNote::class.java)
            startActivity(intent)
        }
    }

    private fun initNotes(){
        //调试
        Log.d("main","initNotes")
        //获取从数据库中取数据，存储到noteList中
        contentResolver.query(NotePad.content_uri,
            null,
            null,
            null,
            null)?.apply {
            //getData方法将会重复所以单独封装一个
           getData(this,noteList)
        }
    }

    private fun setRecyclerViewShow(List: ArrayList<Note>){
        //调试
        Log.d("main","setShow")
        //初始化recyclerview，通过List创建瀑布流
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        val adapter = NoteAdapter(List)
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        /**
         * 当编辑界面保存返回的时候调用此方法重新加载列表
         */
        super.onStart()
        noteList = arrayListOf()
        initNotes()
        setRecyclerViewShow(noteList)
    }

    private fun searchAction() {
        /**
         * 当点击键盘上的搜索的时候将搜索框中的值取下，然后通过 “where note like %$key% and title like %$key%” 进行模糊查询
         * 将查找的数据放入seachList中，
         * 然后初始化Recyclerview去展示数据，
         * 当key为空的时候，使用原noteList
         */
        searchBtn.setOnEditorActionListener(TextView.OnEditorActionListener() { _, i: Int, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                val key = searchBtn.text.toString()
                if (key == "") {
                    setRecyclerViewShow(noteList)
                } else {
                    searchList = arrayListOf()
                    contentResolver.query(NotePad.content_uri,
                        null,
                        " note like ? or title like ?",
                        arrayOf("%$key%", "%$key%"),
                        null)?.apply {
                        getData(this,searchList)
                    }
                    if (searchList.isEmpty()) {
                        Toast.makeText(this, " There is no match!", Toast.LENGTH_SHORT).show()
                    }
                    setRecyclerViewShow(searchList)
                }
            }
            return@OnEditorActionListener true
        })
    }

    private fun deleteData(){
        /**
         * 删除数据，此处使用委托模式。adapter获取到position和view，然后对界面进行删除和更新。
         */
        NoteAdapter.setSelectItem(object : NoteAdapter.SelectItem {
            override fun select(view: View?, position: Int) {
                Log.d("main","select")
                val note = noteList[position]
                contentResolver.delete(
                    NotePad.content_uri,
                    "id = ?",
                    arrayOf("${note.id}")
                )
                noteList = arrayListOf()
                initNotes()
                setRecyclerViewShow(noteList)
            }
        })
    }

    private fun getData(cursor:Cursor,List: ArrayList<Note>){
        //获取cursor和列表，用于初始化列表
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(NotePad.id))
            val noteTitle = cursor.getString(cursor.getColumnIndex(NotePad.column_name_title))
            val note = cursor.getString(cursor.getColumnIndex(NotePad.column_name_note))
            val createDate =
                cursor.getString(cursor.getColumnIndex(NotePad.column_name_create_date)).toLong()
            val modificationDate =
                cursor.getString(cursor.getColumnIndex(NotePad.column_name_modification_date)).toLong()
            val backgroud = cursor.getString(cursor.getColumnIndex(NotePad.column_name_background)).toInt()
            val create_date = Date(createDate)
            val modification_date = Date(modificationDate)
            List.add(Note(id, noteTitle, note, create_date, modification_date,backgroud))
        }
    }
}