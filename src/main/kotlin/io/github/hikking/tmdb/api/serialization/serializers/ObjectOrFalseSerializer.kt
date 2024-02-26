package io.github.hikking.tmdb.api.serialization.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean

open class ObjectOrFalseSerializer<T>(private val serializer: KSerializer<T>) : KSerializer<T?> {

    override val descriptor = serializer.descriptor

    override fun serialize(encoder: Encoder, value: T?) {
        if (value != null) {
            serializer.serialize(encoder, value)
        } else {
            encoder.encodeBoolean(false)
        }
    }

    override fun deserialize(decoder: Decoder): T? {
        val jsonElement = decoder.decodeSerializableValue(JsonElement.serializer())
        if (jsonElement is JsonPrimitive) {
            if (!jsonElement.boolean) return null
        }
        return Json.decodeFromJsonElement(serializer, jsonElement)
    }
}
