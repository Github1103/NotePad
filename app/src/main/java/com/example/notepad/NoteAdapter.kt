package com.example.notepad

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat


class NoteAdapter(private val noteList: List<Note>) :
        RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private val sdf = SimpleDateFormat("yyyy-MM-dd")

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val noteTitle: TextView = view.findViewById(R.id.title)
        val note: TextView = view.findViewById(R.id.note)
        val createDate: TextView = view.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val note = noteList[position]
            val intent = Intent(parent.context, EditNote::class.java)
            intent.putExtra("noteTitle", note.title)
            intent.putExtra("note", note.note)
            intent.putExtra("noteId", note.id.toString())
            intent.putExtra("background",note.background.toString())
            parent.context.startActivity(intent)
        }
        viewHolder.itemView.setOnLongClickListener {
            AlertDialog.Builder(parent.context).apply {
                setMessage("确认删除")
                setCancelable(false)
                setPositiveButton("是") { _, _ ->
                    val position = viewHolder.adapterPosition
                    mSelectItem.select(view,position)
                }
                setNegativeButton("否") { _, _ ->
                }
                show()
            }
            return@setOnLongClickListener true
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList[position]
        holder.noteTitle.text = note.title
        holder.note.text = note.note
        holder.createDate.text = sdf.format(note.createDate)
    }

    override fun getItemCount() = noteList.size

    //委托模式
    interface SelectItem {
                /**
                 * 在活动中定义的方法
                 * @param view view对象
                 * @param position item的位置
                 */
                fun select(view: View?, position: Int)
            }

    companion object {
        private lateinit var mSelectItem : SelectItem

        fun setSelectItem(selectItem: SelectItem) {
            mSelectItem = selectItem
        }
    }

}