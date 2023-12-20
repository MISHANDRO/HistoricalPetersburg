package com.example.historicalpetersburg.map.main.repositories

import android.database.sqlite.SQLiteOpenHelper
import com.example.historicalpetersburg.map.main.Coordinate
import com.example.historicalpetersburg.map.main.objects.Group
import com.example.historicalpetersburg.map.main.objects.PlaceData
import com.example.historicalpetersburg.map.main.objects.RouteData
import com.example.historicalpetersburg.map.main.objects.UnionGroup
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
                Longitude
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
                )

                if (!cursor.isNull(4)) {
                    place.coordinates = listOf(Coordinate(cursor.getDouble(4), cursor.getDouble(5)))
                }

                res.add(place)

            } while (cursor.moveToNext())
        }

        cursor.close()
        return res.toTypedArray()
    }
}