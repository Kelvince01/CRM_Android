package com.timizatechnologies.crm.adapters.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.timizatechnologies.crm.R
import com.timizatechnologies.crm.models.Product

class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var tv_product_name: TextView
    var tv_product_price: TextView
    var tv_product_created_date: TextView
    var tv_product_update_date: TextView
    var tv_product_description: TextView
    var img_product_photo: ImageView

    init {
        tv_product_name = itemView.findViewById(R.id.item_product_name)
        tv_product_price = itemView.findViewById(R.id.item_product_price)
        tv_product_created_date = itemView.findViewById(R.id.item_product_created_date)
        tv_product_update_date = itemView.findViewById(R.id.item_product_update_date)
        tv_product_description = itemView.findViewById(R.id.item_product_description)
        img_product_photo = itemView.findViewById(R.id.item_photo)
    }

    fun bindItem(product: Product) {
        itemView.apply {
            tv_product_name.text = product.name
            tv_product_price.text = "Kshs. ${product.price}"
            tv_product_created_date.text = product.description
            tv_product_description.text = product.created_date!!.toDate().toString()

            if (product.update_date != null) {
                tv_product_update_date.text = product.update_date!!.toDate().toString()
            }
        }
    }
}
