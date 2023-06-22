package com.example.android13_features.setting_placement_api

import android.annotation.SuppressLint
import android.app.StatusBarManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.android13_features.R
import com.example.android13_features.copy_and_paste.CopyAndPasteActivity
import com.example.android13_features.databinding.ActivityCopyAndPasteBinding
import com.example.android13_features.databinding.ActivitySettingPlacementApiBinding
import java.util.concurrent.Executor

class SettingPlacementApiActivity : AppCompatActivity() {
    private var binding:ActivitySettingPlacementApiBinding? = null

    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, SettingPlacementApiActivity::class.java)
            context.startActivity(starter)
        }
    }

    @SuppressLint("NewApi", "WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingPlacementApiBinding.inflate(layoutInflater)
        binding?.apply {
            setContentView(this.root)
            lifecycleOwner = this@SettingPlacementApiActivity
        }
        val requestTileExecutor = Executor {
            runOnUiThread {
                binding?.txtStatus?.text = "Requested Add tile Success"
            }
        }
        binding?.settingPlacement31?.setOnClickListener {
            startService(Intent(this, StartAppTileService::class.java))
        }


        binding?.settingPlacement?.setOnClickListener {
            Log.d("TAGG", "onCreate: ${Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU}")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val statusBarManager =
                    getSystemService(Context.STATUS_BAR_SERVICE) as StatusBarManager
                /**
                 * Important: Change the package name accordingly to your own package name!
                 */
                statusBarManager.requestAddTileService(
                    ComponentName(
                        this ,
                        StartAppTileService::class.java,
                    ),
                    "Android 13 features",
                    Icon.createWithResource(this, R.drawable.ic_launcher_foreground), requestTileExecutor) { resultCodeFailure ->
                    val resultFailureText =
                        when (val ret = RequestResult.findByResultCode(resultCodeFailure)) {
                            RequestResult.TILE_ADD_REQUEST_ERROR_APP_NOT_IN_FOREGROUND,
                            RequestResult.TILE_ADD_REQUEST_ERROR_BAD_COMPONENT,
                            RequestResult.TILE_ADD_REQUEST_ERROR_MISMATCHED_PACKAGE,
                            RequestResult.TILE_ADD_REQUEST_ERROR_NOT_CURRENT_USER,
                            RequestResult.TILE_ADD_REQUEST_ERROR_NO_STATUS_BAR_SERVICE,
                            RequestResult.TILE_ADD_REQUEST_ERROR_REQUEST_IN_PROGRESS,
                            RequestResult.TILE_ADD_REQUEST_RESULT_TILE_ADDED,
                            RequestResult.TILE_ADD_REQUEST_RESULT_TILE_ALREADY_ADDED,
                            RequestResult.TILE_ADD_REQUEST_RESULT_TILE_NOT_ADDED -> {
                                ret.name
                            }

                            else -> {
                                "unknown resultCodeFailure: $resultCodeFailure"
                            }
                        }
                    runOnUiThread {
                        binding?.txtStatus?.text = resultFailureText
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    "`requestAddTileService` will be called only in Android 13/Tiramisu.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }
}