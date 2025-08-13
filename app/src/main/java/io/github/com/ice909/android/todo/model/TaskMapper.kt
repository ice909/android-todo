package io.github.com.ice909.android.todo.model

import io.github.com.ice909.android.todo.TaskProto

fun TaskProto.toTask(): Task = Task(
    id = id,
    title = title,
    content = content,
    parentId = parentId,
    sortOrder = sortOrder,
    completed = completed
)

fun Task.toProto(): TaskProto = TaskProto.newBuilder()
    .setId(id)
    .setTitle(title)
    .setContent(content)
    .setParentId(parentId)
    .setSortOrder(sortOrder)
    .setCompleted(completed)
    .build()