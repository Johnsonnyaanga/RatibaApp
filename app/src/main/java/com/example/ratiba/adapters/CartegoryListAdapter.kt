import android.app.AlertDialog
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ratiba.MainActivity
import com.example.ratiba.R
import com.example.ratiba.TaskRepository
import com.example.ratiba.fragments.CartegoriesFragmentDirections
import com.example.ratiba.models.Cartegories
import com.example.ratiba.room.TaskDatabase
import com.example.ratiba.viewmodels.TaskViewModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartegoryListAdapter: RecyclerView.Adapter<CartegoryListAdapter.CartegoryViewHolder>() {

    private var cartegorylist = emptyList<Cartegories>()
    private lateinit var alertDialog: AlertDialog
    class CartegoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartegoryViewHolder {
        return CartegoryViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.cartegories_rows,parent,false))
    }

    override fun onBindViewHolder(holder: CartegoryViewHolder, position: Int) {
        val ctx = holder.itemView.context





        var currentItem = cartegorylist[position]

        //real time cart count
        (ctx as MainActivity).mTaskViewModel.getCartCount(currentItem.cartegoryName)
        ctx.mTaskViewModel.cartCount.observe(ctx as LifecycleOwner, Observer {
            holder.itemView.findViewById<TextView>(R.id.number_of_tasks_id).text = it.toString()

        })



        holder.itemView.findViewById<TextView>(R.id.cartegory_text_id).text = currentItem.cartegoryName
        //holder.itemView.findViewById<TextView>(R.id.number_of_tasks_id).text = currentItem.cartegoryCount.toString()

        //edit cart name
        holder.itemView.findViewById<ImageView>(R.id.edit_cartegory_name).setOnClickListener(View.OnClickListener {

            val repository  = TaskRepository(TaskDatabase.getDatabase(ctx).taskDao())
            val viewGroup: ViewGroup? = holder.itemView.findViewById(android.R.id.content)


            val dialogView: View =
                LayoutInflater.from(ctx)
                    .inflate(R.layout.add_cartegory_dialog, viewGroup, false)

            val builder: AlertDialog.Builder = AlertDialog.Builder(ctx)
            val cancel = dialogView.findViewById<Button>(R.id.button_cancel)
            val add = dialogView.findViewById<Button>(R.id.button_save)
            val cartegoryName: TextInputEditText = dialogView.findViewById(R.id.cartegory_name_text)
            cartegoryName.setText(currentItem.cartegoryName)

            cancel.setOnClickListener { view ->
                alertDialog.dismiss()
            }
            add.setOnClickListener { view ->

                val cartegoryName: TextInputEditText =
                    dialogView.findViewById<TextInputEditText>(R.id.cartegory_name_text)
                val cartCt: Int = 0

                val cartegories =
                    Cartegories(currentItem.cartegory_ID, cartegoryName.text.toString(), cartCt)

                GlobalScope.launch(Dispatchers.IO) {
                    repository.updateCartegories(cartegories)
                }

                alertDialog.dismiss()

            }
            builder.setView(dialogView)
            alertDialog  = builder.create()
            alertDialog.show()

        })
        //delete cart
        holder.itemView.findViewById<ImageView>(R.id.delete_cartegory).setOnClickListener(View.OnClickListener {



        })


        holder.itemView.findViewById<MaterialCardView>(R.id.item_card).setOnClickListener(
            View.OnClickListener {
                    view ->

                val action = CartegoriesFragmentDirections.actionCartegoriesFragmentToCartegorySpecificTasks(currentItem)
                holder.itemView.findNavController().navigate(action)

            }
        )

    }

    override fun getItemCount(): Int {
        return cartegorylist.size

    }

    fun setData(cartegories:List<Cartegories>){
        this.cartegorylist = cartegories
        notifyDataSetChanged()
    }


}