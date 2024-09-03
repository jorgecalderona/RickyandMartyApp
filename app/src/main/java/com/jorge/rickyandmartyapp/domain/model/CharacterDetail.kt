package com.jorge.rickyandmartyapp.domain.model

data class CharacterDetail(
    val id: String?,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val image: String?,
    val origin: Origin?,
    val location: Location?
)

data class Origin(
    val id: String?,
    val name: String?
)

data class Location(
    val id: String?,
    val name: String?
)

