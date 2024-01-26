package com.example.todoapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.todoapp.Models.notesModel
import com.example.todoapp.R
import com.example.todoapp.database.DatabaseHandler
import com.example.todoapp.databinding.ActivityMakeNotesBinding

class MakeNotes : AppCompatActivity() {

    private lateinit var binding: ActivityMakeNotesBinding

    private lateinit var db:DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHandler(context = this)



        setSupportActionBar(binding.toolbarNotes)
        val actionBar = supportActionBar
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar!!.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        actionBar.title ="New note"

    binding.toolbarNotes.setNavigationOnClickListener { onBackPressed() }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_toolbar,menu)
        return  true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.save ->{

                saveNotes()
                Toast.makeText(this, "Note is Saved", Toast.LENGTH_SHORT).show()
                true
            }

            else -> {super.onOptionsItemSelected(item)}
        }
    }

    private fun saveNotes(){
        val title = binding.etTitle.text.toString()
        val description = binding.etDescription.text.toString()
        val note = notesModel(0,title, description)
        db.addNotes(note)
        finish()
}



}