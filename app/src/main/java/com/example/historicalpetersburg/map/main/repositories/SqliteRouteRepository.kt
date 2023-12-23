package com.example.historicalpetersburg.map.main.repositories

import android.database.sqlite.SQLiteOpenHelper
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.Coordinate
import com.example.historicalpetersburg.map.main.objects.PartRoute
import com.example.historicalpetersburg.map.main.objects.RouteData
import com.example.historicalpetersburg.map.main.shape.style.PlacemarkStyle
import com.example.historicalpetersburg.map.main.shape.style.RouteStyle
import com.example.historicalpetersburg.tools.GlobalTools
import com.example.historicalpetersburg.tools.image.ImageArray
import com.example.historicalpetersburg.tools.value.StringId
import java.lang.Math.toDegrees
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class SqliteRouteRepository(private val sqlite: SQLiteOpenHelper) : IRouteRepository {
    override fun getAllData(): Array<RouteData> {
        val db = sqlite.readableDatabase
        val query = """
            SELECT 
                Routes.RouteId,
                HistoricalObjectId,
                Routes.NameId,
                ShortDescId,
                Latitude,
                Longitude,
                COUNT(*) OVER(PARTITION BY Routes.RouteId),
                Routes.StyleName,
                Routes.StartPositionStyleName,
                IconName
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
                    ).apply {
                        icon = if (cursor.isNull(10)) R.drawable.icon_route
                            else GlobalTools.instance.getIdentifier(cursor.getString(10), "drawable")


                        if (!cursor.isNull(4)) {
                            line = MapManager.instance.map.addLine(curCoordinates.toList())
                            line?.style = try {
                                RouteStyle.valueOf(cursor.getString(7))
                            } catch (_: Exception) {
                                RouteStyle.Default
                            }

                            startPlacemark = MapManager.instance.map.addPlacemark(curCoordinates[0])
                            startPlacemark?.style = try {
                                println(cursor.getString(8))
                                PlacemarkStyle.valueOf(cursor.getString(8))
                            } catch (_: Exception) {
                                PlacemarkStyle.Default
                            }
                            println(startPlacemark?.style?.name)


                            val angle = angleFromCoordinate(
                                curCoordinates[0].latitude, curCoordinates[0].longitude,
                                curCoordinates[1].latitude, curCoordinates[1].longitude,
                            )
                            startPlacemark?.direction = angle.toFloat()
                        }
                    }

                    res.add(route)
                    curCoordinates.clear()
                }

            } while (cursor.moveToNext())
        }

        cursor.close()
        return res.toTypedArray()
    }

    override fun getFirstPartByRoute(route: RouteData): PartRoute? {
        val db = sqlite.readableDatabase
        val query = """
            SELECT
                PartRouteId
            FROM PartsRoute
            WHERE RouteId = ${route.routeId}
            ORDER BY PartRouteId
            LIMIT 1
        """.trimIndent()


        val cursor = db.rawQuery(query, null)
        if (cursor != null && cursor.moveToFirst()) {
            val partRouteId = cursor.getInt(0)
            cursor.close()
            return getPartById(partRouteId)
        }
        cursor.close()

        return null
    }

    override fun getPartById(partRouteId: Int): PartRoute? {
        val db = sqlite.readableDatabase

        val query = """
            SELECT *
            FROM (
                SELECT 
                    PartRouteId,
                    Latitude,
                    Longitude,
                    NameId,
                    PreviewId,
                    TextId,
                    ImageIds,
                    LEAD(PartRouteId) OVER(PARTITION BY RouteId) AS NextId
                FROM PartsRoute
            )
            WHERE PartRouteId = 3
            LIMIT 1
        """.trimIndent()

        var res: PartRoute? = null

        val cursor = db.rawQuery(query, null)
        if (cursor != null && cursor.moveToFirst()) {
            res = PartRoute(
                id = cursor.getInt(0),
                name = StringId(cursor.getString(3)),
                text = StringId(cursor.getString(5)),
                coordinate = Coordinate(cursor.getDouble(1), cursor.getDouble(2))
            ).apply {
                preview = if (cursor.isNull(4)) null else StringId(cursor.getString(4))
                images = if (cursor.isNull(6)) null
                         else ImageArray(GlobalTools.instance.getIdentifier(cursor.getString(6), "array"))
                nextId = if (cursor.isNull(7)) null else cursor.getInt(7)
            }
        }
        cursor.close()

        return res
    }

    //////////
    private fun angleFromCoordinate(
        startLat: Double, startLon: Double, endLat: Double, endLon: Double
    ): Double {
        val startLatRad = Math.toRadians(startLat)
        val startLonRad = Math.toRadians(startLon)
        val endLatRad = Math.toRadians(endLat)
        val endLonRad = Math.toRadians(endLon)

        val dLat = endLatRad - startLatRad
        val dLon = endLonRad - startLonRad

        val angle = atan2(sin(dLon) * cos(endLatRad), cos(startLatRad) * sin(endLatRad) - sin(startLatRad) * cos(endLatRad) * cos(dLon))
        return (Math.toDegrees(angle) + 360) % 360
    }
}