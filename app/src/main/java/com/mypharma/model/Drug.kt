package com.mypharma.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "drugs")
data class Drug (
    @field:JsonProperty("Identyfikator Produktu Leczniczego")
    @DatabaseField(id = true)
    val id: Long,
    @field:JsonProperty("Nazwa Produktu Leczniczego")
    @DatabaseField
    val name: String,
    @field:JsonProperty("Nazwa powszechnie stosowana")
    @DatabaseField
    val popularName: String,
    @field:JsonProperty("Moc")
    @DatabaseField
    val power: String,
    @field:JsonProperty("Postać farmaceutyczna")
    @DatabaseField
    val form: String,
    @field:JsonProperty("Kod ATC")
    @DatabaseField
    val atc: String,
    @field:JsonProperty("Podmiot odpowiedzialny")
    @DatabaseField
    val entityResponsible: String,
    @field:JsonProperty("Opakowanie")
    @DatabaseField
    val packaging: String,
    @field:JsonProperty("Substancja czynna")
    @DatabaseField
    val substance: String,
    @field:JsonProperty("Ulotka")
    @DatabaseField
    val leaflet: String,
    @field:JsonProperty("Charakterystyka")
    @DatabaseField
    val characteristic: String,
)
{
    constructor() : this(0, "", "", "", "", "", "", "", "", "", "")
}
