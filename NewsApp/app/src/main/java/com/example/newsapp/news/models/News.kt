package com.example.newsapp.news.models

import java.io.Serializable

data class News(
    var Image: String? = "",
    var Name: String? = "",
    var id: Int? = 0,
    var newsContent: String? = "",
    var newsLabel: String? = "",
    var plus: Boolean? = false,
    var published: String? = "",
    var subTag: String? = "",
    var tag: String? = ""
) : Serializable {

    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$Image$Name",
            "$Name$Image",
            "${Image?.first()} ${Name?.first()}"
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}