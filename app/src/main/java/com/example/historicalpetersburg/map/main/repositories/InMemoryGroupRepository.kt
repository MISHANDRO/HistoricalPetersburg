package com.example.historicalpetersburg.map.main.repositories

import com.example.historicalpetersburg.map.main.objects.Group
import com.example.historicalpetersburg.map.main.objects.UnionGroup
import com.example.historicalpetersburg.tools.value.Value
import kotlin.math.min

class InMemoryGroupRepository : IGroupRepository {

    private val unions = mutableListOf<UnionGroup>()
    private val groupsUnion: MutableMap<Int, MutableList<Int>> = mutableMapOf()
    private val groupNames = mutableListOf<Value<String>>()

    override fun getUnions(count: Int): Array<UnionGroup> {
        val res = mutableListOf<UnionGroup>()

        for (i in 0 until min(count, unions.size)) {
            res.add(UnionGroup(i, unions[i].name, unions[i].allGroupName, groupsUnion.getValue(i).map {
                Group(it, groupNames[it])
            }.toTypedArray()))
        }

        return res.toTypedArray()
    }

    /////// TODO
    fun addUnion(name: Value<String>, allGroupName: Value<String>) : Int {
        unions.add(UnionGroup(-1, name, allGroupName, emptyArray()))
        return unions.size - 1
    }

    fun addGroup(groupName: Value<String>, unionId: Int) : Int {
        groupNames.add(groupName)
        val idGroup = groupNames.size - 1

        if (!groupsUnion.containsKey(unionId))
            groupsUnion[unionId] = mutableListOf()

        groupsUnion.getValue(unionId).add(idGroup)

        return idGroup
    }
}