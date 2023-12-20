package com.example.historicalpetersburg.map.main.repositories

import android.database.sqlite.SQLiteOpenHelper
import com.example.historicalpetersburg.map.main.Coordinate
import com.example.historicalpetersburg.map.main.objects.Group
import com.example.historicalpetersburg.map.main.objects.RouteData
import com.example.historicalpetersburg.map.main.objects.UnionGroup
import com.example.historicalpetersburg.tools.value.StringId

class SqliteRouteRepository(private val sqlite: SQLiteOpenHelper) : IRouteRepository {
    override fun getAllData(): Array<RouteData> {
        val db = sqlite.readableDatabase
        val query = """
            SELECT 
                Routes.RouteId,
                HistoricalObjectId,
                NameId,
                ShortDescId,
                Latitude,
                Longitude,
                COUNT(*) OVER(PARTITION BY Routes.RouteId)
            FROM Routes
            LEFT JOIN PartsRoute
            ON PartsRoute.RouteId = Routes.RouteId
            ORDER BY PartsRoute.PartRouteId
        """.trimIndent()

        val res = mutableListOf<RouteData>()
        val curCoordinates = mutableListOf<Coordinate>()

        val cursor = db.rawQuery(query, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {

                val coordinate = Coordinate(
                    latitude = cursor.getDouble(4),
                    longitude = cursor.getDouble(5)
                )
                curCoordinates.add(coordinate)

                if (curCoordinates.size == cursor.getInt(6)) {
                    val route = RouteData(
                        routeId = cursor.getInt(0),
                        id = cursor.getInt(1),
                        name = StringId(cursor.getString(2)),
                        shortDesc = StringId(cursor.getString(3)),
                    ).apply { coordinates = if ( cursor.isNull(4)) null else curCoordinates.toList() }
                    res.add(route)
                    curCoordinates.clear()
                }

            } while (cursor.moveToNext())
        }

        cursor.close()
        return res.toTypedArray()
    }
}