package com.arisurya.project.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*
import kotlin.collections.ArrayList


@Suppress("UNCHECKED_CAST")
class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>(), Filterable {

    private lateinit var onItemClickCallback: OnItemClickCallback
    lateinit var listUser: ArrayList<User>
    lateinit var listUserFilter: ArrayList<User>

    fun setData(listUser: ArrayList<User>) {
        this.listUser = listUser
        this.listUserFilter = listUser
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .apply(RequestOptions().override(55, 55))
            .into(holder.imgAvatar)
        holder.tvName.text = user.name
        holder.tvCompany.text = user.company
        holder.tvLocation.text = user.location

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listUser.size


    inner class ListViewHolder(userView: View) : RecyclerView.ViewHolder(userView) {
        var tvName: TextView = userView.findViewById(R.id.tv_user_name)
        var tvCompany: TextView = userView.findViewById(R.id.tv_user_company)
        var tvLocation: TextView = userView.findViewById(R.id.tv_user_location)
        var imgAvatar: ImageView = userView.findViewById(R.id.img_user_avatar)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (charSequence.isNullOrEmpty() || charSequence.length < 0) {
                    filterResults.count = listUserFilter.size
                    filterResults.values = listUserFilter
                } else {
                    val searchChar = charSequence.toString().toLowerCase(Locale.getDefault())
                    val itemModel = ArrayList<User>()

                    for (item in listUserFilter) {
                        if (item.name.toLowerCase(Locale.getDefault()).contains(searchChar))
                            itemModel.add(item)
                    }
                    filterResults.count = itemModel.size
                    filterResults.values = itemModel
                }
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults?
            ) {
                listUser = filterResults?.values as ArrayList<User>
                notifyDataSetChanged()
            }

        }
    }
}