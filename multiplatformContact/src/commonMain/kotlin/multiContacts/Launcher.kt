package multiContacts

expect class Launcher(
    onLaunch: () -> Unit,
) {
    fun launch()
}