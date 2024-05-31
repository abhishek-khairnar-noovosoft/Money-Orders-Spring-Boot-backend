package com.example.moneyorders.services

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

@Converter
class MapToJsonConverter : AttributeConverter<Map<String, String>, String> {
    private val objectMapper = jacksonObjectMapper()

    override fun convertToDatabaseColumn(attribute: Map<String, String>?): String {
        return objectMapper.writeValueAsString(attribute ?: emptyMap<String,String>())
    }

    override fun convertToEntityAttribute(dbData: String?): Map<String, String> {
        return objectMapper.readValue(dbData ?: "{}")
    }
}


