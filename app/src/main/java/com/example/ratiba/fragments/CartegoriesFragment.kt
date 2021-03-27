package com.example.ratiba.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.ratiba.R
import com.example.ratiba.models.Cartegories
import com.example.ratiba.viewmodels.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText


class CartegoriesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_cartegories, container, false)
        val recyclerViewCategory = v.findViewById<RecyclerView>(R.id.recycler_cartegories)
        val floating_Add = v.findViewById<FloatingActionButton>(R.id.floating_add_cartegory)

        floating_Add.setOnClickListener(View.OnClickListener {
            getAddCartegoryDialog(v)
        })




        return v
    }

    fun getAddCartegoryDialog(view:View){

        val viewGroup: ViewGroup = view.findViewById(android.R.id.content)


        val dialogView: View =
            LayoutInflater.from(requireContext())
                .inflate(R.layout.add_cartegory_dialog, viewGroup, false)

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

        val cancel = dialogView.findViewById<Button>(R.id.button_cancel)
        val add = dialogView.findViewById<Button>(R.id.button_save)
        val cartegoryName = view.findViewById<TextInputEditText>(R.id.cartegory_name_inputText)

        cancel.setOnClickListener(View.OnClickListener {
            view ->

            val alertDialog: AlertDialog = builder.create()
            alertDialog.dismiss()

        })

        add.setOnClickListener(View.OnClickListener {
            view->
            insertCartegory(cartegoryName.text.toString())
        })


        builder.setView(dialogView)

        var alertDialog: AlertDialog = builder.create()
        alertDialog.show()


    }

    private fun insertCartegory(cart_name:String) {
        if (inputCheck(cart_name)){
        val mViewModel:TaskViewModel
        mViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        val cartegories = Cartegories(0,cart_name)
        mViewModel.addCartegory(cartegories)
        toastMessage("Cartegory added")
    }else toastMessage("Cartegory name required")
    }

    private fun toastMessage(message:String){
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
    }
  private fun inputCheck(name:String):Boolean{
      return !(name.isEmpty())
  }




}