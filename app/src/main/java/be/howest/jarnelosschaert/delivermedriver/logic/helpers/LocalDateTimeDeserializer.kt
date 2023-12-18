package be.howest.jarnelosschaert.delivermedriver.logic.helpers

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeTypeAdapter : TypeAdapter<LocalDateTime?>() {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME

    @Throws(IOException::class)
    override fun write(out: JsonWriter?, value: LocalDateTime?) {
        if (value == null || out == null) {
            return
        }
        out.value(formatter.format(value))
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader?): LocalDateTime? {
        if (`in` == null) {
            return null
        }

        val dateString = `in`.nextString()
        return if (dateString.isNullOrBlank()) {
            null
        } else {
            LocalDateTime.parse(dateString, formatter)
        }
    }
}

