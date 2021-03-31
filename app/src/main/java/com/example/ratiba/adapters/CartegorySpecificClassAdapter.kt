package com.example.ratiba.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ratiba.R
import com.example.ratiba.fragments.CartegorySpecificTasksDirections
import com.example.ratiba.fragments.HomeDirections
import com.example.ratiba.fragments.UpdateTask
import com.example.ratiba.models.Task
import com.google.android.material.card.MaterialCardView

class CartegorySpecificClassAdapter: RecyclerView.Adapter<CartegorySpecificClassAdapter.TaskViewHolder>() {

    private var tasklist = emptyList<Task>()
    class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cartegory_specific_tasks_view_row,parent,false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        var currentItem = tasklist[position]
        holder.itemView.findViewById<TextView>(R.id.task_text_id).text = currentItem.title
        holder.itemView.findViewById<TextView>(R.id.taskdesc_text_id).text = currentItem.description
        holder.itemView.findViewById<TextView>(R.id.date_text_id).text = currentItem.dueDate
        holder.itemView.findViewById<MaterialCardView>(R.id.item_card).setOnClickListener(
            View.OnClickListener {
                    view ->
                val updateFragment  = UpdateTask()
                val bundle = Bundle()
                bundle.putString("taskname",currentItem.title)
                updateFragment.arguments = bundle

                val action = CartegorySpecificTasksDirections.actionCartegorySpecificTasksToUpdateTask(currentItem)
                holder.itemView.findNavController().navigate(action)

            }
        )

    }

    override fun getItemCount(): Int {
        return tasklist.size

    }

    fun setData(task: List<Task>){
        this.tasklist = task
        notifyDataSetChanged()

    }


}