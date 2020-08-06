package com.employeedetailsapp.employeelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.employeedetailsapp.R
import kotlinx.android.synthetic.main.fragment_employee_list.*

/**
 * A simple [Fragment] subclass.
 * Use the [EmployeeListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmployeeListFragment : Fragment() {

    private val employeeListAdapter =
        EmployeeListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_employee_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = employeeListAdapter
        }
        val arrayList = listOf<String>("Asha", "Manas", "Charvi")
        employeeListAdapter.submitList(arrayList)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            EmployeeListFragment()
    }
}