package com.example.dermoscan.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import java.io.FileDescriptor
import java.io.IOException

//  takes Message and returns a Toast with the message
fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(
        context,
        message,
        duration
    ).show()
}


//  Launch camera intent
 fun dispatchLaunchCameraIntent(cameraLauncher: ActivityResultLauncher<Intent>) {
    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    try {
        cameraLauncher.launch(cameraIntent)
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
    }
}

//  Open Gallery Intent
 fun dispatchLaunchGalleryIntent(galleryLauncher:  ActivityResultLauncher<Intent>) {
    val openGalleryIntent =
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    try {
        openGalleryIntent.type = "image/*"
        openGalleryIntent.action = Intent.ACTION_GET_CONTENT
        galleryLauncher.launch(openGalleryIntent)
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
    }
}

//  takes Bitmap and returns resized bitmap to specified width and height
fun scaleImage(bitmap: Bitmap?, mInputSize: Int): Bitmap {
    val widthOriginal = bitmap!!.width
    val heightOriginal = bitmap.height
    val scaleWidth = mInputSize.toFloat() / widthOriginal
    val scaleHeight = mInputSize.toFloat() / heightOriginal
    val matrix = Matrix()
    matrix.postScale(scaleWidth, scaleHeight)
    return Bitmap.createBitmap(bitmap, 0, 0, widthOriginal, heightOriginal, matrix, true)
}

//  takes URI of the image and returns bitmap
fun uriToBitmap(context: Context,selectedFileUri: Uri): Bitmap? {
    try {
        val parcelFileDescriptor =
            context.contentResolver?.openFileDescriptor(selectedFileUri, "r")
        val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}