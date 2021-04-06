package com.example.ratiba.fragments

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.ratiba.AlarmReceiver
import com.example.ratiba.R
import com.example.ratiba.TaskRepository
import com.example.ratiba.models.Task
import com.example.ratiba.room.TaskDatabase
import com.example.ratiba.viewmodels.TaskViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class AddTask : Fragment(),AdapterView.OnItemSelectedListener{
    var cal = Calendar.getInstance()
    private lateinit var datetext:TextView
    private lateinit var timetext:TextView

    private lateinit var cartegoryNameSpinner:Spinner
    private lateinit var SpinnerselctedTask:String
    private lateinit var addBTN:ExtendedFloatingActionButton
    private lateinit var Spinner:Spinner



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_add_task, container, false)



        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        //setSupportActionBar(toolbar)
        val navHost = requireActivity().supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment



        val navController = navHost.findNavController()
        NavigationUI.setupActionBarWithNavController(activity as AppCompatActivity, navController)




       datetext = view.findViewById<TextView>(R.id.date_text)


        val taskname = view.findViewById<TextInputEditText>(R.id.task_name_id)
        val taskDescription = view.findViewById<TextInputEditText>(R.id.description_id)
        val dueDate = view.findViewById<TextView>(R.id.date_text)
        val status:String ?  = null
        addBTN = view.findViewById<ExtendedFloatingActionButton>(R.id.submit_task)



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
            Spinner = view.findViewById(R.id.spinner_cartegory)
            Spinner.adapter = adapter
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            Spinner.onItemSelectedListener = this


        })


















        addBTN.setOnClickListener(View.OnClickListener { view ->
            //var cartegory_selected:String


            insertDataToDB(
                taskname.text.toString(),
                taskDescription.text.toString(),
                SpinnerselctedTask,
                dueDate.text.toString()
            )


        })

        val pickdate = view.findViewById<LinearLayout>(R.id.calendarpickerr)

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
        pickdate.setOnClickListener(object : View.OnClickListener {
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






      /*  //alarm
        timepicker.setOnClickListener(View.OnClickListener {
            openTimePickerDialog()

        })*/



        return view
    }

    private fun insertDataToDB(
        taskname: String,
        taskdescription: String,
        category: String,
        dueDate: String?
    ) {

        val context = activity?.applicationContext

        if (inputCheck(taskname, taskdescription)){
            val  mTaskModel = ViewModelProvider(this).get(TaskViewModel::class.java)


            val task = Task(0, taskname, taskdescription, dueDate, null, category)
            mTaskModel.addTask(task)

            GlobalScope.launch(Dispatchers.IO) {
                val dao = TaskDatabase.getDatabase(requireContext()).taskDao()
                val taskr:TaskRepository = TaskRepository(dao)
                val count = taskr.getcartCount(category)
                taskr.updateCartCount(count, category)
            }






            addBTN.setText("Added succesifully")
            findNavController().navigate(R.id.action_addTask_to_home)




        }else{
            Toast.makeText(context, "please enter required fields", Toast.LENGTH_LONG).show()

        }

    }
    private fun inputCheck(taskName: String, taskDesc: String):Boolean{
        return !(isEmpty(taskName) && isEmpty(taskDesc))


    }
    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        datetext!!.setText(sdf.format(cal.getTime()))
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        SpinnerselctedTask = parent?.getItemAtPosition(position).toString()

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }



    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun startAlarm(calendar: Calendar) {
        val alarmManager:AlarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
    fun cancelAlarm(view: View) {
        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, 0)
        alarmManager.cancel(pendingIntent)
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun openTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            onTimeSetListener,
            calendar[Calendar.HOUR_OF_DAY],
            calendar[Calendar.MINUTE],
            true
        )
        timePickerDialog.setTitle("Set Alarm Time")
        timePickerDialog.show()
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private var onTimeSetListener = TimePickerDialog.OnTimeSetListener { view, hour, minute ->
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, 4)
        calendar.set(Calendar.DAY_OF_MONTH, 7)
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        startAlarm(calendar)

    }








}

