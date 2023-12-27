package com.example.historicalpetersburg.map

import com.example.historicalpetersburg.map.main.HistoricalObjectManager
import com.example.historicalpetersburg.map.main.location.ILocationManager
import com.example.historicalpetersburg.map.main.IMapService
import com.example.historicalpetersburg.map.main.UserManager
import com.example.historicalpetersburg.map.main.location.AvailableUseLocationProxy
import com.example.historicalpetersburg.map.main.repositories.SqliteGroupRepository
import com.example.historicalpetersburg.map.main.repositories.SqlitePlaceRepository
import com.example.historicalpetersburg.map.main.repositories.SqliteRouteRepository
import com.example.historicalpetersburg.map.main.repositories.SqliteUserRepository
import com.example.historicalpetersburg.map.main.routeinspector.RouteInspector
import com.example.historicalpetersburg.map.yandex.location.YandexLocationManager
import com.example.historicalpetersburg.map.yandex.YandexMapService
import com.example.historicalpetersburg.tools.DbHelper
import com.example.historicalpetersburg.tools.GlobalTools
import com.example.historicalpetersburg.ui.map.MapFragment
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView

class MapManager private constructor() {
    private var _mapFragment: MapFragment? = null
    private var _map: IMapService? = null
    private var _objectManager: HistoricalObjectManager? = null
    private var _locationManager: ILocationManager? = null
    private var _routeInspector: RouteInspector? = null
    private var _userManager: UserManager? = null

    val mapFragment: MapFragment
        get() = _mapFragment!!

    val map: IMapService
        get() = _map!!

    val objectManager: HistoricalObjectManager
        get() = _objectManager!!

    val locationManager: ILocationManager
        get() = _locationManager!!

    val routeInspector: RouteInspector
        get() = _routeInspector!!

    val userManager: UserManager
        get() = _userManager!!

    companion object {
        private val instanceObj: MapManager by lazy { MapManager() }

        val instance: MapManager
            get() { return instanceObj }

        fun setYandexApiKey(apiKey: String) {
            MapKitFactory.setApiKey(apiKey)
        }

        fun setupYandexManager(
            mapView: MapView?,
            mapFragment: MapFragment)
        {
            if (mapView == null) {
                throw NullPointerException("MapView can't be null!")
            }

            instance._mapFragment = mapFragment
            instance._map = YandexMapService(mapView)

            instance._objectManager = HistoricalObjectManager()
            instance._locationManager = YandexLocationManager(AvailableUseLocationProxy())

            instance._routeInspector = RouteInspector()


            instance._userManager = UserManager(
                SqliteUserRepository(DbHelper(GlobalTools.instance.activity, "user.db", 1, false)))

            MapKitFactory.getInstance().onStart()
            mapView.onStart()
        }
    }

    fun createDefaultRoutes() {
        val dbHelper = DbHelper(GlobalTools.instance.activity, "app.db")

        val groupRepository = SqliteGroupRepository(dbHelper)
        objectManager.groupRepository = groupRepository

        val routeRepository = SqliteRouteRepository(dbHelper)
        objectManager.routeRepository = routeRepository

        val placeRepository = SqlitePlaceRepository(dbHelper)
        objectManager.placeRepository = placeRepository

        objectManager.createAll()

//        objectManager.addPlace(Coordinate(60.0229774804831,30.233355569284313)).apply {
//            name.value = "Точка"
//            addGroups(listOf(group1))
//        }
    }
}