package com.jorge.rickyandmartyapp.domain.model

data class CharacterInfo(
    val id: String?,
    val name: String?,
    val image: String?
)

data class CharacterPages(
    val nextPage: Int?
)
