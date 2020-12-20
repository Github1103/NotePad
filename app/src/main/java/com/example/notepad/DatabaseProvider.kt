package com.example.notepad

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract

class DatabaseProvider : ContentProvider() {

    private val tag = "DatabaseProvider"

    private val noteDir = 0
    private val noteItem = 1
    private var dbHelper : MyDatabaseHelper? = null

    private val uriMatcher by lazy {
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
        matcher.addURI(NotePad.authority,"notes",noteDir)
        matcher.addURI(NotePad.authority,"notes/#",noteItem)
        matcher
    }

    override fun onCreate() = context?.let {
        dbHelper = MyDatabaseHelper(it,"note_pad.db",1)
        true
    } ?: false

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?) =
        dbHelper?.let {
        val db = it.readableDatabase
        val cursor = when(uriMatcher.match(uri)){
            noteDir -> db.query(NotePad.table_name, projection, selection, selectionArgs, null, null, sortOrder)
            noteItem -> {
                val noteid = uri.pathSegments[1]
                db.query("notes", projection, "id = ?", arrayOf(noteid), null, null, sortOrder)
            }
            else -> null
        }
        cursor
    }

    override fun getType(uri: Uri) = when (uriMatcher.match(uri)){
        noteDir -> NotePad.content_type
        noteItem -> NotePad.content_item_type
        else -> null
    }

    override fun insert(uri: Uri, values: ContentValues?) = dbHelper?.let {
        val db = it.writableDatabase
        val uriReturn = when(uriMatcher.match(uri)){
            noteDir, noteItem ->{
                val newNoteId = db.insert("notes", null, values)
                Uri.parse("content://${NotePad.authority}/notes/$newNoteId")
            }
            else -> null
        }
        uriReturn
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?) = dbHelper?.let {
        val db = it.writableDatabase
        val deletedRows = when (uriMatcher.match(uri)){
            noteDir -> db.delete("notes", selection, selectionArgs)
            noteItem -> {
                val noteid = uri.pathSegments[1]
                db.delete("notes", "id = ?", arrayOf(noteid))
            }
            else -> 0
        }
        deletedRows
    } ?: 0

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?)=
        dbHelper?.let {
        val db = it.writableDatabase
        val updatedRows = when (uriMatcher.match(uri)){
            noteDir -> db.update("notes", values, selection, selectionArgs)
            noteItem -> {
                val noteid = uri.pathSegments[1]
                db.update("notes", values, "id = ?", arrayOf(noteid))
            }
            else -> 0
        }
        updatedRows
    } ?: 0

}