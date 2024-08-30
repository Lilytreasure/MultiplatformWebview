expect class TimerManager() {
    fun scheduleTimer(
        visibilityDuration: Long,
        onTimerTriggered: () -> Unit
    )
    fun cancelTimer()
}