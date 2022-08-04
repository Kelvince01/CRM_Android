package com.timizatechnologies.crm.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.timizatechnologies.crm.models.Customer
import kotlin.collections.ArrayList

class CustomerViewModel : ViewModel() {
    private var db = Firebase.firestore
    private val customers = "Customers"

    val createLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val updateLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val getListLiveData: MutableLiveData<List<Customer>> by lazy {
        MutableLiveData<List<Customer>>()
    }

    val deleteLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun create(customer: Customer) {
        val docRef = db.collection(customers)
        docRef.add(customer.toMap()).addOnSuccessListener {
            createLiveData.postValue(true)
        }.addOnFailureListener {
            Log.d("create", it.localizedMessage!!)
            createLiveData.postValue(false)
        }
    }

    fun update(customer: Customer) {
        val docRef = db.collection(customers)
        docRef.document(customer.id!!).update(customer.toMap()).addOnSuccessListener {
            updateLiveData.postValue(true)
        }.addOnFailureListener {
            Log.d("update", it.localizedMessage!!)
            updateLiveData.postValue(false)
        }
    }

    fun delete(id: String) {
        val docRef = db.collection(customers)
        docRef.document(id).delete().addOnSuccessListener {
            deleteLiveData.postValue(true)
        }.addOnFailureListener {
            Log.d("delete", it.localizedMessage!!)
            deleteLiveData.postValue(false)
        }
    }

    fun getList() {
        val docRef = db.collection(customers)
        docRef.get().addOnSuccessListener {
            val customers = ArrayList<Customer>()
            for (item in it.documents) {
                val customer = Customer()
                customer.id = item.id
                customer.name = item.data!!["name"] as String?
                customer.email = item.data!!["email"] as String?
                customer.phone = item.data!!["phone"] as String?
                customer.gender = item.data!!["gender"] as String?
                customer.city = item.data!!["city"] as String?
                customer.address = item.data!!["address"] as String?
                customer.created_date = item.data!!["created_date"] as Timestamp?
                customer.update_date = item.data!!["update_date"] as Timestamp?
                customer.photo = item.data!!["photo"] as String?
                customers.add(customer)
            }

            getListLiveData.postValue(customers)
        }.addOnFailureListener {
            Log.d("get", it.localizedMessage!!)
            getListLiveData.postValue(null)
        }
    }
}
