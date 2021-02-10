package com.sushmoyr.notes.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.sushmoyr.notes.MainActivity
import com.sushmoyr.notes.R
import com.sushmoyr.notes.databinding.FragmentUpdateBinding
import com.sushmoyr.notes.model.Note
import com.sushmoyr.notes.viewModel.NoteViewModel

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args : UpdateFragmentArgs by navArgs()
    private lateinit var currentNote : Note
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel

        currentNote = args.notes
        binding.etNoteTitleUpdate.setText(currentNote.title)
        binding.etNoteBodyUpdate.setText(currentNote.body)

        binding.fabDone.setOnClickListener {
            val title = binding.etNoteTitleUpdate.text.toString().trim()
            val body = binding.etNoteBodyUpdate.text.toString().trim()

            if(title.isNotEmpty())
            {
                val note = Note(currentNote.id, title, body)
                noteViewModel.updateNote(note)
                Toast.makeText(requireContext(), "Note Updated", Toast.LENGTH_SHORT).show()
                it.findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
            }
            else
            {
                Toast.makeText(requireContext(), "Title can't be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        menu.clear()
        inflater.inflate(R.menu.update_note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_delete ->{
                deleteNote()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote()
    {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note?")
            setMessage("Are You sure want to delete this note?")
            setPositiveButton("DELETE"){_,_ ->
                noteViewModel.deleteNote(currentNote)
                view?.findNavController()?.navigate(R.id.action_updateFragment_to_homeFragment)
            }
            setNegativeButton("Cancel",null)
        }.create().show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}