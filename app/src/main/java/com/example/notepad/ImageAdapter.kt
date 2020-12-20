package com.example.notepad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView


class ImageAdapter(private val ImageList: List<Int>) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val bkImage : ImageView = view.findViewById(R.id.background_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        /**
         * 选择背景图片，
         * 通过点击事件监听点击的背景的position，
         * 然后通过委托模式在前端修改背景
         */
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.background_list,parent,false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener(){
            val position = viewHolder.adapterPosition
            mSelectItem.select(view, position)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bkImage.setImageResource(ImageList[position])
    }

    override fun getItemCount() = ImageList.size

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