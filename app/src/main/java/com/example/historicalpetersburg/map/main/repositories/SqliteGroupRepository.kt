package com.example.historicalpetersburg.map.main.repositories

import android.database.sqlite.SQLiteOpenHelper
import com.example.historicalpetersburg.map.main.objects.Group
import com.example.historicalpetersburg.map.main.objects.IHistoricalObjectData
import com.example.historicalpetersburg.map.main.objects.UnionGroup
import com.example.historicalpetersburg.tools.value.StringId

class SqliteGroupRepository(private val sqlite: SQLiteOpenHelper) : IGroupRepository {
    override fun getUnions(count: Int): Array<UnionGroup> {
        val db = sqlite.readableDatabase
        val query = """
            SELECT 
                UnionsGroup.UnionGroupId,
                UnionsGroup.NameId,
                UnionsGroup.AllGroupNameId,
                Groups.GroupId,
                Groups.NameId,
                COUNT(GroupId) OVER(PARTITION BY UnionsGroup.UnionGroupId) AS Count
            FROM UnionsGroup
            JOIN Groups
            ON UnionsGroup.UnionGroupId = Groups.UnionGroupId
        """.trimIndent()

        val res = mutableListOf<UnionGroup>()
        val curGroups = mutableListOf<Group>()

        val cursor = db.rawQuery(query, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val group = Group(
                    id = cursor.getInt(3),
                    name = StringId(cursor.getString(4))
                )
                curGroups.add(group)

                if (curGroups.size == cursor.getInt(5)) {
                    val union = UnionGroup(
                        id = cursor.getInt(0),
                        name = StringId(cursor.getString(1)),
                        allGroupName = StringId(cursor.getString(2)),
                        groups = curGroups.toTypedArray()
                    )
                    res.add(union)

                    curGroups.clear()
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return res.toTypedArray()
    }

    override fun objectInGroup(historicalObjectData: IHistoricalObjectData, group: Group): Boolean {
        val db = sqlite.readableDatabase
        val query = """
            SELECT COUNT(1)
            FROM HistoricalObjects
            WHERE HistoricalObjectId = ? AND GroupId = ?
        """.trimIndent()
        val cursor = db.rawQuery(query, arrayOf(historicalObjectData.id.toString(), group.id.toString()))
        var exists = false

        if (cursor.moveToFirst()) {
            exists = cursor.getInt(0) > 0
        }
        cursor.close()
        return exists
    }
}