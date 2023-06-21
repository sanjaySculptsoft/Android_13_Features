package com.example.android13_features.copy_and_paste

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.android13_features.databinding.ActivityCopyAndPasteBinding
import com.google.android.material.snackbar.Snackbar

class CopyAndPasteActivity : AppCompatActivity() {
    var binding:ActivityCopyAndPasteBinding? = null

    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, CopyAndPasteActivity::class.java)
            context.startActivity(starter)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCopyAndPasteBinding.inflate(layoutInflater)
        binding?.apply {
            setContentView(this.root)
        }

        // Setup button click listeners
        binding?.buttonCopy?.setOnClickListener {
            copyPlainTextToClipBoard(
                context = this,
                label = "normal_text",
                text = "This is copied text!",
            )
//            textCopyThenPost("Hello World How is it going on?")
        }
        binding?.buttonCopySensitive?.setOnClickListener {
            copyPlainTextToClipBoard(
                context = this,
                label = "sensitive_text",
                text = "this is sensetive text ",
                isSensitive = true,
            )
        }
    }


    // Avoid duplicate notifications
    // show custom toast or snackbar when copied to Avoid duplicate notifications Android 12 and lower
    fun textCopyThenPost(textCopied:String) {
        val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        // When setting the clipboard text.
        clipboardManager.setPrimaryClip(ClipData.newPlainText   ("", textCopied))
        // Only show a toast for Android 12 and lower.
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
            Toast.makeText(this , "Copied", Toast.LENGTH_SHORT).show()
    }
    private fun copyPlainTextToClipBoard(
        context: Context,
        label: String,
        text: String,
        isSensitive: Boolean = false,
    ) {
        val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(
            label,
            text
        ).apply {
            if (isSensitive) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    description.extras = PersistableBundle().apply {
                        putBoolean(ClipDescription.EXTRA_IS_SENSITIVE, true)
                    }
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    description.extras = PersistableBundle().apply {
                        putBoolean("android.content.extra.IS_SENSITIVE", true)
                    }
                }
            }
        }
        clipboardManager.setPrimaryClip(clipData)
        if (clipboardManager.hasPrimaryClip()) {
            val item = clipboardManager.primaryClip?.getItemAt(0)
            // Gets the clipboard as text.
//            Log.d("TAGG", "copyPlainTextToClipBoard: ${item?.text }")
        }
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) {
            binding?.root?.rootView?.let {
                Snackbar.make(
                    it,
                    "Text copied!",
                    Snackbar.LENGTH_SHORT,
                ).show()
            }
        }
    }
}