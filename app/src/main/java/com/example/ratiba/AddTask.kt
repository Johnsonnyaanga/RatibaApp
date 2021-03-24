package com.example.ratiba

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextUtils.isEmpty
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ratiba.models.Task
import com.example.ratiba.viewmodels.TaskViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.fragment_add_task.*
import java.text.SimpleDateFormat
import java.util.*


class AddTask : Fragment() {
    var cal = Calendar.getInstance()
    private lateinit var datetext:TextInputEditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_task, container, false)

       val  mTaskModel = ViewModelProvider(this).get(TaskViewModel::class.java)

       datetext = view.findViewById<TextInputEditText>(R.id.date_text)
        val taskname = view.findViewById<TextInputEditText>(R.id.task_name_id)
        val taskDescription = view.findViewById<TextInputEditText>(R.id.description_id)
        val dueDate = view.findViewById<TextInputEditText>(R.id.date_text)

        val status:String ?  = null
        val cartegoryNameSpinner = view.findViewById<Spinner>(R.id.spinner_cartegory)
        val addBTN = view.findViewById<MaterialButton>(R.id.submit_task)



        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spiner_tasks,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            cartegoryNameSpinner.adapter = adapter
        }


        addBTN.setOnClickListener(View.OnClickListener {
                view ->
            //var cartegory_selected:String

            insertDataToDB(taskname.text.toString(),
                taskDescription.text.toString(),
                "cartegory_selected",
                dueDate.text.toString())
            findNavController().navigate(R.id.action_addTask_to_home)




        })

        val pickdate = view.findViewById<ImageView>(R.id.calendarpicker)

        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        pickdate!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(requireContext(),
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })







        return view
    }

    private fun insertDataToDB(taskname:String, taskdescription:String,category:String,dueDate:String?) {

        val context = activity?.applicationContext

        if (inputCheck(taskname,taskdescription)){
            val  mTaskModel = ViewModelProvider(this).get(TaskViewModel::class.java)


            val task = Task(0,taskname,taskdescription,dueDate,null,category)
            mTaskModel.addTask(task)

            Toast.makeText(context,"added succesifuly", Toast.LENGTH_LONG).show()

        }else{
            Toast.makeText(context,"please enter required fields", Toast.LENGTH_LONG).show()
        }

    }
    private fun inputCheck(taskName:String,taskDesc: String):Boolean{
        return !(isEmpty(taskName) && isEmpty(taskDesc))


    }
    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        datetext!!.setText(sdf.format(cal.getTime()))
    }

   /* private fun geteSelectedSpinerItem(  myspinner:Spinner):String{

        myspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                var cartegory_selected = parent?.getItemAtPosition(position).toString()


            }


        }





    }*/




}
