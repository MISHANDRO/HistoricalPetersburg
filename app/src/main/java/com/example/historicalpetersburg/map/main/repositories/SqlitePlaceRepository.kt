package com.example.historicalpetersburg.map.main.repositories

import android.database.sqlite.SQLiteOpenHelper
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.Coordinate
import com.example.historicalpetersburg.map.main.objects.Group
import com.example.historicalpetersburg.map.main.objects.PlaceData
import com.example.historicalpetersburg.map.main.objects.RouteData
import com.example.historicalpetersburg.map.main.objects.UnionGroup
import com.example.historicalpetersburg.map.main.shape.style.PlacemarkStyle
import com.example.historicalpetersburg.tools.GlobalTools
import com.example.historicalpetersburg.tools.value.StringId

class SqlitePlaceRepository(private val sqlite: SQLiteOpenHelper) : IPlaceRepository {
    override fun getAllData(): Array<PlaceData> {
        val db = sqlite.readableDatabase
        val query = """
            SELECT 
                PlaceId,
                HistoricalObjectId,
                NameId,
                ShortDescId,
                Latitude,
                Longitude,
                StyleName,
                IconName
            FROM Places
        """.trimIndent()

        val res = mutableListOf<PlaceData>()

        val cursor = db.rawQuery(query, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {

                val place = PlaceData(
                    placeId = cursor.getInt(0),
                    id = cursor.getInt(1),
                    name = StringId(cursor.getString(2)),
                    shortDesc = StringId(cursor.getString(3))
                ).apply {
                    icon = if (cursor.isNull(7)) R.drawable.icon_achievement // TODO
                        else GlobalTools.instance.getIdentifier(cursor.getString(7), "drawable")
                }

                if (!cursor.isNull(4)) { // TODO
                    place.placemark = MapManager.instance.map.addPlacemark(
                        Coordinate(cursor.getDouble(4), cursor.getDouble(5))).apply {
                        style = try {
                            PlacemarkStyle.valueOf(cursor.getString(6))
                        } catch (_: Exception) {
                            PlacemarkStyle.Default
                        }
                    }
                }

                res.add(place)

            } while (cursor.moveToNext())
        }

        cursor.close()
        return res.toTypedArray()
    }
}