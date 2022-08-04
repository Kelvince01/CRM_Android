package com.timizatechnologies.crm.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.timizatechnologies.crm.databinding.FragmentHomeBinding
import com.timizatechnologies.crm.ui.ActivityFeedActivity
import com.timizatechnologies.crm.ui.CustomersActivity
import com.timizatechnologies.crm.ui.OrdersActivity
import com.timizatechnologies.crm.ui.TasksActivity
import com.timizatechnologies.crm.viewmodels.HomeViewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null
    private var mAuth: FirebaseAuth? = null
    private var loadingPB: ProgressBar? = null

    private val mContainerId = 0
    private var fragmentTransaction: FragmentTransaction? = null

    //private val fragmentManager: FragmentManager? = null
    private val TAG = "DashBoardActivity"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle the back button event
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        firebaseDatabase = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()
        // on below line we are getting database reference.
        databaseReference = firebaseDatabase!!.getReference("products")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val analytics: LinearLayout = binding.llAnalytics
        val customers: LinearLayout = binding.llCustomers
        val orders: LinearLayout = binding.llOrders
        val tasks: LinearLayout = binding.llTasks

        homeViewModel.text.observe(viewLifecycleOwner) {
            //textView.text = it
        }

        analytics.setOnClickListener {
            val intent = Intent(activity, ActivityFeedActivity::class.java)
            startActivity(intent)
        }

        customers.setOnClickListener {
            val intent = Intent(activity, CustomersActivity::class.java)
            startActivity(intent)
        }

        orders.setOnClickListener {
            val intent = Intent(activity, OrdersActivity::class.java)
            startActivity(intent)
        }

        tasks.setOnClickListener {
            val intent = Intent(activity, TasksActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    fun replaceFragment(fragment: Fragment, TAG: String) {
        try {
            fragmentTransaction = requireFragmentManager().beginTransaction()
            fragmentTransaction!!.replace(mContainerId, fragment, tag)
            fragmentTransaction!!.addToBackStack(tag)
            fragmentTransaction!!.commitAllowingStateLoss()
        } catch (e: Exception) {
            // TODO: handle exception
        }
    }

    fun goToHome() {
        //val fragmentManager: FragmentManager = getSupportFragmentManager()
        //for (i in 0 until fragmentManager.getBackStackEntryCount() - 1) {
        //   fragmentManager.popBackStack()
        //}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
