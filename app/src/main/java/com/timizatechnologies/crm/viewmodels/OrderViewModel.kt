package com.timizatechnologies.crm.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.timizatechnologies.crm.models.Order

class OrderViewModel: ViewModel() {
    private var db = Firebase.firestore
    private val orders = "Orders"

    val createLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val updateLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val getListLiveData: MutableLiveData<List<Order>> by lazy {
        MutableLiveData<List<Order>>()
    }

    val deleteLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun create(order: Order) {
        val docRef = db.collection(orders)
        //val docRef = db.collection("CRM_DB").document(orders)
        //docRef.set(order.toMap()).addOnSuccessListener {
        docRef.add(order.toMap()).addOnSuccessListener {
            createLiveData.postValue(true)
        }.addOnFailureListener {
            Log.d("create", it.localizedMessage!!)
            createLiveData.postValue(false)
        }
    }

    fun update(order: Order) {
        val docRef = db.collection(orders)
        docRef.document(order.id!!).update(order.toMap()).addOnSuccessListener {
            updateLiveData.postValue(true)
        }.addOnFailureListener {
            Log.d("update", it.localizedMessage!!)
            updateLiveData.postValue(false)
        }
    }

    fun delete(id: String) {
        val docRef = db.collection(orders)
        docRef.document(id).delete().addOnSuccessListener {
            deleteLiveData.postValue(true)
        }.addOnFailureListener {
            Log.d("delete", it.localizedMessage!!)
            deleteLiveData.postValue(false)
        }
    }

    fun getList() {
        val docRef = db.collection(orders)
        docRef.get().addOnSuccessListener {
            val orders = ArrayList<Order>()
            for (item in it.documents) {
                val order = Order()
                order.id = item.id
                order.name = item.data!!["name"] as String?
                order.price = item.data!!["price"] as Double?
                order.description = item.data!!["description"] as String?
                order.create_date = item.data!!["create_date"] as Timestamp?
                order.update_date = item.data!!["update_date"] as Timestamp?
                orders.add(order)
            }

            getListLiveData.postValue(orders)
        }.addOnFailureListener {
            Log.d("get", it.localizedMessage!!)
            getListLiveData.postValue(null)
        }
    }
}
