package com.example.android13_features.downgradable_permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.android13_features.R
import com.example.android13_features.databinding.ActivityDowngradablePermissionBinding
import com.example.android13_features.databinding.ActivityPerAppLanguageBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess
// add permission in manifest
//<uses-permission android:name="android.permission.CAMERA" />
//<uses-permission android:name="android.permission.CALL_PHONE" />
class DowngradablePermissionActivity : AppCompatActivity() {
    private var binding: ActivityDowngradablePermissionBinding? = null

    private var snackBar: Snackbar? = null
    private lateinit var requestPermissionsLauncher: ActivityResultLauncher<Array<String>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDowngradablePermissionBinding.inflate(layoutInflater)
        binding?.apply {
            setContentView(this.root)
            lifecycleOwner = this@DowngradablePermissionActivity
        }
        // Sets up permissions request launcher.
        requestPermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            refreshUI()
        }

        // Initialize UI.
        initializeUI()

        // Refresh UI.
        refreshUI()
    }

    /**
     * Initialize UI elements for the first time.
     */
    private fun initializeUI() {
       binding?.buttonRequestPermission?.setOnClickListener {
            if (requiredPermissions.all { isPermissionGranted(it) }) {
                refreshUI()
            } else {
                requestPermissionsLauncher.launch(requiredPermissions)
            }
        }
        binding?.buttonRevokeCameraPermission?.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                revokeSelfPermissionOnKill(Manifest.permission.CAMERA)
                showRevokeSuccessSnackBar()
            }
        }
        binding?.buttonRevokeCallPermission?.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                revokeSelfPermissionOnKill(Manifest.permission.CALL_PHONE)
                showRevokeSuccessSnackBar()
            }
        }
        binding?.buttonRevokeAllPermissions?.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                revokeSelfPermissionsOnKill(requiredPermissions.toList())
                showRevokeSuccessSnackBar()
            }
        }
    }

    /**
     * Refresh UI elements.
     */
    private fun refreshUI() {
        val isCameraPermissionGranted = isPermissionGranted(Manifest.permission.CAMERA)
        val isCallPermissionGranted = isPermissionGranted(Manifest.permission.CALL_PHONE)

        // Updates permission texts
        binding?.textCameraPermission?.text = if (isCameraPermissionGranted) "GRANTED" else "NOT GRANTED"
        binding?.textCallPermission?.text = if (isCallPermissionGranted) "GRANTED" else "NOT GRANTED"

        // Updates buttons isEnabled
        binding?.buttonRevokeCameraPermission?.isEnabled = isCameraPermissionGranted
        binding?.buttonRevokeCallPermission?.isEnabled = isCallPermissionGranted
        binding?.buttonRevokeAllPermissions?.isEnabled = isCallPermissionGranted && isCameraPermissionGranted
    }

    /**
     * Returns true if [permission] is granted.
     */
    private fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission,
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Shows [Snackbar] telling user to restart the app.
     */
    private fun showRevokeSuccessSnackBar() {
        snackBar?.dismiss()
        snackBar = Snackbar.make(
            findViewById<View>(android.R.id.content).rootView,
            "Permission has been revoked! Restart app to see the change.",
            Snackbar.LENGTH_INDEFINITE,
        ).apply {
            setAction("Quit App") {
                exitProcess(0)
            }
        }
        snackBar?.show()
    }

    companion object {
        /**
         * App's required permissions.
         */
        val requiredPermissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
        )
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, DowngradablePermissionActivity::class.java)
            context.startActivity(starter)
        }
    }
}