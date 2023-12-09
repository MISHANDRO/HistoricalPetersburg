package com.example.historicalpetersburg.map.main.filters

import com.example.historicalpetersburg.map.main.objects.IHistoricalObject

interface IHistoricalObjectFilterChain {

    fun addNext(next: IHistoricalObjectFilterChain)

    fun isNormal(historicalObject: IHistoricalObject): Boolean
}