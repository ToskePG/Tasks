package llc.amplitudo.amplitudo_akademija.ui.done_tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import llc.amplitudo.amplitudo_akademija.core.utils.NetworkResponse
import llc.amplitudo.amplitudo_akademija.data.remote.models.Task
import llc.amplitudo.amplitudo_akademija.data.remote.repos.TaskRepository

class DoneTasksViewModel : ViewModel() {

    private val taskRepository = TaskRepository()

    private val _doneTasksSharedFlow = MutableSharedFlow<List<Task>>()

    val doneTasksSharedFlow: SharedFlow<List<Task>> = _doneTasksSharedFlow

    private val _errorMessageChannel = Channel<String>()
    val errorMessageFlow: Flow<String> = _errorMessageChannel.receiveAsFlow()

    private val _loadingMessageChannel = Channel<Boolean>()
    val loadingMessageFlow : Flow<Boolean> = _loadingMessageChannel.receiveAsFlow()

    fun getDoneTasks() {
        viewModelScope.launch {
            taskRepository.getTasks().collectLatest { networkResponse ->
                val data = networkResponse.data
                when(networkResponse){
                    is NetworkResponse.Success -> {
                        data?.filter { task ->
                            task.isCompleted == true
                        }?.let { tasks->
                            delay(2790)
                            _doneTasksSharedFlow.emit(tasks)
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