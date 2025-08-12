package io.github.com.ice909.android.todo.model

data class Task(
    val id: String,
    val title: String,
    val content: String,
    val parentId: String,
    val completed: Boolean,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val dueDate: String? = null,
    val priority: Int? = null,
    val tags: String? = null,
    val sortOrder: Int,
    val expanded: Boolean? = null,
    val deletedAt: String? = null,
)
