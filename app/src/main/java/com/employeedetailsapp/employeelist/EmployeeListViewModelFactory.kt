package com.employeedetailsapp.employeelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.employeedetailsapp.network.ApiHelper

class EmployeeListViewModelFactory(private val apiHelper: ApiHelper):
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeListViewModel::class.java)) {
            return EmployeeListViewModel(apiHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}