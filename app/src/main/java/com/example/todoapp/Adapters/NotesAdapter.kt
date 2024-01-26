package com.example.todoapp.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.Activities.NotesEditActivity
import com.example.todoapp.Models.notesModel
import com.example.todoapp.R

class NotesAdapter(
    private val context: Context,
    private var notes: List<notesModel>,
               ): RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){


    class NoteViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvDescription  = itemView.findViewById<TextView>(R.id.tvDescription)
        val cardview = itemView.findViewById<CardView>(R.id.rv_card_view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.rv_notes,parent,false)
        return NoteViewHolder(view)
    }



    override fun getItemCount(): Int {
      return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val note = notes[position]

        holder.tvTitle.text = note.title
        holder.tvDescription.text = note.description

        holder.cardview.setOnClickListener {
            val intent = Intent(holder.itemView.context,NotesEditActivity::class.java).apply {
                putExtra(NOTE_ID,note.id)
            }

            holder.itemView.context.startActivity(intent)
        }


    }

    fun notifyData(newNotes:List<notesModel>){
        notes = newNotes
        notifyDataSetChanged()
    }

    companion object{
        var NOTE_ID = "note_id"
    }

}