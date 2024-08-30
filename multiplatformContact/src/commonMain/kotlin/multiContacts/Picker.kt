package multiContacts

import androidx.compose.runtime.Composable

@Composable
expect fun pickMultiplatformContacts(onResult: (String) -> Unit): Launcher