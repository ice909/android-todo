package io.github.com.ice909.android.todo.model

data class Task(
    val id: String,
    val title: String,
    val content: String,
    val parentId: String,
    val completed: Boolean,
    val reatedAt: String,
    val updatedAt: String,
    val dueDate: String,
    val priority: Int,
    val tags: String,
    val sortOrder: Int,
    val expanded: Boolean,
    val deletedAt: String,
)
