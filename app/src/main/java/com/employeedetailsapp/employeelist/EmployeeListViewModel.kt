package com.employeedetailsapp.employeelist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.employeedetailsapp.model.Employee
import com.employeedetailsapp.network.EmployeeApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

enum class EmployeeApiStatus { LOADING, ERROR, DONE }

/**
 * The [ViewModel] that is attached to the [EmployeeListFragment].
 */
class EmployeeListViewModel: ViewModel() {
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

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

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
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            var getEmployeeDeferred = EmployeeApiService.retrofitService.getEmployeeList()
            try {
                // this will run on a thread managed by Retrofit
                val listResult = getEmployeeDeferred.await()
                _status.value = EmployeeApiStatus.DONE
                _employees.value = listResult
                Timber.i( "Data loaded successfully from network")
            } catch (e: Exception) {
                if (_employees.value.isNullOrEmpty()) {
                    _status.value = EmployeeApiStatus.ERROR
                    _employees.value = ArrayList()
                }
                Timber.i( "Error loading data from network")
            }
        }
    }

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}