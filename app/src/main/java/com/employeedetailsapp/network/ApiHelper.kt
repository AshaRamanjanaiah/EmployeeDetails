package com.employeedetailsapp.network

import com.employeedetailsapp.model.Employee

interface ApiHelper {

    suspend fun getEmployeeList(): List<Employee>

}