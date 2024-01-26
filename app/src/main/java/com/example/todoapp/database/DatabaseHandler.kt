package com.example.todoapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todoapp.Models.notesModel

class DatabaseHandler(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME ="NotesDatabase"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NOTES = "notesTable"

        private const val KEY_ID ="id"
        private const val KEY_TITLE ="title"
        private const val KEY_DESCRIPTION ="description"

    }

    override fun onCreate(db: SQLiteDatabase?) {

      val createTableQuery =
          "CREATE TABLE $TABLE_NOTES($KEY_ID INTEGER PRIMARY KEY," +
                  "$KEY_TITLE TEXT, $KEY_DESCRIPTION TEXT )"

        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVirsion: Int, newVersion: Int) {

       val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NOTES"
       db?.execSQL(dropTableQuery)
       onCreate(db)

    }

    fun addNotes(Notes:notesModel){

        val db = writableDatabase

        val values = ContentValues().apply {
            put(KEY_TITLE,Notes.title)
            put(KEY_DESCRIPTION,Notes.description)
        }

        db.insert(TABLE_NOTES,null,values)
        db.close()


    }

    fun getAllNotes():List<notesModel>{

        val notesList = mutableListOf<notesModel>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NOTES"
        val cursor = db.rawQuery(query,null)

        while(cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION))

            val note = notesModel(id,title,description)
            notesList.add(note)
        }
        cursor.close()
        db.close()
        return notesList
    }

    fun updateNotes(Notes:notesModel){

        val db = writableDatabase

        val values = ContentValues().apply {
            put(KEY_TITLE,Notes.title)
            put(KEY_DESCRIPTION,Notes.description)
        }

        val whereClause = "$KEY_ID = ?"
        val whereArgs = arrayOf(Notes.id.toString())
        db.update(TABLE_NOTES,values,whereClause,whereArgs)
        db.close()
    }



    fun GetNoteById(noteId:Int):notesModel{

        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NOTES WHERE $KEY_ID =$noteId "
        val cursor = db.rawQuery(query,null)

        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION))

        cursor.close()
        db.close()
        return notesModel(id,title,description)

    }

    fun DeleteNote(noteId:Int){
        val db = writableDatabase
        val whereClause ="$KEY_ID = ?"
        val whereArgs = arrayOf(noteId.toString())

        db.delete(TABLE_NOTES,whereClause,whereArgs)
        db.close()

    }


}