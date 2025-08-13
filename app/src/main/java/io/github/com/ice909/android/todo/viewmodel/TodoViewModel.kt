package io.github.com.ice909.android.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.com.ice909.android.todo.datastore.taskDataStore
import io.github.com.ice909.android.todo.model.Task
import io.github.com.ice909.android.todo.model.toProto
import io.github.com.ice909.android.todo.model.toTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TodoViewModel(app: Application) : AndroidViewModel(app) {
    private val _todoList = MutableStateFlow<List<Task>>(emptyList())
    val todoList: StateFlow<List<Task>> = _todoList

    private val dataStore = app.taskDataStore

    init {
        // 启动时加载本地
        viewModelScope.launch {
            val proto = dataStore.data.first()
            _todoList.value = proto.tasksList.map { it.toTask() }
        }
    }

    fun addTask(task: Task) {
        _todoList.value = listOf(task) + _todoList.value

        saveToLocal()
    }

    fun toggleCompleted(id: String) {
        _todoList.value = _todoList.value.map {
            if (it.id == id) it.copy(completed = !it.completed) else it
        }
        saveToLocal()
    }

    private fun saveToLocal() {
        viewModelScope.launch {
            dataStore.updateData { old ->
                old.toBuilder().clearTasks().addAllTasks(_todoList.value.map { it.toProto() }).build()
            }
        }
    }

}