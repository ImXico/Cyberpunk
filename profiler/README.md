The profiler module offers practical wrappers to use on top of libGDX [profiling utilities](https://github.com/libgdx/libgdx/wiki/Profiling).

### PerformanceProfiler

The [`PerformanceProfiler`](https://github.com/ImXico/Cyberpunk/blob/master/text/src/main/kotlin/cyberpunk/profiler/PerformanceProfiler.kt) is a tiny wrapper to use on top of
libGDX `PerformanceCounter`.

Imagine that, somewhere in your code, you call an expensive operation:

```kotlin
someExpensiveOperation()
```

To profile it, you need to first instantiate a `PerformanceCounter`:

```kotlin
private val performanceProfiler = PerformanceCounter("My Profiler")
```

Now, all that you need to do is wrap the operation inside the profiler's `profile` call:

```kotlin
performanceProfiler.profile {
  someExpensiveOperation()
}
```

After each call to `someExpensiveOperation`, you'll see the output in the log:

```
My Profiler: -----------------------------------------------------
My Profiler: Number of Calls: 7
My Profiler: Latest  OP Time: 1.0035133
My Profiler: Average OP Time: 1.002641
My Profiler: Maximum OP Time: 1.0029575
My Profiler: Minimum OP Time: 1.0021613
My Profiler: -----------------------------------------------------
```

