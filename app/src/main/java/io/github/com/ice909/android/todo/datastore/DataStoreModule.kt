package io.github.com.ice909.android.todo.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import io.github.com.ice909.android.todo.TaskListProto

val Context.taskDataStore: DataStore<TaskListProto> by dataStore(
    fileName = "tasks.db",
    serializer = TaskProtoSerializer
)