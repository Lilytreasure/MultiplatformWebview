package multiplatformWebView

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRect
import platform.Foundation.NSError
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.UIView
import platform.WebKit.WKDownload
import platform.WebKit.WKNavigation
import platform.WebKit.WKNavigationAction
import platform.WebKit.WKNavigationActionPolicy
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKNavigationTypeLinkActivated
import platform.WebKit.WKWebView
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun  WebViewEngine(
    url: String,
    isLoading: (isLoading: Boolean) -> Unit,
    onUrlClicked: (url: String) -> Unit,
    onCreated: () -> Unit,
    onDispose: () -> Unit,
) {
    val webView = remember { WKWebView() }
    val navigationDelegate = rememberWebViewDelegate(onUrlClicked, isLoading)
    LaunchedEffect( url) {
        webView.navigationDelegate = navigationDelegate
        webView.loadRequest(NSURLRequest(NSURL(string =  url)))
      
    }
    UIKitView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        factory = {
            val container = UIView()
            container.addSubview(webView)
            onCreated()
            container

        },
        onResize = { view: UIView, rect: CValue<CGRect> ->
            CATransaction.begin()
            CATransaction.setValue(true, kCATransactionDisableActions)
            view.layer.setFrame(rect)
            webView.setFrame(rect)
            CATransaction.commit()
        },
        onRelease = {
            onDispose()
        }
    )
}

@Composable
private fun rememberWebViewDelegate(
    onUrlClicked: (String) -> Unit,
    onLoadingChanged: (Boolean) -> Unit
): WKNavigationDelegateProtocol {
    return object : NSObject(), WKNavigationDelegateProtocol {
        override fun webView(
            webView: WKWebView,
            didStartProvisionalNavigation: WKNavigation?
        ) {
            onLoadingChanged(true)
        }

        override fun webView(
            webView: WKWebView,
            navigationAction: WKNavigationAction,
            didBecomeDownload: WKDownload
        ) {
            onLoadingChanged(true)
        }

        override fun webView(
            webView: WKWebView,
            didFailNavigation: WKNavigation?,
            withError: NSError
        ) {
            onLoadingChanged(false)
        }

        override fun webView(
            webView: WKWebView,
            decidePolicyForNavigationAction: WKNavigationAction,
            decisionHandler: (WKNavigationActionPolicy) -> Unit
        ) {
            onLoadingChanged(false)
            val navigationType = decidePolicyForNavigationAction.navigationType
            val request = decidePolicyForNavigationAction.request

            when (navigationType) {
                WKNavigationTypeLinkActivated -> {
                    // Allow navigation to the URL
                    onUrlClicked(request.URL?.absoluteString ?: "")
                    decisionHandler(WKNavigationActionPolicy.WKNavigationActionPolicyAllow)
                }

                else -> {
                    decisionHandler(WKNavigationActionPolicy.WKNavigationActionPolicyAllow)
                }
            }
        }
    }
}