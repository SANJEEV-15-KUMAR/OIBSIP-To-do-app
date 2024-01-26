package com.example.todoapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.todoapp.Adapters.NotesAdapter.Companion.NOTE_ID
import com.example.todoapp.Models.notesModel
import com.example.todoapp.R
import com.example.todoapp.database.DatabaseHandler
import com.example.todoapp.databinding.ActivityNotesEditBinding

class NotesEditActivity : AppCompatActivity() {
    
    private lateinit var binding:ActivityNotesEditBinding

    private lateinit var db: DatabaseHandler
    private var noteId:Int = -1
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHandler(context = this)


        setSupportActionBar(binding.toolbarNotesEdit)
        val actionBar = supportActionBar
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        actionBar.title ="Edit Note"

        binding.toolbarNotesEdit.setNavigationOnClickListener { onBackPressed() }



        noteId = intent.getIntExtra(NOTE_ID,-1)
        if (noteId==-1){
            finish()
            return
        }

        val note = db.GetNoteById(noteId)
        binding.etTitleEdit.setText(note.title)
        binding.etDescriptionEdit.setText(note.description)

        
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.notes_edit,menu)
        return  true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.save_edit ->{
                btnSaveEdit()
                true
            }
            
            R.id.delete_edit ->{
                db.DeleteNote(noteId)
                finish()
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                true
            }

            else -> {super.onOptionsItemSelected(item)}
        }
    }

    private fun btnSaveEdit(){

        val newTitle = binding.etTitleEdit.text.toString()
        val newDescription = binding.etDescriptionEdit.text.toString()
        val updateNote = notesModel(noteId,newTitle,newDescription)
        db.updateNotes(updateNote)
        finish()

        Toast.makeText(this, "Updated ", Toast.LENGTH_SHORT).show()
    }

}