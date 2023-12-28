package com.example.historicalpetersburg.map.main.filters

import com.example.historicalpetersburg.map.main.objects.IHistoricalObjectData

interface IHistoricalObjectFilterChain {

    fun addNext(next: IHistoricalObjectFilterChain)

    fun isNormal(historicalObject: IHistoricalObjectData): Boolean
}