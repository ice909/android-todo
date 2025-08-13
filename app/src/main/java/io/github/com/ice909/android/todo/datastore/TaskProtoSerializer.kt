package io.github.com.ice909.android.todo.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import io.github.com.ice909.android.todo.TaskListProto
import java.io.InputStream
import java.io.OutputStream

object TaskProtoSerializer : Serializer<TaskListProto> {
    override val defaultValue: TaskListProto = TaskListProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): TaskListProto {
        try {
            return TaskListProto.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override suspend fun writeTo(
        t: TaskListProto,
        output: OutputStream
    ) = t.writeTo(output)
}