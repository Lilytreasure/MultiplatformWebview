import platform.Foundation.NSTimer

actual class TimerManager {
    private var timer: NSTimer? = null

    actual fun scheduleTimer(
        visibilityDuration: Long,
        onTimerTriggered: () -> Unit
    ) {
        timer = NSTimer.scheduledTimerWithTimeInterval(
            visibilityDuration.toDouble() / 1000,
            repeats = false,
            block = { onTimerTriggered() }
        )
    }

    actual fun cancelTimer() {
        timer?.invalidate()
        timer = null
    }
}