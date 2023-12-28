package com.example.historicalpetersburg.map.main.objects

import com.example.historicalpetersburg.tools.value.Value

class UnionGroup(
    val id: Int,
    val name: Value<String>,
    val allGroupName: Value<String>,
    val groups: Array<Group>
)