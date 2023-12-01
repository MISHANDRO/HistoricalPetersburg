package com.example.historicalpetersburg.map.entities

class Place(
    override var name: String,
    override var shortDesc: String,
    override var longDesc: String
) : IHistoricalObject {
    override fun select(zoomPaddingX: Float, zoomPaddingY: Float, action: (() -> Unit)?) {
        TODO("Not yet implemented")
    }

    override fun hide() {
        TODO("Not yet implemented")
    }

    override fun show() {
        TODO("Not yet implemented")
    }
}