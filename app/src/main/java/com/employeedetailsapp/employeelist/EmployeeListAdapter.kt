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
import com.employeedetailsapp.util.IMAGE_JPEG
import com.employeedetailsapp.util.convertBase64StringToBitmap
import com.employeedetailsapp.util.convertBitmapToJpeg
import kotlinx.android.synthetic.main.list_item_employee_list.view.*

/**
 * This class implements a [RecyclerView] [ListAdapter] which presents [List]
 * data, including computing diffs between lists.
 */
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
            if(!item.thumbImage.isNullOrEmpty()) {
                image.setImageBitmap(convertBase64StringToBitmap(item.thumbImage))
            }
            employeeListLayout.setOnClickListener {
                val intent = Intent(it.context, EmployeeDetailsActivity::class.java)
                if (!item.image.isNullOrEmpty()) {
                    val bitmap = convertBase64StringToBitmap(item.image)
                    intent.putExtra(IMAGE_JPEG, convertBitmapToJpeg(bitmap))
                }
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

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Employee]
     * has been updated.
     */
    class EmployeeListDiffCallback : DiffUtil.ItemCallback<Employee>() {
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem == newItem
        }

    }
}