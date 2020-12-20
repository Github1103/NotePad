package com.example.notepad

import java.util.*

data class Note(val id: Int,
                val title : String,
                val note : String ,
                val createDate : Date,
                val modificationDate : Date,
                val background : Int)