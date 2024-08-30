package multiContacts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MultiContacts(
    modifier: Modifier = Modifier,
) {
    var number by remember { mutableStateOf("") }
    val multiplatformContactsPicker = pickMultiplatformContacts(onResult = {
        number = it
    })
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = number)
        Button(modifier = Modifier.padding(top = 16.dp),
            onClick = {
                multiplatformContactsPicker.launch()
            }) {
            Text("Run")
        }
    }
}





