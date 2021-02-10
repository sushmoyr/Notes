package com.sushmoyr.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.sushmoyr.notes.database.NoteDatabase
import com.sushmoyr.notes.databinding.ActivityMainBinding
import com.sushmoyr.notes.repositiory.Repository
import com.sushmoyr.notes.viewModel.NoteViewModel
import com.sushmoyr.notes.viewModel.NoteViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var noteViewModel: NoteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setUpViewModel()

    }

    private fun setUpViewModel()
    {
        val noteRepository = Repository(NoteDatabase(this))

        val viewModelProviderFactory = NoteViewModelProviderFactory(application, noteRepository)

        noteViewModel = ViewModelProvider(this, viewModelProviderFactory).get(NoteViewModel::class.java)
    }
}