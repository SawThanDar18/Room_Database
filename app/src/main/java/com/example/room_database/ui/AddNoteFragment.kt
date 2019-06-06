package com.example.room_database.ui


import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.example.room_database.R
import com.example.room_database.database.Note
import com.example.room_database.database.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_note.*

class AddNoteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        floating_save.setOnClickListener {
            val note_title = edit_text_title.text.toString().trim()
            val note_body = edit_text_note.text.toString().trim()

            if(note_title.isEmpty()){
                edit_text_title.error = "title required"
                edit_text_title.requestFocus()
                return@setOnClickListener
            }

            if(note_body.isEmpty()){
                edit_text_note.error = "note required"
                edit_text_note.requestFocus()
                return@setOnClickListener
            }

            val note = Note(note_title, note_body)
            saveNote(note)
        }
    }

    private fun saveNote(note: Note){
        class SaveNote : AsyncTask<Void, Void, Void>(){

            override fun doInBackground(vararg params: Void?): Void? {
                NoteDatabase(activity!!).getNoteDao().addNote(note)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)

                Toast.makeText(activity, "Note Saved", Toast.LENGTH_LONG).show()
            }
        }

        SaveNote().execute()
    }
}
