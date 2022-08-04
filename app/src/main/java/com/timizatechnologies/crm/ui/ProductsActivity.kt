package com.timizatechnologies.crm.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.Timestamp
import com.squareup.picasso.Picasso
import com.timizatechnologies.crm.R
import com.timizatechnologies.crm.adapters.ProductAdapter
import com.timizatechnologies.crm.models.Product
import com.timizatechnologies.crm.viewmodels.ProductViewModel


class ProductsActivity : AppCompatActivity(), ProductAdapter.OnItemClickListener {
    /*private var mJsonArrayRequest: JsonArrayRequest? = null
    private var mRequestQueue: RequestQueue? = null
    private lateinit var productsList: ArrayList<Product>*/

    private var mRecyclerView: RecyclerView? = null
    private var mFrameLayout: ShimmerFrameLayout? = null
    private var homeRL: LinearLayout? = null

    private lateinit var name: EditText
    private lateinit var price: EditText
    private lateinit var description: EditText
    private lateinit var photo: ImageView
    private lateinit var submit: Button
    private lateinit var rvList: RecyclerView

    private lateinit var productAdapter: ProductAdapter
    private lateinit var list: ArrayList<Product>

    private var selected: Product = Product()

    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        mRecyclerView = findViewById(R.id.RV_product_list)
        homeRL = findViewById(R.id.sv_layout)
        mFrameLayout = findViewById(R.id.shimmerLayout)

