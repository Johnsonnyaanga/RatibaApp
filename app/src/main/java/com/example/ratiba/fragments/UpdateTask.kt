package com.example.ratiba.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.ratiba.R
import com.example.ratiba.fragments.UpdateTaskArgs
import com.example.ratiba.models.Task
import com.example.ratiba.viewmodels.TaskViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*


class UpdateTask : Fragment(),AdapterView.OnItemSelectedListener{
    var cal = Calendar.getInstance()
    private lateinit var updateBTN:ExtendedFloatingActionButton
    private lateinit var Due:TextInputEditText
    private lateinit var Spinner:Spinner
    private lateinit var SpinnerselctedTask:String
    private lateinit var args:UpdateTaskArgs



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view = inflater.inflate(R.layout.fragment_update_task, container, false)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        //setSupportActionBar(toolbar)
        val navHost = requireActivity().supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment

        val navController = navHost.findNavController()
        NavigationUI.setupActionBarWithNavController(activity as AppCompatActivity, navController)

        arguments?.let {

            args = UpdateTaskArgs.fromBundle(it)
            Due =  view.findViewById(R.id.due_date_label)
            val taskname =  view.findViewById<TextInputEditText>(R.id.task_label_text_input)
            val taskDesc =  view.findViewById<TextInputEditText>(R.id.desc_text_label)

            //date picker
            val updateDate = view.findViewById<ImageView>(R.id.update_date)

            // create an OnDateSetListener
            val dateSetListener = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(
                    view: DatePicker, year: Int, monthOfYear: Int,
                    dayOfMonth: Int
                ) {
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateDateInView()
                }
            }

            // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
            updateDate!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    DatePickerDialog(
                        requireContext(),
                        dateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }

            })





            Due.setText(args.currentTask.dueDate)
            taskname.setText(args.currentTask.title)
            taskDesc.setText(args.currentTask.description)

            //update by click
            updateBTN = view.findViewById(R.id.floatingActionButton_update)
            updateBTN.setOnClickListener(View.OnClickListener {
                view->
                insertDataToDB(
                    args.currentTask.id,
                    taskname.text.toString(),
                    taskDesc.text.toString(),
                    args.currentTask.category.toString(),
                  Due.text.toString()
                )



            })


        }

        //get cartegories
        val mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        val mSpinnerData = mTaskViewModel.readAllCategorynames
        mSpinnerData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

                data ->

            val adapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                data
            )
            Spinner = view.findViewById(R.id.spinner_update)
            Spinner.adapter = adapter
            val set:String? = args.currentTask.category
            Spinner.setSelection(adapter.getPosition(set))
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            Spinner.onItemSelectedListener = this



        })











        return view
    }
    private fun insertDataToDB(
        id:Int,
        taskname: String,
        taskdescription: String,
        category: String,
        dueDate: String?
    ) {

        val context = activity?.applicationContext

        if (inputCheck(taskname, taskdescription)){
            val  mTaskModel = ViewModelProvider(this).get(TaskViewModel::class.java)


            val task = Task(id, taskname, taskdescription, dueDate, null, SpinnerselctedTask)
            mTaskModel.updateTask(task)
            updateBTN.setText("Saved")
            findNavController().navigate(R.id.action_updateTask_to_home)

        }else{
            Toast.makeText(context, "please enter required fields", Toast.LENGTH_LONG).show()
        }

    }
    private fun inputCheck(taskName: String, taskDesc: String):Boolean{
        return !(TextUtils.isEmpty(taskName) || TextUtils.isEmpty(taskDesc))


    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        Due!!.setText(sdf.format(cal.getTime()))
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        SpinnerselctedTask = parent?.getItemAtPosition(position).toString()

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {



    }



}