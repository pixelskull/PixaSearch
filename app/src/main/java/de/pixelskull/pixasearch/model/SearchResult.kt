package de.pixelskull.pixasearch.model

import java.io.Serializable

class SearchResult: Serializable {

    val id: Int                 = 0
    val user: String            = ""
    val tags: String            = ""
    val likes: Int              = 0
    val downloads: Int          = 0
    val comments: Int           = 0
    val previewURL: String      = ""
    val largeImageURL: String   = ""

}