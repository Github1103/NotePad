package com.example.notepad

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.text.method.BaseKeyListener
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.content.contentValuesOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_edit_note.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class EditNote : AppCompatActivity(){

    private var mode = 0 // add
    private var notesId = ""
    private val imageList = ArrayList<Int>()
    private var BKG = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)
        //初始化委托模式
        initSelect()
        //初始化背景
        initImage()
        //如果是从修改模式启动该activity时，获取要修改的内容从Intent
        initIntent()
        //设置背景，设置透明度
        setBackground(BKG)
        //初始化背景选择
        recyclerviewInit(imageList)
        //输入框获取焦点
        setFocus()
        //保存摁扭
        saveNote()
        //修改背景
        ChangeBk()
    }

    private fun initImage(){
        imageList.add(R.drawable.background3)
        imageList.add(R.drawable.background1)
        imageList.add(R.drawable.background2)
        imageList.add(R.drawable.background4)
    }

    private fun initIntent(){
        val note = intent.getStringExtra("note")
        val noteTitle = intent.getStringExtra("noteTitle")
        notesId = intent.getStringExtra("noteId").toString()
        if (note != null) {
            EditNote.setText(note)
            mode = 1 // modify
        }
        if (noteTitle != "") EditTitle.setText(noteTitle)
        BKG = intent.getStringExtra("background")?.toInt() ?: imageList[0]
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setBackground(BKG : Int){
        edit_main.background = getDrawable(BKG)
        edit_main.background.alpha = 100
    }

    private fun recyclerviewInit(List:ArrayList<Int>){
        val layoutManager = LinearLayoutManager(this)
        backgroundList.layoutManager = layoutManager
        val adapter = ImageAdapter(List)
        backgroundList.adapter = adapter
    }

    private fun setFocus(){
        EditNote.isFocusable = true
        EditNote.isFocusableInTouchMode = true
        EditNote.requestFocus()
    }

    private fun saveNote(){
        save.setOnClickListener{
            var titleInput = EditTitle.text.toString()
            val noteInput = EditNote.text.toString()
            if (mode == 0) {
                if (noteInput != "") {
                    if (titleInput == "") {
                        titleInput = if (noteInput.length >= 5) {
                            noteInput.substring(0, 5)
                        } else {
                            noteInput
                        }
                    }
                    val createDate = Date().time
                    val values = contentValuesOf(NotePad.column_name_title to titleInput,
                            NotePad.column_name_note to noteInput,
                            NotePad.column_name_create_date to createDate,
                            NotePad.column_name_modification_date to createDate,
                            NotePad.column_name_background to BKG
                    )
                    contentResolver.insert(NotePad.content_uri, values)
                }
            }else{
                val modificationDate = Date().time
                val values = contentValuesOf(NotePad.column_name_title to titleInput,
                        NotePad.column_name_note to noteInput,
                        NotePad.column_name_modification_date to modificationDate,
                        NotePad.column_name_background to BKG
                )
                contentResolver.update(NotePad.content_uri, values, " id = ?", arrayOf(notesId))
            }
            finish()
        }
    }

    private fun ChangeBk(){
        changebk.setOnClickListener() {
            drawerLayout.openDrawer(GravityCompat.START)
            hideKeyboard()
        }
    }

    //收起键盘

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun initSelect() {
        ImageAdapter.setSelectItem(object :ImageAdapter.SelectItem {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun select(view: View?, position: Int) {
                BKG = imageList[position]
                setBackground(BKG)
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        })
    }

}

