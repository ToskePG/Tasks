package llc.amplitudo.amplitudo_akademija.ui.all_tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import llc.amplitudo.amplitudo_akademija.core.utils.NetworkResponse
import llc.amplitudo.amplitudo_akademija.data.remote.models.Task
import llc.amplitudo.amplitudo_akademija.data.remote.repos.TaskRepository


class AllTasksViewModel : ViewModel() {

    private val taskRepository = TaskRepository()

    // Backing property to avoid state updates from other classes
    private val _tasksSharedFlow = MutableSharedFlow<List<Task>>()

    // The UI collects from this StateFlow to get its state updates
    val tasksSharedFlow: SharedFlow<List<Task>> = _tasksSharedFlow

    private val _errorMessageChannel = Channel<String>()
    val errorMessageFlow: Flow<String> = _errorMessageChannel.receiveAsFlow()

    private val _loadingMessageChannel = Channel<Boolean>()
    val loadingMessageFlow: Flow<Boolean> = _loadingMessageChannel.receiveAsFlow()

    fun getTasks() {
        viewModelScope.launch {
            taskRepository.getTasks().collectLatest { networkResponse ->
                val data = networkResponse.data
                when (networkResponse) {
                    is NetworkResponse.Success -> {
                        data?.let { tasks ->
                            delay(2790)
                            _tasksSharedFlow.emit(tasks)
                        }
                        _loadingMessageChannel.send(false)
                    }
                    is NetworkResponse.Error -> {
                        networkResponse.message?.let { message ->
                            _errorMessageChannel.send(message)
                        }
                        _loadingMessageChannel.send(false)
                    }
                    is NetworkResponse.Loading -> {
                        _loadingMessageChannel.send(true)
                    }
                }
            }
        }
    }
}