package multiContacts

import android.Manifest
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

/**
 * @param Launcher  used to invoke the contacts picker
 * @param getPhoneNumberFromUriData is used to extract phone number  from the uri
 */


@Composable
actual fun pickMultiplatformContacts(onResult: (String) -> Unit): Launcher {
    val context = LocalContext.current
    val launcherCustom: Launcher?
    val resultContacts = remember { mutableStateOf<Uri?>(null) }
    var phoneNumber by remember { mutableStateOf<String?>(null) }
    val resultContactsValue = remember { mutableStateOf(false) }
    val launcherContacts = rememberLauncherForActivityResult(ActivityResultContracts.PickContact()) {
            resultContacts.value = it
        }
    val launcherPermission = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        resultContactsValue.value = isGranted
    }
    LaunchedEffect(resultContacts.value) {
        resultContactsValue.value.let {uri->
            println("Result changed;;;;;;;;;;"+ uri)

        }
        resultContacts.value?.let { uri ->
            phoneNumber = getPhoneNumberFromUriData(context, uri)
        }
    }
    phoneNumber?.let {
        onResult(it)
    }
    launcherCustom = remember {
        Launcher(onLaunch = {
            launcherPermission.launch(Manifest.permission.READ_CONTACTS)
            if(resultContactsValue.value){
                launcherContacts.launch()
            }else{
                launcherPermission.launch(Manifest.permission.READ_CONTACTS)
            }
        })
    }
    return launcherCustom
}

fun getPhoneNumberFromUriData(context: Context, uri: Uri): String? {
    val contentResolver = context.contentResolver
    val cursor: Cursor? =
        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
            arrayOf(uri.lastPathSegment),
            null
        )

    var phoneNumber: String? = null
    cursor?.use {
        if (it.moveToFirst()) {
            phoneNumber = it.getString(
                it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
            )
        }
    }
    return phoneNumber
}

