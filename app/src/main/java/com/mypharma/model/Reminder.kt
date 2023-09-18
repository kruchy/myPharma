package com.mypharma.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.Date

@DatabaseTable(tableName = "reminders")
data class Reminder(
    @DatabaseField(generatedId = true)
    var id: Long? = 0,
    @DatabaseField
    var name: String? = null,
    @DatabaseField(columnName = "drug_id", foreign = true)
    var drug: Drug? = null,
    @DatabaseField
    var date: Date? = null

) {
    override fun toString(): String {
        return "Reminder(id=$id, name=$name, drug=$drug, date=$date)"
    }
}