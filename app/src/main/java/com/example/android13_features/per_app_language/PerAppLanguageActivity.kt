package com.example.android13_features.per_app_language

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.android13_features.R
import com.example.android13_features.databinding.ActivityPerAppLanguageBinding
import com.example.android13_features.databinding.ActivitySettingPlacementApiBinding
import android.app.LocaleManager
import android.content.Intent
import android.os.LocaleList
import androidx.annotation.RequiresApi
import java.util.Locale

@SuppressLint("InlinedApi")
class PerAppLanguageActivity : AppCompatActivity() {
    private var binding: ActivityPerAppLanguageBinding? = null
    private var localeManager: LocaleManager? = null

    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, PerAppLanguageActivity::class.java)
            context.startActivity(starter)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerAppLanguageBinding.inflate(layoutInflater)
        binding?.apply {
            setContentView(this.root)
            lifecycleOwner = this@PerAppLanguageActivity
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            localeManager =
                getSystemService(Context.LOCALE_SERVICE) as LocaleManager
        }
        setonclickListener()


    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun setonclickListener(){
        binding?.buttonLangEn?.setOnClickListener {
            localeManager?.applicationLocales = LocaleList(Locale.forLanguageTag("en"))
        }
        binding?.buttonLangInd?.setOnClickListener {
            localeManager?.applicationLocales = LocaleList(Locale.forLanguageTag("id-ID"))
        }
        binding?.buttonLangSpanish?.setOnClickListener {
            localeManager?.applicationLocales = LocaleList(Locale.forLanguageTag("id-ES"))
        }
        binding?.buttonLangReset?.setOnClickListener {
            localeManager?.applicationLocales = LocaleList.getEmptyLocaleList()
        }
    }
    override fun onResume() {
        super.onResume()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val language = when (localeManager?.applicationLocales?.toLanguageTags()) {
                "en" -> "English"
                "id-ID" -> "Indonesian"
                "id-ES" -> "Spanish"
                else -> "Not Set"
            }
            Log.d("Current_Lang", "onResume: ${localeManager?.applicationLocales?.toLanguageTags()}")
            binding?.textCurrentLanguage?.text =
                "Current Language: $language"
        }
    }
}