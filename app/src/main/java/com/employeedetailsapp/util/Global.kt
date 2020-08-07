package com.employeedetailsapp.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Base64
import java.io.ByteArrayOutputStream

const val IMAGE_JPEG = "image_jpeg"

/**
 * Converts Base64 string to Bitmap
 * @param encodedImage base64 encode image will to passed as parameter
 * @return returns decoded bitmap
 */
fun convertBase64StringToBitmap(encodedImage: String?): Bitmap {
    val decodedString: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
    val decodedByte =
        BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    return decodedByte
}

/**
 * Converts Bitmaps to jpeg and returns ByteArray
 * @param encodedImage bitmap should be passed as parameter
 * @return returns decoded ByteArray which can be used to transfer image
 * from one activity to another
 */
fun convertBitmapToJpeg(bitmap: Bitmap): ByteArray {
    val bs = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs)
    return bs.toByteArray()
}