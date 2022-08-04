package com.timizatechnologies.crm.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.timizatechnologies.crm.R
import com.timizatechnologies.crm.models.Task

class TasksRecycleViewAdapter :
    RecyclerView.Adapter<TasksRecycleViewAdapter.MyViewHolder>() {
    var context: Context? = null
    var list: List<Task>? = null
    fun TaskAdapter(context: Context?, list: List<Task>?) {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.task_item, null)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        myViewHolder.tvtid.text = (list!![i].tid)
        myViewHolder.tvtitle.text = (list!![i].title)
        myViewHolder.tvtdate.text = (list!![i].tdate)
        myViewHolder.tvdeadline.text = (list!![i].tdeadline)
        myViewHolder.tvdesc.text = (list!![i].tdesc)
        myViewHolder.tvstatus.text = (list!![i].tstatus)
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvtid: TextView
        var tvtitle: TextView
        var tvtdate: TextView
        var tvdeadline: TextView
        var tvdesc: TextView
        var tvstatus: TextView

        init {
            tvtid = itemView.findViewById(R.id.tvtid)
            tvtitle = itemView.findViewById(R.id.tvtitle)
            tvtdate = itemView.findViewById(R.id.tvtdate)
            tvdeadline = itemView.findViewById(R.id.tvdeadline)
            tvdesc = itemView.findViewById(R.id.tvdesc)
            tvstatus = itemView.findViewById(R.id.tvstatus)
        }
    }
}
