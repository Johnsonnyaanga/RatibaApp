package com.example.ratiba.fragments

import CartegoryListAdapter
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ratiba.R
import com.example.ratiba.adapters.TaskListAdapter
import com.example.ratiba.models.Cartegories
import com.example.ratiba.viewmodels.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText


class CartegoriesFragment : Fragment() {
    private lateinit var mTaskViewModel:TaskViewModel
    private lateinit var alertDialog: AlertDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_cartegories, container, false)
        /*v.setOnClickListener(View.OnClickListener {
            v->
            val model = ViewModelProvider(this).get(TaskViewModel::class.java)
            val m = model.getCartCount("personal")
            toastMessage(m.toString())

        })*/
        val recyclerViewCategory = v.findViewById<RecyclerView>(R.id.recycler_cartegories)
        val floating_Add = v.findViewById<FloatingActionButton>(R.id.floating_add_cartegory)
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        val adapter = CartegoryListAdapter()
        recyclerViewCategory.adapter = adapter
        recyclerViewCategory.layoutManager = LinearLayoutManager(context)
        mTaskViewModel.readAllCategories.observe(viewLifecycleOwner, Observer { cart ->
            adapter.setData(cart)
        })


        floating_Add.setOnClickListener(View.OnClickListener {
            getAddCartegoryDialog(v)
        })




        return v
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
        if (inputCheck(cart_name)){
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
  private fun inputCheck(name:String):Boolean{
      return !(name.isEmpty())
  }




}