package com.example.ratiba.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ratiba.R
import com.example.ratiba.fragments.UpdateTaskArgs
import com.google.android.material.textfield.TextInputEditText


class UpdateTask : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view = inflater.inflate(R.layout.fragment_update_task, container, false)

        arguments?.let {
         val args = UpdateTaskArgs.fromBundle(it)

            val Due =  view.findViewById<TextInputEditText>(R.id.due_date_label)
            val taskname =  view.findViewById<TextInputEditText>(R.id.task_label_text_input)
            val taskDesc =  view.findViewById<TextInputEditText>(R.id.desc_text_label)

            Due.setText(args.currentTask.dueDate)
            taskname.setText(args.currentTask.title)
            taskDesc.setText(args.currentTask.description)

        }









        return view
    }

}