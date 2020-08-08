package com.employeedetailsapp.employeelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.employeedetailsapp.model.Employee
import com.employeedetailsapp.network.ApiHelper
import kotlinx.coroutines.launch
import timber.log.Timber

enum class EmployeeApiStatus { LOADING, ERROR, DONE }

/**
 * The [ViewModel] that is attached to the [EmployeeListFragment].
 */
class EmployeeListViewModel(val apiHelper: ApiHelper) : ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<EmployeeApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<EmployeeApiStatus>
        get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of Employees
    // with new values
    private val _employees = MutableLiveData<List<Employee>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val employees: LiveData<List<Employee>>
        get() = _employees

    /**
     * Call getEmployeeList() on init so we can display status immediately.
     */
    init {
        _status.value = EmployeeApiStatus.LOADING
        refresh()
    }

    /**
     * Refreshes employee list by pulling data from network using Employees API Retrofit service.
     */
    fun refresh() {
        getEmployeeList()
    }

    /**
     * Gets Employees list information from the Employees API Retrofit service and
     * updates the [Employees] [List] and [Status] [LiveData]. The Retrofit service
     * returns a coroutine Deferred, which we await to get the result of the transaction.
     */
    private fun getEmployeeList() {
        viewModelScope.launch {
            try {
                var getEmployeeDeferred = apiHelper.getEmployeeList()
                _status.postValue(EmployeeApiStatus.DONE)
                _employees.postValue(getEmployeeDeferred)
                Timber.i("Data loaded successfully from network")
            } catch (e: Exception) {
                if (_employees.value.isNullOrEmpty()) {
                    _status.postValue(EmployeeApiStatus.ERROR)
                    _employees.postValue(ArrayList())
                }
                Timber.i("Error loading data from network")
            }
        }
    }

}