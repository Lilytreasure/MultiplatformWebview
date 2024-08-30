package multiContacts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Contacts.CNContact
import platform.ContactsUI.CNContactPickerDelegateProtocol
import platform.ContactsUI.CNContactPickerViewController
import platform.UIKit.UIApplication
import platform.darwin.NSObject



/**
 * @param Launcher  used to invoke the contacts picker
 * @param extractPhoneNumber is used to extract phone number, can be modified to extract phone number data of your choice
 */

typealias ContactPickedCallback = (String) -> Unit

@Composable
actual fun pickMultiplatformContacts(onResult: ContactPickedCallback): Launcher {
    val launcherCustom = remember {
        Launcher(onLaunch = {
            val picker = CNContactPickerViewController()
            picker.delegate = object : NSObject(), CNContactPickerDelegateProtocol {
                override fun contactPicker(picker: CNContactPickerViewController, didSelectContact: CNContact) {
                    val phoneNumber = didSelectContact.phoneNumbers.firstOrNull()?.toString()
                    val phoneNumberExTracted = phoneNumber?.let { extractPhoneNumber(it) }
                    onResult(phoneNumberExTracted ?: "No Phone Number")
                }
                override fun contactPickerDidCancel(picker: CNContactPickerViewController) {
                    onResult("")
                }
            }
            UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
                picker,
                true,
                null,
            )
        })
    }
    return launcherCustom
}
fun extractPhoneNumber(cnlabeledValue: String): String? {
    val regex = Regex("stringValue=([^,]+)")
    val matchResult = regex.find(cnlabeledValue)
    return matchResult?.groupValues?.get(1)
}

