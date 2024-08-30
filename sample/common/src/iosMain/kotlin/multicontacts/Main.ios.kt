package multicontacts

import androidx.compose.ui.window.ComposeUIViewController
import multicontactSample.Sample
import platform.UIKit.UIViewController

@Suppress("FunctionName", "unused")
fun MainViewController(): UIViewController =
    ComposeUIViewController {
       Sample()
    }