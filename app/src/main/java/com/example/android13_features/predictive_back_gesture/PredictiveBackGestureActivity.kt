package com.example.android13_features.predictive_back_gesture

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.window.OnBackInvokedCallback
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import com.example.android13_features.R

class PredictiveBackGestureActivity : AppCompatActivity() {
    private lateinit var onBackInvokedCallback: OnBackInvokedCallback

    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, PredictiveBackGestureActivity::class.java)
            context.startActivity(starter)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_predictive_back_gesture)

        // For Non-AndroidX:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onBackInvokedCallback = OnBackInvokedCallback {
                // Your app's onBackPressed logic goes here...
                Toast.makeText(this@PredictiveBackGestureActivity,"1" , Toast.LENGTH_SHORT).show()
            }
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT,
                onBackInvokedCallback,
            )
        }


        // For AndroidX:
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Your app's onBackPressed logic goes here...

            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        // You can enable/disable onBackPressedCallback by using `isEnabled` method:
        // onBackPressedCallback.isEnabled = false
    }
    override fun onDestroy() {
        super.onDestroy()
        // For Non-AndroidX:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onBackInvokedDispatcher.unregisterOnBackInvokedCallback(onBackInvokedCallback)
        }
    }
}
/**
 * Test the predictive back gesture animation
 * On your device, go to Settings > System > Developer options.
 * Select Predictive back animations.
 *
 * add this in manifest
 *     android:enableOnBackInvokedCallback="true"

 * */