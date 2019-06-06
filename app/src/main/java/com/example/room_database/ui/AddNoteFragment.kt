package com.example.room_database.ui


import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.navigation.Navigation

import com.example.room_database.R
import com.example.room_database.database.Note
import com.example.room_database.database.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.launch

class AddNoteFragment : BaseFragment() {

    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //for arguments pass
        arguments?.let {
            note = AddNoteFragmentArgs.fromBundle(it).note
            edit_text_title.setText(note?.title)
            edit_text_note.setText(note?.note)
        }
        floating_save.setOnClickListener { view ->
            val note_title = edit_text_title.text.toString().trim()
            val note_body = edit_text_note.text.toString().trim()

            if (note_title.isEmpty()) {
                edit_text_title.error = "title required"
                edit_text_title.requestFocus()
                return@setOnClickListener
            }

            if (note_body.isEmpty()) {
                edit_text_note.error = "note required"
                edit_text_note.requestFocus()
                return@setOnClickListener
            }

            //with coroutine
            launch {

                //without onClick to update
                /*val note = Note(note_title, note_body)
                context?.let {
                    NoteDatabase(it).getNoteDao().addNote(note)
                    it.toast("Note Saved")

                    val action = AddNoteFragmentDirections.actionSaveNote()
                    Navigation.findNavController(view).navigate(action)
                    }*/

                //with onClick to update
                context?.let {
                    val notes = Note(note_title, note_body)

                    if (note == null) {
                        NoteDatabase(it).getNoteDao().addNote(notes)
                        it.toast("Note Saved")
                    } else {
                        notes.id = note!!.id
                        NoteDatabase(it).getNoteDao().updateNote(notes)
                        it.toast("Note Updated")
                    }

                    val action = AddNoteFragmentDirections.actionSaveNote()
                    Navigation.findNavController(view).navigate(action)
                }

                //without coroutine
                /*val note = Note(note_title, note_body)
                  saveNote(note)*/
            }
        }
    }

    private fun deleteNote(){
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes"){ _, _ ->
                launch {
                    NoteDatabase(context).getNoteDao().deleteNote(note!!)
                    val action = AddNoteFragmentDirections.actionSaveNote()
                    Navigation.findNavController(view!!).navigate(action)
                }
            }
            setNegativeButton("No"){ _, _ ->

            }
        }.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete -> if(note != null) deleteNote() else context?.toast("Cannot Deleted")
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

        //without coroutine scope
        /*private fun saveNote(note: Note){
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
    }*/
}
