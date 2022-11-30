package com.example.mahtis

class FoodData {
    var itemName: String? = null
        private set
    var itemDescription: String? = null
        private set
    var itemDescription2: String? = null
        private set
    var itemRating: String? = null
        private set
    var itemImage: String? = null
        private set
    var key: String? = null

    constructor(
    )

    constructor(
        itemName: String?,
        itemDescription: String?,
        itemDescription2: String?,
        itemRating: String?,
        itemImage: String?
    ) {
        this.itemName = itemName
        this.itemDescription = itemDescription
        this.itemDescription2 = itemDescription2
        this.itemRating = itemRating
        this.itemImage = itemImage
    }
}