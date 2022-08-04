package com.timizatechnologies.crm.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.timizatechnologies.crm.R
import com.timizatechnologies.crm.adapters.OrderAdapter
import com.timizatechnologies.crm.models.Order
import com.timizatechnologies.crm.viewmodels.OrderViewModel


class OrdersActivity : AppCompatActivity(), OrderAdapter.OnItemClickListener {
    private lateinit var name: EditText
    private lateinit var price: EditText
    private lateinit var description: EditText
    private lateinit var submit: Button
    private lateinit var rvList: RecyclerView

    private lateinit var orderAdapter: OrderAdapter
    private lateinit var list: ArrayList<Order>

    private var selected: Order = Order()

    private val orderViewModel: OrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        //setContentView(CanadaChart(this))

        initElement()
        initViewModel()
    }

    private fun initElement() {
        name = findViewById(R.id.name)
        price = findViewById(R.id.price)
        description = findViewById(R.id.description)
        submit = findViewById(R.id.submit)
        rvList = findViewById(R.id.rvList)

        list = ArrayList()

        submit.setOnClickListener {
            create()
        }

        // Get list
        orderViewModel.getList()

    }

    private fun initViewModel() {
        orderViewModel.createLiveData.observe(this, {
            onCreate(it)
        })

        orderViewModel.updateLiveData.observe(this, {
            onUpdate(it)
        })

        orderViewModel.deleteLiveData.observe(this, {
            onDelete(it)
        })

        orderViewModel.getListLiveData.observe(this, {
            onGetList(it)
        })
    }

    private fun onCreate(it: Boolean) {
        if (it) {
            orderViewModel.getList()
            resetText()
        }
    }

    private fun onUpdate(it: Boolean) {
        if (it) {
            orderViewModel.getList()
            resetText()
        }
    }

    private fun onDelete(it: Boolean) {
        if (it) {
            orderViewModel.getList()
            resetText()
        }
    }

    private fun onGetList(it: List<Order>) {
        list = ArrayList()
        list.addAll(it)

        orderAdapter = OrderAdapter(list, this)

        rvList.adapter = orderAdapter
        rvList.layoutManager = LinearLayoutManager(baseContext)

        orderAdapter.notifyDataSetChanged()
    }

    private fun create() {
        val order = Order(
            selected.id,
            name.text.toString(),
            price.text.toString().toDouble(),
            description.text.toString(),
            selected.create_date ?: Timestamp.now(),
            selected.update_date
        )
        if (order.id != null) {
            orderViewModel.update(order)
        } else {
            Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show()
            orderViewModel.create(order)
            Toast.makeText(this, "Order  added", Toast.LENGTH_LONG)
                .show() //displaying a success toast
        }
    }

    private fun resetText() {
        selected = Order()

        name.text = null
        price.text = null
        description.text = null
    }

    override fun onClick(item: Order, position: Int) {
        selected = item
        selected.update_date = Timestamp.now()

        name.setText(selected.name)
        price.setText(selected.price.toString())
        description.setText(selected.description)
    }

    override fun onDelete(item: Order, position: Int) {
        orderViewModel.delete(item.id!!)
    }
}
