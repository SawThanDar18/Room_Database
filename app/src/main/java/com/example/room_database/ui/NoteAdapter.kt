package com.example.room_database.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.room_database.R
import com.example.room_database.database.Note
import kotlinx.android.synthetic.main.note_layout.view.*

class NoteAdapter(val noteList : List<Note>) : RecyclerView.Adapter<NoteAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.note_layout, parent, false)
        )
    }

    override fun getItemCount() = noteList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.text_view_title.text = noteList[position].title
        holder.view.text_view_note.text = noteList[position].note

        holder.view.setOnClickListener {
            val action = HomeFragmentDirections.actionAddNote()
            action.note = noteList[position]
            Navigation.findNavController(it).navigate(action)
        }
    }

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}