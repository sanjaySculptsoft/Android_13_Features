package com.example.android13_features

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.android13_features.copy_and_paste.CopyAndPasteActivity
import com.example.android13_features.databinding.ActivityMainBinding
import com.example.android13_features.downgradable_permission.DowngradablePermissionActivity
import com.example.android13_features.per_app_language.PerAppLanguageActivity
import com.example.android13_features.setting_placement_api.SettingPlacementApiActivity
import com.example.android13_features.ui.theme.Android13_featuresTheme
import android.os.Build
import android.widget.Toast
import android.window.OnBackInvokedCallback
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.android13_features.predictive_back_gesture.PredictiveBackGestureActivity

class MainActivity : ComponentActivity() {
    var binding:ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding?.apply {
            setContentView(root)
        }

        binding?.copyAndPatse?.setOnClickListener {
            CopyAndPasteActivity.start(this)
        }
        binding?.settingPlacement?.setOnClickListener {
            SettingPlacementApiActivity.start(this)
        }
        binding?.perAppLanguage?.setOnClickListener {
            PerAppLanguageActivity.start(this)
        }
        binding?.downGradablePermissionDemo?.setOnClickListener {
            DowngradablePermissionActivity.start(this)
        }
        binding?.backGestureBtn?.setOnClickListener {
            PredictiveBackGestureActivity.start(this)
        }

    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    Android13_featuresTheme {
//        Greeting("Android")
//    }
//}