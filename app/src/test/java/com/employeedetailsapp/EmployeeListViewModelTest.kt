package com.employeedetailsapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.employeedetailsapp.employeelist.EmployeeListViewModel
import com.employeedetailsapp.model.Employee
import com.employeedetailsapp.network.ApiHelper
import com.employeedetailsapp.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EmployeeListViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiHelper: ApiHelper

    @Mock
    private lateinit var employeeListObserver: Observer<List<Employee>>

    @Before
    fun setUp() {
        // do something if required
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        val employeeList = listOf(
            Employee(
                1, "John",
                "bad man", "male", "10th Feb", "", ""
            )
        )
        testCoroutineRule.runBlockingTest {
            doReturn(employeeList)
                .`when`(apiHelper)
                .getEmployeeList()
            val viewModel = EmployeeListViewModel(apiHelper)
            viewModel.employees.observeForever(employeeListObserver)
            verify(apiHelper).getEmployeeList()
            verify(employeeListObserver).onChanged(employeeList)
            viewModel.employees.removeObserver(employeeListObserver)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"
            doThrow(RuntimeException(errorMessage))
                .`when`(apiHelper)
                .getEmployeeList()
            val viewModel = EmployeeListViewModel(apiHelper)
            viewModel.employees.observeForever(employeeListObserver)
            verify(apiHelper).getEmployeeList()
            verify(employeeListObserver).onChanged(emptyList())
            viewModel.employees.removeObserver(employeeListObserver)
        }
    }

    @After
    fun tearDown() {
        // do something if required
    }
}