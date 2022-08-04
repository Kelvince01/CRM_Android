package com.timizatechnologies.crm.adapters.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.timizatechnologies.crm.R
import com.timizatechnologies.crm.models.Order

class OrderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val nameText: TextView = itemView.findViewById(R.id.nameText)
    private val priceText: TextView = itemView.findViewById(R.id.priceText)
    private val descriptionText: TextView = itemView.findViewById(R.id.descriptionText)
    private val createDateText: TextView = itemView.findViewById(R.id.createDateText)
    private val updateDateText: TextView = itemView.findViewById(R.id.updateDateText)

    fun bindItem(order: Order) {
        itemView.apply {
            nameText.text = order.name
            priceText.text = "Kshs. ${order.price}"
            descriptionText.text = order.description
            createDateText.text = order.create_date!!.toDate().toString()

            if (order.update_date != null) {
                updateDateText.text = order.update_date!!.toDate().toString()
            }
        }
    }
}
