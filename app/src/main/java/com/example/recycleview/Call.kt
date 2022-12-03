package com.example.recycleview

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity

class Call {
    companion object {
        const val REQUEST_CODE = 42
    }
    fun callPhone(phoneNumber: String,con: Context) {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
        startActivity(con,intent,null)
    }

    fun checkPermission(phoneNumber: String, act: Activity,con: Context) {
        if (ContextCompat.checkSelfPermission(
                con,
                Manifest.permission.CALL_PHONE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    act,
                    Manifest.permission.CALL_PHONE
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    act,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    REQUEST_CODE
                )
            }
        } else {
            // Permission has already been granted
            callPhone(phoneNumber,con)
        }
    }
}