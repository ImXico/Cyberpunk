@file:JvmName("PerformanceProfiler")

package cyberpunk.profiler

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.PerformanceCounter

/**
 * Profiles the execution time of some block of code and log the results.
 * You should reset the counter manually when you're done using it.
 * @param operation block of code you want to profile.
 */
inline fun PerformanceCounter.profile(operation: () -> Any) {
  // Care for the cold start.
  if (this.time.count == 0) tick()
  this.start()
  operation()
  this.stop()
  this.tick()
  prettyPrint(this)
}

/**
 * Logs a [PerformanceCounter] information as an organized block.
 * @param performanceCounter the counter with the log entries.
 */
fun prettyPrint(performanceCounter: PerformanceCounter) = with(performanceCounter) {
  Gdx.app.log(name, "-----------------------------------------------------")
  Gdx.app.log(name, "Number of Calls: ${time.count}")
  Gdx.app.log(name, "Latest  OP Time: ${time.latest}")
  Gdx.app.log(name, "Average OP Time: ${time.average}")
  Gdx.app.log(name, "Maximum OP Time: ${time.max}")
  Gdx.app.log(name, "Minimum OP Time: ${time.min}")
  Gdx.app.log(name, "-----------------------------------------------------\n")
}