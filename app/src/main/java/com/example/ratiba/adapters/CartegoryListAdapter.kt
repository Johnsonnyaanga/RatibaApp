import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ratiba.R
import com.example.ratiba.fragments.CartegoriesFragmentDirections
import com.example.ratiba.models.Cartegories
import com.google.android.material.card.MaterialCardView

class CartegoryListAdapter: RecyclerView.Adapter<CartegoryListAdapter.CartegoryViewHolder>() {

    private var cartegorylist = emptyList<Cartegories>()
    class CartegoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartegoryViewHolder {
        return CartegoryViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.cartegories_rows,parent,false))
    }

    override fun onBindViewHolder(holder: CartegoryViewHolder, position: Int) {
        var currentItem = cartegorylist[position]
        holder.itemView.findViewById<TextView>(R.id.cartegory_text_id).text = currentItem.cartegoryName
        holder.itemView.findViewById<TextView>(R.id.number_of_tasks_id).text = currentItem.cartegoryCount.toString()
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