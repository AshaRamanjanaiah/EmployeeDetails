package com.employeedetailsapp.network

class ApiHelperImpl(private val apiService: EmployeeApiEndPoint) : ApiHelper {

    override suspend fun getEmployeeList() = apiService.getEmployeeList()

}