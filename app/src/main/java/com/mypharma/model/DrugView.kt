package com.mypharma.model

import java.util.stream.Collectors.joining

data class DrugView( val id: Long, private val popularName: String, private val entityResponsible: String, private val substance: String) {
    override fun toString(): String {
        return StringBuilder()
            .append(popularName).append(",")
            .append(entityResponsible).append(",")
            .append(substance)
            .toString()
    }
}