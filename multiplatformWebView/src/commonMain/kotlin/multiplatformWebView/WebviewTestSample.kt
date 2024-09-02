package multiplatformWebView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WebViewTestSample(){
    Scaffold(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize()){
            WebViewEngine(
                htmlContent = "https://github.com/Lilytreasure",
                isLoading = {
                },
                onUrlClicked = { url ->
                },
                onCreated ={

                },
                onDispose = {

                }
            )
        }
    }
}