package com.example.ratiba.fragments

import android.app.*
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
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
import com.example.ratiba.models.Cartegories
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


class AddTask : Fragment(), AdapterView.OnItemSelectedListener {
    var cal = Calendar.getInstance()
    private lateinit var datetext: TextView
    private lateinit var timetext: TextView
    private lateinit var cartegoryNameSpinner: Spinner
    private lateinit var SpinnerselctedTask: String
    private lateinit var addBTN: ExtendedFloatingActionButton
    private lateinit var Spinner: Spinner
    private lateinit var alertDialog: AlertDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_add_task, container, false)


        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)


        //setSupportActionBar(toolbar)
        val navHost =
            requireActivity().supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment


        val navController = navHost.findNavController()
        NavigationUI.setupActionBarWithNavController(activity as AppCompatActivity, navController)




        datetext = view.findViewById<TextView>(R.id.date_text)
        val taskname = view.findViewById<TextInputEditText>(R.id.task_name_id)
        val taskDescription = view.findViewById<TextInputEditText>(R.id.description_id)
        val dueDate = view.findViewById<TextView>(R.id.date_text)
        val addCart = view.findViewById<ImageView>(R.id.addCartId)
        val status: String? = null
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
        //add cartegory
        addCart.setOnClickListener(View.OnClickListener {

            getAddCartegoryDialog(view)

        })

        val pickdate = view.findViewById<LinearLayout>(R.id.calendarpickerr)

        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
                openTimePickerDialog()
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

        if (inputCheck(taskname, taskdescription)) {
            val mTaskModel = ViewModelProvider(this).get(TaskViewModel::class.java)
            val task = Task(0, taskname, taskdescription, dueDate, null, category)
            mTaskModel.addTask(task)

            GlobalScope.launch(Dispatchers.IO) {
                val dao = TaskDatabase.getDatabase(requireContext()).taskDao()
                val taskr: TaskRepository = TaskRepository(dao)
                val count = taskr.getcartCount(category)
                taskr.updateCartCount(count, category)
            }

            addBTN.setText("Added succesifully")
            findNavController().navigate(R.id.action_addTask_to_home)

        } else {
            Toast.makeText(context, "please enter required fields", Toast.LENGTH_LONG).show()

        }

    }

    private fun inputCheck(taskName: String, taskDesc: String): Boolean {
        return !(isEmpty(taskName) || isEmpty(taskDesc))


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
        val alarmManager: AlarmManager =
            requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
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
            cal[Calendar.HOUR_OF_DAY],
            cal[Calendar.MINUTE],
            true
        )
        timePickerDialog.setTitle("Set Alarm Time")
        timePickerDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private var onTimeSetListener = TimePickerDialog.OnTimeSetListener { view, hour, minute ->

        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)
        cal.set(Calendar.SECOND, 0)
        Log.d("miero", cal.toString())
        startAlarm(cal)

    }


    fun getAddCartegoryDialog(view:View){

        val viewGroup: ViewGroup? = view.findViewById(android.R.id.content)


        val dialogView: View =
            LayoutInflater.from(requireContext())
                .inflate(R.layout.add_cartegory_dialog, viewGroup, false)

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val cancel = dialogView.findViewById<Button>(R.id.button_cancel)
        val add = dialogView.findViewById<Button>(R.id.button_save)

        cancel.setOnClickListener(View.OnClickListener {
                view ->
            alertDialog.dismiss()
        })

        add.setOnClickListener(View.OnClickListener {
                view->
            val cartegoryName:TextInputEditText = dialogView.findViewById<TextInputEditText>(R.id.cartegory_name_text)
            val cartCt:Int = 0
            insertCartegory(cartegoryName?.text.toString(),cartCt)
            toastMessage(cartegoryName.text.toString())
            alertDialog.dismiss()

        })


        builder.setView(dialogView)

        alertDialog  = builder.create()
        alertDialog.show()


    }
    private fun insertCartegory(cart_name:String,cart_count:Int) {
        if (inputCheckCart(cart_name)){
            val mViewModel:TaskViewModel
            mViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
            val cartegories = Cartegories(0,cart_name,cart_count)
            mViewModel.addCartegory(cartegories)
            toastMessage("Cartegory added")
        }else toastMessage("Cartegory name required")
    }
    private fun toastMessage(message:String){
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
    }
    private fun inputCheckCart(name:String):Boolean{
        return !(name.isEmpty())
    }


}

