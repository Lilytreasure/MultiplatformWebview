

<p align="center">
  <a href="https://central.sonatype.com/artifact/io.github.lilytreasure/multiplatformContacts"><img alt="Profile" src="https://badgen.net/badge/Maven Central/v1.0.1/blue?icon=github"/></a>
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

### On Android

Add the following on your Manifest file:
```xml
  <uses-permission android:name="android.permission.READ_CONTACTS" />
```

### Gradle

You can add a dependency inside the `androidMain` or `commonMain` source set:
```gradle
commonMain.dependencies {
    implementation("io.github.lilytreasure:multiplatformContacts:1.0.1")
}
```
## Usage


```kotlin
    var phoneNumber by remember { mutableStateOf("") }
    val multiplatformContactsPicker = pickMultiplatformContacts(onResult = {number->
        phoneNumber = number
    })

  Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                multiplatformContactsPicker.launch()
            }) {
                Text("Load contacts")
            }
            Text(text = phoneNumber)
        }

```
