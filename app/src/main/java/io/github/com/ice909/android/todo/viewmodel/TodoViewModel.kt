package io.github.com.ice909.android.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.com.ice909.android.todo.datastore.taskDataStore
import io.github.com.ice909.android.todo.model.Task
import io.github.com.ice909.android.todo.model.toProto
import io.github.com.ice909.android.todo.model.toTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(app: Application) : AndroidViewModel(app) {
    private val _todoList = MutableStateFlow<List<Task>>(emptyList())
    val todoList: StateFlow<List<Task>> = _todoList

    // 1. 待完成数量
    val activeCount: StateFlow<Int> = todoList
        .map { list -> list.count { !it.completed } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    // 2. 已完成数量
    val completedCount: StateFlow<Int> = todoList
        .map { list -> list.count { it.completed } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    // 3. 重要任务数量，假设 Task 有个 isImportant 字段
    val importantCount: StateFlow<Int> = todoList
        .map { list -> list.count { it.priority == 3 } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0)

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