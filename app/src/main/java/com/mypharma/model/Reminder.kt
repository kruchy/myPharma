package com.mypharma.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "reminders")
data class Reminder (
    @DatabaseField(id = true)
    val id: Int,
    @DatabaseField
    val name: String,
    @DatabaseField(foreign = true)
    val drug: Drug
)
{
 constructor() : this(0,"", Drug())
}