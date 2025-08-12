package io.github.com.ice909.android.todo.viewmodel

import androidx.lifecycle.ViewModel
import io.github.com.ice909.android.todo.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TodoViewModel : ViewModel() {
    // 用于演示的本地数据
    private val _todoList = MutableStateFlow(sampleData())
    val todoList: StateFlow<List<Task>> = _todoList

    companion object {
        private fun sampleData() = listOf(
            Task(
                id = "1",
                title = "完成项目报告",
                content = "",
                parentId = "",
                sortOrder = 0,
                completed = false
            ),
            Task(
                id = "2",
                title = "分析数据",
                content = "",
                parentId = "",
                sortOrder = 0,
                completed = false
            ),
            Task(
                id = "3",
                title = "撰写报告",
                content = "",
                parentId = "",
                sortOrder = 0,
                completed = false
            ),
            Task(
                id = "4",
                title = "准备明天的会议",
                content = "",
                parentId = "",
                sortOrder = 0,
                completed = false
            )
        )
    }

    fun toggleCompleted(id: String) {
        _todoList.value = _todoList.value.map {
            if (it.id == id) it.copy(completed = !it.completed) else it
        }
    }
}