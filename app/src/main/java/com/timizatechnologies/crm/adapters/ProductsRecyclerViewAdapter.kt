package com.timizatechnologies.crm.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.timizatechnologies.crm.R
import com.timizatechnologies.crm.models.Product

class ProductsRecyclerViewAdapter(mContext: Context, mData: List<Product>) :
    RecyclerView.Adapter<ProductsRecyclerViewAdapter.MyViewHolder>() {
    private val mContext: Context
    private val mData: List<Product>

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ProductsRecyclerViewAdapter.MyViewHolder {
        val view: View
        val inflater = LayoutInflater.from(mContext)
        view = inflater.inflate(R.layout.product_item, parent, false)
        return ProductsRecyclerViewAdapter.MyViewHolder(view)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_product_name: TextView
        var tv_product_price: TextView
        var tv_product_created_date: TextView
        var tv_product_description: TextView
        var img_product_photo: ImageView

        init {
            tv_product_name = itemView.findViewById(R.id.item_product_name)
            tv_product_price = itemView.findViewById(R.id.item_product_price)
            tv_product_created_date = itemView.findViewById(R.id.item_product_created_date)
            tv_product_description = itemView.findViewById(R.id.item_product_description)
            img_product_photo = itemView.findViewById(R.id.item_photo)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(@NonNull holder: ProductsRecyclerViewAdapter.MyViewHolder, position: Int) {
        holder.tv_product_name.text = mData[position].name
        holder.tv_product_price.text = mData[position].price.toString()
        holder.tv_product_created_date.text = mData[position].created_date.toString()
        holder.tv_product_description.text = mData[position].description
        Picasso.get()
            .load(mData[position].photo)
            .into(holder.img_product_photo)
    }

    init {
        this.mContext = mContext
        this.mData = mData
    }
}
