package com.example.todoapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.Adapters.NotesAdapter
import com.example.todoapp.R
import com.example.todoapp.database.DatabaseHandler
import com.example.todoapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    private lateinit var db:DatabaseHandler
    private lateinit var notesAdapter:NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = DatabaseHandler(context = this)

        notesAdapter = NotesAdapter(this,db.getAllNotes())
        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        binding.rvNotes.adapter = notesAdapter


        setSupportActionBar(binding.toolbarNotes)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false)
            actionBar.title = "Notes"
        }


       binding.fabBtn.setOnClickListener{
           startActivity(Intent(this, MakeNotes::class.java))
       }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.notes_toolbar_menu,menu)
        return  true
    }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.logout ->{
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, SignUpActivity::class.java))
                finish()
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show(

                )
                true
            }

            else -> {super.onOptionsItemSelected(item)}
        }
    }



    override fun onResume() {
        super.onResume()
        notesAdapter.notifyData(db.getAllNotes())
    }

 }
