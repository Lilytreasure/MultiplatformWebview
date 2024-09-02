package multiplatformWebView

import androidx.compose.runtime.Composable

@Composable
expect fun WebViewEngine(
    htmlContent: String,
    isLoading: (isLoading: Boolean) -> Unit,
    onUrlClicked: (url: String) -> Unit,
    onCreated: () -> Unit,
    onDispose: () -> Unit,
)