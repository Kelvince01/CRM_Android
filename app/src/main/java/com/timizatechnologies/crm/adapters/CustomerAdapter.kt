package com.timizatechnologies.crm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.timizatechnologies.crm.R
import com.timizatechnologies.crm.adapters.viewholders.CustomerViewHolder
import com.timizatechnologies.crm.models.Customer

class CustomerAdapter(var list: List<Customer>,
                      var onItemClickListener: CustomerAdapter.OnItemClickListener
) : RecyclerView.Adapter<CustomerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.customer_item, parent, false)
        return CustomerViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val item = list[position]
        holder.bindItem(item)
        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(item, position)
        }
        holder.itemView.findViewById<Button>(R.id.delete).setOnClickListener {
            onItemClickListener.onDelete(item, position)
        }
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener {
        fun onClick(item: Customer, position: Int)
        fun onDelete(item: Customer, position: Int)
    }
}
