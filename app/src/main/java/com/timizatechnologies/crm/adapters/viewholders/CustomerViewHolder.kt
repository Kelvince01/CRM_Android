package com.timizatechnologies.crm.adapters.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.timizatechnologies.crm.R
import com.timizatechnologies.crm.models.Customer

class CustomerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
    private val nameText: TextView = itemView.findViewById(R.id.nameText)
    private val emailText: TextView = itemView.findViewById(R.id.emailText)
    private val phoneText: TextView = itemView.findViewById(R.id.phoneText)
    private val genderText: TextView = itemView.findViewById(R.id.genderText)
    private val cityText: TextView = itemView.findViewById(R.id.cityText)
    private val addressText: TextView = itemView.findViewById(R.id.addressText)
    private val createDateText: TextView = itemView.findViewById(R.id.createDateText)
    private val updateDateText: TextView = itemView.findViewById(R.id.updateDateText)

    fun bindItem(customer: Customer) {
        itemView.apply {
            nameText.text = customer.name
            emailText.text = customer.email
            phoneText.text = customer.phone
            genderText.text = customer.gender
            cityText.text = customer.city
            addressText.text = customer.address
            createDateText.text = customer.created_date!!.toDate().toString()

            if (customer.update_date != null) {
                updateDateText.text = customer.update_date!!.toDate().toString()
            }
        }
    }
}
