package uz.usoft.merchapp.utils.extentions

import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


fun <T> Flow<T>.collectWithLifecycle(
    lifecycle: Lifecycle,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    collector: FlowCollector<T>
): Job {
    return lifecycle.coroutineScope.launch {
        lifecycle.repeatOnLifecycle(state) {
            collect(collector)
        }
    }
}

fun <T> Flow<T>.collectLatestWithLifecycle(
    lifecycle: Lifecycle,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    action: suspend (value: T) -> Unit
): Job {
    return lifecycle.coroutineScope.launch {
        lifecycle.repeatOnLifecycle(state) {
            collectLatest(action)
        }
    }
}