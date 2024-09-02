package com.jorge.rickyandmartyapp.data.network.mapper

import com.jorge.GetCharactersQuery
import com.jorge.rickyandmartyapp.domain.model.CharacterInfo
import com.jorge.rickyandmartyapp.domain.model.CharacterPages

fun GetCharactersQuery.Characters?.toDomain(): Pair<List<CharacterInfo>, CharacterPages?> {
    val characterList = this?.results?.map { result ->
        CharacterInfo(
            id = result?.id ?: "",
            name = result?.name ?: "",
            image = result?.image ?: ""
        )
    }.orEmpty()

    val pages = this?.info?.let { info ->
        CharacterPages(
            nextPage = info.next
        )
    }

    return Pair(characterList, pages)
}

