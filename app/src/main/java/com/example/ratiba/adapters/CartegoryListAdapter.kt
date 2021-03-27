import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ratiba.R
import com.example.ratiba.fragments.HomeDirections
import com.example.ratiba.fragments.UpdateTask
import com.example.ratiba.models.Task
import com.google.android.material.card.MaterialCardView

class CartegoryListAdapter: RecyclerView.Adapter<CartegoryListAdapter.CartegoryViewHolder>() {

    private var tasklist = emptyList<Task>()
    class CartegoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartegoryViewHolder {
        return CartegoryViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.cartegories_rows,parent,false))
    }

    override fun onBindViewHolder(holder: CartegoryViewHolder, position: Int) {
        var currentItem = tasklist[position]
        holder.itemView.findViewById<TextView>(R.id.cartegory_text_id).text = currentItem.title
        holder.itemView.findViewById<TextView>(R.id.number_of_tasks_id).text = currentItem.description
        holder.itemView.findViewById<MaterialCardView>(R.id.item_card).setOnClickListener(
            View.OnClickListener {
                    view ->

                val action = HomeDirections.actionHomeToUpdateTask(currentItem)
                holder.itemView.findNavController().navigate(action)

            }
        )

    }

    override fun getItemCount(): Int {
        return tasklist.size

    }

    fun setData(task:List<Task>){
        this.tasklist = task
        notifyDataSetChanged()
    }


}