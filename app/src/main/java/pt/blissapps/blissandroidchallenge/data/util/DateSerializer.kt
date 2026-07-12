package pt.blissapps.blissandroidchallenge.data.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateSerializer : KSerializer<Date> {

    // Define o formato ISO-8601
    // Ex: 2022-02-17T20:40:32Z
    private val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Date {
        val stringValue = decoder.decodeString()
        return formatter.parse(stringValue) ?: Date()
    }

    override fun serialize(encoder: Encoder, value: Date) {
        val stringValue = formatter.format(value)
        encoder.encodeString(stringValue)
    }
}