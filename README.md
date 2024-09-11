<p align="center">
  <a href="https://central.sonatype.com/artifact/io.github.lilytreasure/multiplatformWebView"><img alt="Profile" src="https://badgen.net/badge/Maven Central/v1.0.1/blue?icon=github"/></a>
</p><br>

<p align="center">
👻 Multiplatform Contacts is a straight forward library used to get  Contacts in Android and iOS.
</p><br>

### sample iOS

<p align="center">
<img <img src="https://github.com/Lilytreasure/MultiplatformContacts/assets/78819932/d3150a0d-1578-4c29-9c59-7d8d83f3dd2e.gif?raw=true" width="268"/>
</p>

### sample Android

<p align="center">
<img <img src="https://github.com/Lilytreasure/MultiplatformContacts/assets/78819932/472d2a66-acca-467a-aefc-b27cbd18b06a.gif?raw=true" width="268"/>
</p>


### Gradle

You can add a dependency inside the `androidMain` or `commonMain` source set:

```gradle
commonMain.dependencies {
    implementation("io.github.lilytreasure:multiplatformWebView:1.0.1")
}
```

## Usage

```kotlin
 var isLoadingDescription by remember { mutableStateOf(false) }
var offsetX by remember { mutableStateOf(0f) }
var offsetY by remember { mutableStateOf(0f) }
Scaffold(modifier = Modifier.fillMaxSize()) {
    Column(
        modifier = Modifier
            .padding(it)
            .fillMaxSize()
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .windowInsetsPadding(WindowInsets.systemBars)
                .windowInsetsPadding(WindowInsets.ime)
        ) {
            WebViewEngine(
                url = "https://github.com/Lilytreasure",
                isLoading = { loadDelegate ->
                    isLoadingDescription = loadDelegate
                },
                onUrlClicked = { url -> },
                onCreated = {},
                onDispose = {}
            )
            if (isLoadingDescription) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                )
            }
            if (isLoadingDescription) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
            //Enabled dragging the dismissing button to not get in the way of the WebView content
            IconButton(
                onClick = {
                    // Handle onClick
                },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume() // Consume the gesture to prevent interference with other gestures
                            offsetX += dragAmount.x
                            offsetY += dragAmount.y
                        }
                    }
            ) {
                Icon(
                    Icons.Filled.Cancel,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}

```