        initElement()
        initViewModel()
        //RequestJsonData();
    }

    private fun initElement() {
        name = findViewById(R.id.name)
        price = findViewById(R.id.price)
        description = findViewById(R.id.description)
        photo = findViewById(R.id.photo)
        submit = findViewById(R.id.submit)
        rvList = findViewById(R.id.RV_product_list)

        list = ArrayList()

        submit.setOnClickListener {
            create()
        }

        // Get list
        productViewModel.getList()

    }

    private fun initViewModel() {
        productViewModel.createLiveData.observe(this) {
            onCreate(it)
        }

        productViewModel.updateLiveData.observe(this) {
            onUpdate(it)
        }

        productViewModel.deleteLiveData.observe(this) {
            onDelete(it)
        }

        productViewModel.getListLiveData.observe(this) {
            onGetList(it)
        }
    }

    private fun onCreate(it: Boolean) {
        if (it) {
            productViewModel.getList()
            resetText()
        }
    }

    private fun onUpdate(it: Boolean) {
        if (it) {
            productViewModel.getList()
            resetText()
        }
    }

    private fun onDelete(it: Boolean) {
        if (it) {
            productViewModel.getList()
            resetText()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onGetList(it: List<Product>) {
        list = ArrayList()
        list.addAll(it)

        productAdapter = ProductAdapter(list, this)

        rvList.adapter = productAdapter
        rvList.layoutManager = LinearLayoutManager(baseContext)

        //SetRecyclerViewAdapter(list)
        mFrameLayout!!.startShimmer()
        mFrameLayout!!.visibility = View.GONE
        mRecyclerView!!.visibility = View.VISIBLE

        productAdapter.notifyDataSetChanged()
    }

    private fun create() {
        val product = Product(
            selected.id,
            name.text.toString(),
            price.text.toString().toDouble(),
            selected.created_date ?: Timestamp.now(),
            selected.update_date,
            description.text.toString(),
            photo.toString(),
        )
        if (product.id != null) {
            productViewModel.update(product)
        } else {
            Toast.makeText(this, "Adding Product...", Toast.LENGTH_SHORT).show()
            productViewModel.create(product)
            Toast.makeText(this, "Product  added", Toast.LENGTH_LONG)
                .show() //displaying a success toast
        }
    }

    private fun resetText() {
        selected = Product()

        name.text = null
        price.text = null
        description.text = null
    }

    override fun onClick(item: Product, position: Int) {
        selected = item
        selected.update_date = Timestamp.now()
        displayBottomSheet(selected)

        name.setText(selected.name)
        price.setText(selected.price.toString())
        description.setText(selected.description)

        //displayBottomSheet(list.get(position))
    }

    override fun onDelete(item: Product, position: Int) {
        productViewModel.delete(item.id!!)
    }

    /*fun RequestJsonData() {
        mJsonArrayRequest = JsonArrayRequest(
            "https://jsonkeeper.com/b/JRPX", object : Response.Listener<JSONArray?> {
                override fun onResponse(response: JSONArray?) {
                    var jsonObject: JSONObject? = null
                    for (i in 0 until response!!.length()) {
                        try {
                            jsonObject = response.getJSONObject(i)
                            val product = Product()
                            product.name = jsonObject.getString("name")
                            product.description = jsonObject.getString("description")
                            product.price = jsonObject.getDouble("price")
                            product.created_date = jsonObject.get("created_date") as Timestamp?
                            product.photo = jsonObject.getString("photo")
                            productsList.add(product)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    SetRecyclerViewAdapter(productsList)
                    mFrameLayout!!.startShimmer()
                    mFrameLayout!!.visibility = View.GONE
                    mRecyclerView!!.visibility = View.VISIBLE
                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {}
            })
        mRequestQueue = Volley.newRequestQueue(this@ProductsActivity)
        mRequestQueue!!.add(mJsonArrayRequest)
    }*/

    @SuppressLint("SetTextI18n")
    private fun displayBottomSheet(modal: Product) {
        // on below line we are creating our bottom sheet dialog.
        val bottomSheetTeachersDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        // on below line we are inflating our layout file for our bottom sheet.
        val layout: View =
            LayoutInflater.from(this).inflate(R.layout.bottom_sheet_product_layout, homeRL, false)
        // setting content view for bottom sheet on below line.
        /*if (layout.parent != null) {
            //((ViewGroup) layout.parent).removeView(layout)
            (layout.parent)
        }*/
        bottomSheetTeachersDialog.setContentView(layout)
        // on below line we are setting a cancelable
        bottomSheetTeachersDialog.setCancelable(false)
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true)
        // calling a method to display our bottom sheet.
        bottomSheetTeachersDialog.show()
        // on below line we are creating variables for
        // our text view and image view inside bottom sheet
        // and initialing them with their ids.
        val courseNameTV = layout.findViewById<TextView>(R.id.idTVCourseName)
        val courseDescTV = layout.findViewById<TextView>(R.id.idTVCourseDesc)
        val suitedForTV = layout.findViewById<TextView>(R.id.idTVSuitedFor)
        val priceTV = layout.findViewById<TextView>(R.id.idTVCoursePrice)
        val courseIV: ImageView = layout.findViewById(R.id.idIVCourse)
        // on below line we are setting data to different views on below line.
        courseNameTV.text = modal.name
        courseDescTV.text = modal.description
        suitedForTV.text = "Created at " + modal.created_date
        priceTV.text = "Kshs." + modal.price
        Picasso.get().load(modal.photo).into(courseIV)
        val viewBtn: Button = layout.findViewById(R.id.idBtnVIewDetails)
        val editBtn: Button = layout.findViewById(R.id.idBtnEditCourse)

        // adding on click listener for our edit button.
        editBtn.setOnClickListener { // on below line we are opening our EditCourseActivity on below line.
            /*val i = Intent(this@MainActivity, EditCourseActivity::class.java)
            // on below line we are passing our course modal
            i.putExtra("course", modal)
            startActivity(i)*/
        }
        // adding click listener for our view button on below line.
        viewBtn.setOnClickListener { // on below line we are navigating to browser
            // for displaying course details from its url
            val i = Intent(Intent.ACTION_VIEW)
            //i.data = Uri.parse(modal.getCourseLink())
            startActivity(i)
        }
    }

    /*fun SetRecyclerViewAdapter(list: List<Product>) {
        val myAdapter = ProductAdapter(list, this)
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)
        mRecyclerView!!.adapter = myAdapter
    }*/

    override fun onResume() {
        mFrameLayout!!.startShimmer()
        super.onResume()
    }

    override fun onPause() {
        mFrameLayout!!.stopShimmer()
        super.onPause()
    }
}
