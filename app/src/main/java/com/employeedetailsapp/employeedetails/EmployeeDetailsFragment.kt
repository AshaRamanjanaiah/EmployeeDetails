package com.employeedetailsapp.employeedetails

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.employeedetailsapp.R
import com.employeedetailsapp.util.IMAGE_JPEG
import kotlinx.android.synthetic.main.fragment_employee_details.*


/**
 * Use the [EmployeeDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmployeeDetailsFragment : Fragment() {

    private var employeeImage: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            employeeImage = it.getByteArray(IMAGE_JPEG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(employeeImage == null) {
            image_not_found.visibility = View.VISIBLE
            image.visibility = View.GONE
        } else {
            image.visibility = View.VISIBLE
            val bmp = BitmapFactory.decodeByteArray(employeeImage, 0, employeeImage!!.size)
            image.setImageBitmap(bmp)
            image_not_found.visibility = View.GONE
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameter.
         *
         * @param employee Parameter object includes employee details.
         * @return A new instance of fragment EmployeeDetailsFragment.
         */

        @JvmStatic
        fun newInstance(image: ByteArray?) =
            EmployeeDetailsFragment().apply {
                arguments = Bundle().apply {
                    putByteArray(IMAGE_JPEG, image)
                }
            }
    }
}