package com.employeedetailsapp.network

import com.employeedetailsapp.model.Employee
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface EmployeeApiEndPoint {
    @GET("employees")
    suspend fun getEmployeeList(): List<Employee>
}