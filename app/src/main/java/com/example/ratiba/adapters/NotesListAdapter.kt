package com.example.ratiba.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ratiba.MainActivity
import com.example.ratiba.R
import com.example.ratiba.fragments.HomeDirections
import com.example.ratiba.fragments.NotesFragmentDirections
import com.example.ratiba.fragments.UpdateTask
import com.example.ratiba.models.Notes
import com.example.ratiba.models.Task
import com.google.android.material.card.MaterialCardView
import com.google.android.play.core.assetpacks.cu

class NotesListAdapter:RecyclerView.Adapter<NotesListAdapter.NotesViewHolder>()  {

     var noteslist = mutableListOf<Notes>()




    class NotesViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        var notesTitle:TextView
        var notesDesc:TextView
        var notesDate:TextView
        var cardView:MaterialCardView
        var deleteNote:ImageView

        init {
            notesTitle = itemView.findViewById(R.id.notes_text_id)
            notesDesc = itemView.findViewById(R.id.notesdesc_text_id)
            notesDate = itemView.findViewById(R.id.notesdate_text_id)
            cardView = itemView.findViewById(R.id.item_card_update)
            deleteNote = itemView.findViewById(R.id.delete_note)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.notes_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val ctx = holder.itemView.context

        var currentItem = noteslist[position]
        val notesViewModel = (ctx as MainActivity).mNotesViewModel


        holder.apply {
            notesTitle.text = currentItem.title
            notesDesc.text = currentItem.description
            notesDate.text = currentItem.date
            cardView.setOnClickListener{
                val action = NotesFragmentDirections.actionNotesFragmentToUpdateNotesFragment(currentItem)
                itemView.findNavController().navigate(action)
            }

            deleteNote.setOnClickListener{
                notesViewModel.deleteNote(currentItem)
                noteslist.removeAt(position)
                ctx.Snackbar(currentItem)
                notifyDataSetChanged();

                //notifyItemRemoved(adapterPosition)

            }

        }




    }

    override fun getItemCount(): Int {
        return noteslist.size
    }

    fun setData(note: List<Notes>){
        this.noteslist = note as MutableList<Notes>
        notifyDataSetChanged()

    }


}