package io.github.hikking.tmdb.api.serialization.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object LocalDateSerializer : KSerializer<LocalDate?> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDate? {
        val decodeString = decoder.decodeString()
        return if (decodeString.isEmpty()) {
            null
        } else {
            LocalDate.parse(decodeString, DateTimeFormatter.ISO_LOCAL_DATE)
        }
    }

    override fun serialize(encoder: Encoder, value: LocalDate?) {
        if (value == null) {
            encoder.encodeString("")
        } else {
            encoder.encodeString(DateTimeFormatter.ISO_LOCAL_DATE.format(value))
        }
    }
}
