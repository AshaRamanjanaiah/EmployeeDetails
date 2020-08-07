package com.employeedetailsapp.employeelist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.employeedetailsapp.R
import com.employeedetailsapp.employeedetails.EmployeeDetailsActivity
import com.employeedetailsapp.model.Employee
import kotlinx.android.synthetic.main.list_item_employee_list.view.*

class EmployeeListAdapter : ListAdapter<Employee,
        EmployeeListAdapter.EmployeeListViewHolder>(EmployeeListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeListViewHolder {
        return EmployeeListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: EmployeeListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class EmployeeListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.iv_employee_image
        val name = view.tv_name
        val gender = view.tv_gender
        val dateOfBirth = view.tv_date_of_birth
        val employeeListLayout = view.employee_list_layout

        fun bind(item: Employee) {
            name.text = "${item.first_name} ${item.last_name}"
            gender.text = item.gender
            dateOfBirth.text = item.birth_date
            employeeListLayout.setOnClickListener {
                val intent = Intent(it.context, EmployeeDetailsActivity::class.java)
                it.context.startActivity(intent)
            }
        }

        companion object {
            fun from(parent: ViewGroup): EmployeeListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.list_item_employee_list, parent, false)

                return EmployeeListViewHolder(view)
            }
        }
    }

    class EmployeeListDiffCallback : DiffUtil.ItemCallback<Employee>() {
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem == newItem
        }

    }
}