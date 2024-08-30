package com.dennis.multicontacts

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.dennis.multicontacts.ui.theme.ComposeSignatureTheme
import multicontactSample.Sample

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSignatureTheme(darkTheme = false) {
                Sample()
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
}
