package multiplatformWebView

import androidx.compose.runtime.Composable

@Composable
expect fun WebViewEngine(
    url: String,
    isLoading: (isLoading: Boolean) -> Unit,
    onUrlClicked: (url: String) -> Unit,
    onCreated: () -> Unit,
    onDispose: () -> Unit,
)