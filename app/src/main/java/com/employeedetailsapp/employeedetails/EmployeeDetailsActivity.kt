package com.employeedetailsapp.employeedetails

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.employeedetailsapp.R
import com.employeedetailsapp.util.IMAGE_JPEG
import kotlinx.android.synthetic.main.toolbar.*


class EmployeeDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        val image = intent.getByteArrayExtra(IMAGE_JPEG)
        if (savedInstanceState == null) {
            val fragmentManager = supportFragmentManager
            val employeeDetailsFragment = EmployeeDetailsFragment.newInstance(image)

            fragmentManager.beginTransaction()
                .add(R.id.employee_detail_fragment, employeeDetailsFragment).commit()
        }

        back_icon.visibility = View.VISIBLE
        people_db_text.visibility = View.VISIBLE
        back_icon.setOnClickListener {
            onBackPressed()
        }
        people_db_text.setOnClickListener {
            onBackPressed()
        }

    }

}