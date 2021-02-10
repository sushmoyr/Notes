package com.sushmoyr.notes.utils

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sushmoyr.notes.databinding.NoteLayoutBinding
import com.sushmoyr.notes.fragments.HomeFragmentDirections
import com.sushmoyr.notes.model.Note
import java.util.*

class NotesAdapter: RecyclerView.Adapter<NotesAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: NoteLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    private val diferCallback =
        object : DiffUtil.ItemCallback<Note>(){
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

        }

    val differ = AsyncListDiffer(this, diferCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(NoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        holder.itemView.apply {
            holder.binding.tvNoteTitle.text = currentNote.title
            holder.binding.tvNoteBody.text = currentNote.body
            val random = Random()
            val color = Color.argb(
                255,
                random.nextInt(255),
                random.nextInt(255),
                random.nextInt(255)
            )

            holder.binding.ibColor.setBackgroundColor(color)

        }.setOnClickListener{
            val direction = HomeFragmentDirections.actionHomeFragmentToUpdateFragment(currentNote)
            it.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}