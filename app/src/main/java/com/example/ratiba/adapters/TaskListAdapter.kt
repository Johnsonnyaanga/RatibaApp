package com.example.ratiba.adapters

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ratiba.MainActivity
import com.example.ratiba.R
import com.example.ratiba.fragments.HomeDirections
import com.example.ratiba.fragments.UpdateTask
import com.example.ratiba.models.Task
import com.google.android.material.card.MaterialCardView

class TaskListAdapter:RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    private var tasklist = emptyList<Task>()
    class TaskViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var titleTask:TextView = itemView.findViewById(R.id.task_text_id)
        var descTask:TextView = itemView.findViewById(R.id.taskdesc_text_id)
        var dateTask:TextView = itemView.findViewById(R.id.date_text_id)
        var checkBoxTask:CheckBox = itemView.findViewById(R.id.task_checkbox_id)
        var taskCard:MaterialCardView = itemView.findViewById(R.id.item_card)

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.tasks_view_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        var currentItem = tasklist[position]
        val ctx = holder.itemView.context
        holder.apply {
            titleTask.text = currentItem.title
                descTask.text = currentItem.dueDate
        }

        holder.itemView.findViewById<TextView>(R.id.taskdesc_text_id).text = currentItem.description
        holder.itemView.findViewById<TextView>(R.id.date_text_id).text = currentItem.dueDate
        holder.itemView.findViewById<MaterialCardView>(R.id.item_card).setOnClickListener(
            View.OnClickListener { view ->
                val updateFragment = UpdateTask()
                val bundle = Bundle()
                bundle.putString("taskname", currentItem.title)
                updateFragment.arguments = bundle

                val action = HomeDirections.actionHomeToUpdateTask(currentItem)
                holder.itemView.findNavController().navigate(action)

            }
        )
        holder.itemView.findViewById<CheckBox>(R.id.task_checkbox_id).setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                    val taskUpdate = Task(
                        currentItem.id,
                        currentItem.title,
                        currentItem.description,
                        currentItem.dueDate, "done", currentItem.category
                    )
                (ctx as MainActivity).mTaskViewModel.updateTask(taskUpdate)
            }else {
                val taskUpdate = Task(
                    currentItem.id,
                    currentItem.title,
                    currentItem.description,
                    currentItem.dueDate, "notDone", currentItem.category
                )
                (ctx as MainActivity).mTaskViewModel.updateTask(taskUpdate)
            }

        }

        if (currentItem.status.equals("done")){
            holder.itemView.findViewById<CheckBox>(R.id.task_checkbox_id).isChecked = true
            holder.itemView.findViewById<TextView>(R.id.taskdesc_text_id).paintFlags = holder.itemView.findViewById<TextView>(R.id.taskdesc_text_id)
                .paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.itemView.findViewById<TextView>(R.id.task_text_id).paintFlags = holder.itemView.findViewById<TextView>(R.id.task_text_id)
                .paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.itemView.findViewById<TextView>(R.id.date_text_id).setTextColor(Color.parseColor("#FF0000"))

        }else{
            holder.itemView.findViewById<CheckBox>(R.id.task_checkbox_id).isChecked = false
            holder.itemView.findViewById<TextView>(R.id.taskdesc_text_id).paintFlags = 0
            holder.itemView.findViewById<TextView>(R.id.task_text_id).paintFlags = 0
            holder.itemView.findViewById<TextView>(R.id.date_text_id).setTextColor(Color.parseColor("#FF000000"))

        }


    }

    override fun getItemCount(): Int {
        return tasklist.size

    }

    fun setData(task: List<Task>){
        this.tasklist = task
        notifyDataSetChanged()

    }


}