package com.employeedetailsapp.model

data class Employee(
    val id: Int,
    val first_name: String?,
    val last_name: String?,
    val gender: String?,
    val birth_date: String?,
    val image: String?,
    val thumbImage: String?
) {
}