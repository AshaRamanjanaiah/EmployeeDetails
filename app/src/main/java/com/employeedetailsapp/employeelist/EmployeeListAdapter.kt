package com.employeedetailsapp.employeelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.employeedetailsapp.R
import kotlinx.android.synthetic.main.list_item_employee_list.view.*

class EmployeeListAdapter: ListAdapter<String,
        EmployeeListAdapter.EmployeeListViewHolder>(
    EmployeeListDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeListViewHolder {
        return EmployeeListViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: EmployeeListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class EmployeeListViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name = view.name

        fun bind(item: String) {
            name.text = item
        }

        companion object {
            fun from(parent: ViewGroup): EmployeeListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.list_item_employee_list, parent, false)

                return EmployeeListViewHolder(
                    view
                )
            }
        }
    }

    class EmployeeListDiffCallback: DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
}