package com.timizatechnologies.crm.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Timestamp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.timizatechnologies.crm.R
import com.timizatechnologies.crm.adapters.CustomerAdapter
import com.timizatechnologies.crm.models.Customer
import com.timizatechnologies.crm.viewmodels.CustomerViewModel
import kotlinx.android.synthetic.main.add_customer_dialog.view.*
import java.util.*


class CustomersActivity : AppCompatActivity(), CustomerAdapter.OnItemClickListener {
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var gender: EditText
    private lateinit var city: EditText
    private lateinit var address: EditText
    private lateinit var photo: ImageView

    //private lateinit var submit: Button
    private lateinit var btnAddCustomer: Button
    private lateinit var rvList: RecyclerView

    private lateinit var customerAdapter: CustomerAdapter
    private lateinit var list: ArrayList<Customer>
    private var REQUIRED: String = "This field id required!"
    private var GALLERY_REQUEST_CODE: Int = 1
    val ONE_MEGABYTE = (1024 * 1024).toLong()

    private var selected: Customer = Customer()

    private val customerViewModel: CustomerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customers)

        initElement()
        initViewModel()

        btnAddCustomer.setOnClickListener {
            showMessageBox(null)
        }
    }

    private fun validateData(): Boolean {
        if (TextUtils.isEmpty(name.text)) {
            name.error = REQUIRED
            return false
        }
        return true
    }

    private fun initElement() {
        /*name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        gender = findViewById(R.id.gender)
        city = findViewById(R.id.city)
        address = findViewById(R.id.address)
        photo = findViewById(R.id.photo)
        //submit = findViewById(R.id.submit)
       */

        btnAddCustomer = findViewById(R.id.btn_add_dialog)
        rvList = findViewById(R.id.rvList)

        list = ArrayList()

        /*submit.setOnClickListener {
            create()
        }*/

        // Get list
        customerViewModel.getList()

    }

    private fun initViewModel() {
        customerViewModel.createLiveData.observe(this, {
            onCreate(it)
        })

        customerViewModel.updateLiveData.observe(this, {
            onUpdate(it)
        })

        customerViewModel.deleteLiveData.observe(this, {
            onDelete(it)
        })

        customerViewModel.getListLiveData.observe(this, {
            onGetList(it)
        })
    }

    fun showMessageBox(customer: Customer?) {

        //Inflate the dialog as custom view
        val messageBoxView = LayoutInflater.from(this).inflate(R.layout.add_customer_dialog, null)

        //AlertDialogBuilder
        val messageBoxBuilder = AlertDialog.Builder(this).setView(messageBoxView)

        //setting text values
        messageBoxView.message_box_header.text = "Add Customer Info"

        name = messageBoxView.name
        email = messageBoxView.email
        phone = messageBoxView.phone
        gender = messageBoxView.gender
        city = messageBoxView.city
        address = messageBoxView.address
        photo = messageBoxView.photo
        //submit = findViewById(R.id.submit)

        //show dialog
        val messageBoxInstance = messageBoxBuilder.show()

        //set Listener
        messageBoxView.setOnClickListener {
            //close dialog
            messageBoxInstance.dismiss()
        }

        photo.setOnClickListener {
            selectImageFromGallery()
            //finish()
        }

        messageBoxView.btn_add.setOnClickListener {
            create()
        }

        messageBoxView.btn_cancel.setOnClickListener { //close dialog
            messageBoxInstance.dismiss()
        }
    }

    private fun selectImageFromGallery() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Please select..."
            ),
            GALLERY_REQUEST_CODE
        )
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        if (requestCode == GALLERY_REQUEST_CODE
            && resultCode == Activity.RESULT_OK
            && data != null
            && data.data != null
        ) {

            // Get the Uri of data
            val file_uri = data.data
            uploadImageToFirebase(file_uri!!)
        }
    }

    private fun uploadImageToFirebase(fileUri: Uri) {
        if (fileUri != null) {
            val fileName = UUID.randomUUID().toString() + ".jpg"

            val database = FirebaseDatabase.getInstance()
            val refStorage =
                FirebaseStorage.getInstance().reference.child("customer_images/$fileName")

            refStorage.putFile(fileUri)
                .addOnSuccessListener(
                    OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                            val imageUrl = it.toString()
                            //Toast.makeText(this, imageUrl)
                        }
                    })

                .addOnFailureListener(OnFailureListener { e ->
                    print(e.message)
                })
        }
    }

    private fun onCreate(it: Boolean) {
        if (it) {
            customerViewModel.getList()
            resetText()
        }
    }

    private fun onUpdate(it: Boolean) {
        if (it) {
            customerViewModel.getList()
            resetText()
        }
    }

    private fun onDelete(it: Boolean) {
        if (it) {
            customerViewModel.getList()
            resetText()
        }
    }

    private fun onGetList(it: List<Customer>) {
        list = ArrayList()
        list.addAll(it)

        /*Glide.with(this /* context */)
            .using(FirebaseImageLoader())
            .load(storageReference)
            .into(imageView)*/

        customerAdapter = CustomerAdapter(list, this)

        rvList.adapter = customerAdapter
        rvList.layoutManager = LinearLayoutManager(baseContext)

        customerAdapter.notifyDataSetChanged()
    }

    private fun create() {
        val customer = Customer(
            selected.id,
            name.text.toString(),
            email.text.toString(),
            phone.text.toString(),
            gender.text.toString(),
            city.text.toString(),
            address.text.toString(),
            selected.created_date ?: Timestamp.now(),
            selected.update_date
        )
        if (customer.id != null) {
            customerViewModel.update(customer)
        } else {
            customerViewModel.create(customer)
        }
    }

    private fun resetText() {
        selected = Customer()

        name.text = null
        email.text = null
        phone.text = null
        gender.text = null
        city.text = null
        address.text = null
    }

    override fun onClick(item: Customer, position: Int) {
        selected = item
        selected.update_date = Timestamp.now()

        showMessageBox(selected)

        name.setText(selected.name)
        email.setText(selected.email.toString())
        phone.setText(selected.phone.toString())
        gender.setText(selected.gender.toString())
        city.setText(selected.city.toString())
        address.setText(selected.address)
    }

    override fun onDelete(item: Customer, position: Int) {
        customerViewModel.delete(item.id!!)
    }
}
