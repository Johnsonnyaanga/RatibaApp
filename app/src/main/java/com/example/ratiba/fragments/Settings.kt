package com.example.ratiba.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.ratiba.R
import com.example.ratiba.viewmodels.TaskViewModel


class Settings : Fragment() {
    private lateinit var deleteTasks:ImageView
    private lateinit var deleteCartegories: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view1 = inflater.inflate(R.layout.fragment_settings, container, false)

        val toolbar = view1.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        //setSupportActionBar(toolbar)
        val navHost = requireActivity().supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment

        val navController = navHost.findNavController()
        NavigationUI.setupActionBarWithNavController(activity as AppCompatActivity, navController)





        view1.findViewById<TextView>(R.id.twitterlink).setOnClickListener(View.OnClickListener {
            var intent: Intent? = null
            try {
                // get the Twitter app if possible
                (activity as AppCompatActivity).packageManager.getPackageInfo(
                    "com.twitter.android",
                    0
                )
                intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("twitter://user?screen_name=johnsonyaanga")
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            } catch (e: Exception) {
                // no Twitter app, revert to browser
                intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/USERID_OR_PROFILENAME")
                )
            }
            this.startActivity(intent)
        })

        view1.findViewById<TextView>(R.id.instagramlink).setOnClickListener(View.OnClickListener {
            val uri = Uri.parse("http://instagram.com/johnson_nyaanga")
            val likeIng = Intent(Intent.ACTION_VIEW, uri)

            likeIng.setPackage("com.instagram.android")

            try {
                startActivity(likeIng)
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/johnson_nyaanga")
                    )
                )
            }
        })

        view1.findViewById<TextView>(R.id.feedback).setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:johnsonyaanga@gmail.com") // only email apps should handle this

            intent.putExtra(Intent.EXTRA_EMAIL, "")
            intent.putExtra(Intent.EXTRA_SUBJECT,"")
            if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                startActivity(intent)
            }
        })

        view1.findViewById<ImageView>(R.id.delete_tasks_icon).setOnClickListener(View.OnClickListener {
            deleteTasksDialog()
        })

        view1.findViewById<ImageView>(R.id.delete_cartegories_icon).setOnClickListener(View.OnClickListener {
            deleteCartegoriesDialog()
        })




        return view1
    }

    private fun deleteAllTasks(){
        val vmodel = ViewModelProvider(this).get(TaskViewModel::class.java)
        vmodel.deleteAllTasks()
    }
    private fun deleteAllCartegories(){
        val vmodel = ViewModelProvider(this).get(TaskViewModel::class.java)
        vmodel.deleteAllCartegories()
    }

    private fun deleteTasksDialog(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Delete all Tasks ?")
            .setCancelable(false)
            .setPositiveButton("Okay") { dialog, id ->
                deleteAllTasks()
            }
            .setNegativeButton("Cancel") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
    private fun deleteCartegoriesDialog(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Delete all Cartegories ?")
            .setCancelable(false)
            .setPositiveButton("Okay") { dialog, id ->
                deleteAllCartegories()
            }
            .setNegativeButton("Cancel") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }


}