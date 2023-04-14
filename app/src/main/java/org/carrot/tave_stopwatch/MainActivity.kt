import kotlinx.coroutines.*

//코루틴을 사용하여 스톱워치를 만들었는데.............
//실행이 안된.........

class Stopwatch {
    private var startTime: Long = 0L
    private var elapsedTime: Long = 0L
    private var job: Job? = null

    fun start(onTick: (String) -> Unit) { //시작 버튼
        startTime = System.currentTimeMillis()
        job = GlobalScope.launch {
            while (true) {
                delay(10)
                elapsedTime = System.currentTimeMillis() - startTime
                val timeStr = formatTime(elapsedTime)
                withContext(Dispatchers.Main) {
                    onTick(timeStr)
                }
            }
        }
    }

    fun pause() {
        job?.cancel()
    }

    fun reset() {
        job?.cancel()
        elapsedTime = 0L
    }

    private fun formatTime(time: Long): String {
        val minutes = (time / 1000 / 60).toString().padStart(2, '0')
        val seconds = (time / 1000 % 60).toString().padStart(2, '0')
        val milliseconds = (time % 1000 / 10).toString().padStart(2, '0')
        return "$minutes:$seconds.$milliseconds"
    }
}
