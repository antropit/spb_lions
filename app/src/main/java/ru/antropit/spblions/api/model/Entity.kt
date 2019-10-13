package ru.antropit.spblions.api.model

data class Entity(
    var id: Int,
    var name: String,
    var description: String,
    var photo: String,
    var address: String,
    var location: location
)