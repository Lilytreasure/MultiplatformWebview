package multiplatformWebView

import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat


@Composable
actual fun WebViewEngine(
    htmlContent: String,
    isLoading: (isLoading: Boolean) -> Unit,
    onUrlClicked: (url: String) -> Unit,
    onCreated: () -> Unit,
    onDispose: () -> Unit,
) {
    val context = LocalContext.current
    val color = ContextCompat.getColor(context, androidx.core.R.color.notification_icon_bg_color)
    var isLoadingFinished by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                WebView(context).apply {
                    // Enable WebView debugging for development
                    onCreated()
                    //Todo---- remove debugger when publishing
                    WebView.setWebContentsDebuggingEnabled(true)

                    scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
                    setBackgroundColor(color)
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    // Enable horizontal scrolling and adjust settings
                    settings.apply {
                        loadWithOverviewMode = true
                        useWideViewPort = true
                        layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
                        javaScriptEnabled = true // Enable JavaScript if needed
                        domStorageEnabled = true // Enable DOM storage for complex pages
                        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW // Allow mixed content
                        userAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36"
                        setSupportZoom(true) // Enable zoom support
                        builtInZoomControls = true // Enable built-in zoom controls (e.g., pinch-to-zoom)
                        displayZoomControls = false
                    }

                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(
                            view: WebView?,
                            url: String?,
                            favicon: Bitmap?
                        ) {
                            isLoading(true)
                        }
                        override fun onPageFinished(view: WebView?, url: String?) {
                            view?.scrollTo(view.contentHeight, 0)
                            isLoadingFinished = true
                            isLoading(false)
                        }

                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            return if (request?.url.toString().contains("jpg") ||
                                request?.url.toString().contains("png") ||
                                request?.url.toString().contains("attachment_id")
                            ) {
                                true
                            } else {
                                onUrlClicked(request?.url.toString())
                                true
                            }
                        }
                    }
                }
            },
            update = { webView ->
                webView.loadUrl(htmlContent)
            },
            onRelease = {
                onDispose()
            }
        )
    }
}