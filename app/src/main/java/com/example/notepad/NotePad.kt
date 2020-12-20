package com.example.notepad

import android.net.Uri
import java.text.SimpleDateFormat

object NotePad {
    const val authority = "com.example.notepad.provider"
    const val table_name = "notes"
    const val id = "id"
    private const val scheme = "content://"
    private const val path_notes = "/notes"
    val content_uri = Uri.parse(scheme + authority + path_notes)
    const val content_type = "vnd.android.cursor.dir/vnd.com.example.NotePad.provider.note"
    const val content_item_type = "vnd.android.cursor.item/vnd.com.example.NotePad.provider.note"
    const val column_name_title = "title"
    const val column_name_note = "note"
    const val column_name_create_date = "created"
    const val column_name_modification_date = "modified"
    const val column_name_background = "background"
    val noteList = ArrayList<Note>()
}