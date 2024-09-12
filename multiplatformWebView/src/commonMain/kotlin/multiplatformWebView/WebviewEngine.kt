package multiplatformWebView

import androidx.compose.runtime.Composable

/**
 * url represents the link to the webpage.
 * isLoading represents the loading status of the webView.
 * onUrlClicked represents what happens when the url is Clicked.
 * OnCreated represents the initialization of the webView.
 * OnDispose represents when the webView is Dismissed.
 * **/

@Composable
expect fun WebViewEngine(
    url: String,
    isLoading: (isLoading: Boolean) -> Unit,
    onUrlClicked: (url: String) -> Unit,
    onCreated: () -> Unit,
    onDispose: () -> Unit,
)