package com.example.historicalpetersburg.map.main.repositories

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.historicalpetersburg.map.main.objects.PartRoute
import com.example.historicalpetersburg.map.main.objects.PlaceData
import com.example.historicalpetersburg.map.main.objects.RouteData

class SqliteUserRepository(private val sqlite: SQLiteOpenHelper) : IUserRepository {
    override fun getCompletePartsByRoute(routeData: RouteData) : Array<Int> {
        val db = sqlite.readableDatabase
        val query = """
            SELECT
                PartRouteId
            FROM CompletePartsRoute
            WHERE RouteId = ${routeData.routeId}
        """.trimIndent()

        val res = mutableListOf<Int>()

        val cursor = db.rawQuery(query, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                res.add(cursor.getInt(0))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return res.toTypedArray()
    }

    override fun getCompletePartsByPlace(placeData: PlaceData) : Array<Int> {
        val db = sqlite.readableDatabase
        val query = """
            SELECT
                PartRouteId
            FROM CompletePartsRoute
            WHERE PlaceId = ${placeData.placeId}
        """.trimIndent()

        val res = mutableListOf<Int>()

        val cursor = db.rawQuery(query, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                res.add(cursor.getInt(0))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return res.toTypedArray()
    }

    override fun isPlaceCompleted(placeData: PlaceData): Boolean {
        val db = sqlite.readableDatabase
        val query = """
            SELECT
                PlaceId
            FROM CompletePlaces
            WHERE PlaceId = ${placeData.placeId}
        """.trimIndent()


        val cursor = db.rawQuery(query, null)
        val res = cursor != null && cursor.moveToFirst()
        cursor.close()

        return res
    }

    override fun saveCompletePart(partRoute: PartRoute, routeId: Int) {
        val db = sqlite.writableDatabase
        val contentValues = ContentValues().apply {
            put("PartRouteId", partRoute.id)
            put("RouteId", routeId)
            if (partRoute.placeId != null)
                put("PlaceId", partRoute.placeId)
        }

        db.insertWithOnConflict("CompletePartsRoute", null, contentValues, SQLiteDatabase.CONFLICT_IGNORE)
    }

    override fun saveCompletePlace(placeData: PlaceData) {
        val db = sqlite.writableDatabase
        val contentValues = ContentValues().apply {
            put("PlaceId", placeData.placeId)
        }

        db.insertWithOnConflict("CompletePlaces", null, contentValues, SQLiteDatabase.CONFLICT_IGNORE)
    }

    override fun resetAll() {
        val db = sqlite.writableDatabase
        db.execSQL("DELETE FROM CompletePlaces")
        db.execSQL("DELETE FROM CompletePartsRoute")
    }
}