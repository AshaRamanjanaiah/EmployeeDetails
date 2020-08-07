package com.employeedetailsapp.employeelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.employeedetailsapp.R
import com.employeedetailsapp.util.ConnectToInternet
import kotlinx.android.synthetic.main.fragment_employee_list.*

/**
 * A simple [Fragment] subclass.
 * Use the [EmployeeListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmployeeListFragment : Fragment() {

    private val employeeListAdapter =
        EmployeeListAdapter()

    /**
     * initialize our [EmployeeListViewModelViewModel].
     */
    private lateinit var employeeListViewModel: EmployeeListViewModel

    private var isConnected = false

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

        employeeListViewModel =
            of(this).get(EmployeeListViewModel::class.java)

        employeeListViewModel.employees.observe(viewLifecycleOwner, Observer {
            employeeListAdapter.submitList(it)
        })

        employeeListViewModel.status.observe(viewLifecycleOwner, Observer {
            populateUI(it)
        })

        refreshLayout.setOnRefreshListener {
            loadError.visibility = View.GONE
            employeeListViewModel.refresh()
            loading.visibility = View.GONE
            refreshLayout.isRefreshing = false
            if (!isConnected) {
                showToast()
            }
        }

        val connectionToInternet = ConnectToInternet(activity)
        connectionToInternet.observe(requireActivity(), object : Observer<Boolean> {
            override fun onChanged(@Nullable connection: Boolean) {
                isConnected = connection
                if (!connection) {
                    showToast()
                }
            }
        })
    }

    private fun populateUI(status: EmployeeApiStatus?) {
        when(status) {
            EmployeeApiStatus.LOADING -> {
                loading.visibility = View.VISIBLE
                loadError.visibility = View.GONE
            }
            EmployeeApiStatus.ERROR -> {
                loadError.visibility = View.VISIBLE
                loading.visibility = View.GONE
            }
            EmployeeApiStatus.DONE -> {
                loading.visibility = View.GONE
                loadError.visibility = View.GONE
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            EmployeeListFragment()
    }

    private fun showToast() {
        Toast.makeText(
            activity,
            String.format(getString(R.string.you_are_offline)),
            Toast.LENGTH_SHORT
        ).show();
    }
}