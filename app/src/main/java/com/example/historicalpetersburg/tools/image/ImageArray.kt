package com.example.historicalpetersburg.tools.image

import androidx.core.text.isDigitsOnly
import com.example.historicalpetersburg.tools.GlobalTools
import com.example.historicalpetersburg.tools.value.ImageVal


class ImageArray(var imageVals: Array<String>) {
    private var data = arrayOfNulls<ImageVal>(imageVals.size)

    val size: Int
        get() = data.size

    constructor(resourceId: Int) : this(GlobalTools.instance.getStringArray(resourceId)) { }
    constructor() : this(emptyArray()) { }

    init {
        var i = -1
        for (imageStr in imageVals) {
            ++i
            if (imageStr.isDigitsOnly()) {
                data[i] = ImageVal(imageStr.toInt())
                continue
            }

            if (imageStr.startsWith("http")) {
                data[i] = ImageVal(imageStr)
                continue
            }

            data[i] = ImageVal(GlobalTools.instance.getIdentifier(imageStr))
        }
    }

    operator fun get(index: Int): ImageVal? {
        return data[index]
    }

    operator fun plusAssign(other: ImageArray) {
        this.data += other.data
        this.imageVals += other.imageVals
    }
}