package com.example.historicalpetersburg.tools.value

abstract class Value<Type> {
    abstract var value: Type

    override fun toString() : String {
        return value.toString()
    }
}