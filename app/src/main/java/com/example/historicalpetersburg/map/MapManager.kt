package com.example.historicalpetersburg.map

import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.map.main.Coordinate
import com.example.historicalpetersburg.map.main.HistoricalObjectManager
import com.example.historicalpetersburg.map.main.location.ILocationManager
import com.example.historicalpetersburg.map.main.IMapService
import com.example.historicalpetersburg.map.main.location.AvailableUseLocationProxy
import com.example.historicalpetersburg.map.yandex.location.YandexLocationManager
import com.example.historicalpetersburg.map.yandex.YandexMapService
import com.example.historicalpetersburg.ui.map.MapFragment
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView

class MapManager private constructor() {
    private var _mapFragment: MapFragment? = null

    private var _map: IMapService? = null

    private var _objectManager: HistoricalObjectManager? = null

    private var _locationManager: ILocationManager? = null


    val mapFragment: MapFragment
        get() = _mapFragment!!

    val map: IMapService
        get() = _map!!

    val objectManager: HistoricalObjectManager
        get() = _objectManager!!

    val locationManager: ILocationManager
        get() = _locationManager!!

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

            MapKitFactory.getInstance().onStart()
            mapView.onStart()
        }
    }

    fun clear() {
        _map = null
        _objectManager = null
        _locationManager = null
    }

    fun createDefaultRoutes() {
        var route = listOf(
            Coordinate(60.02296865460042, 30.232057123587953),
            Coordinate(60.021416771156964, 30.233827381537782),
            Coordinate(60.02191617415349, 30.23556545297943),
            Coordinate(60.022469267560666, 30.235801487372743)
        )

        val group1 = objectManager.addGroup().apply {
            name.value = "Категория 1"
        }

        val group2 = objectManager.addGroup().apply {
            name.value = "Категория 2"
        }
        objectManager.addRoute(route).apply {
            name.value = "Маршрут 1 харош"
            addGroups(listOf(group1))
        }

        route = listOf(
            Coordinate(60.023376971379676,30.232946668237947),
            Coordinate(60.024683921240154,30.237026046907634)
        )
        objectManager.addRoute(route).apply {
            name.value = "Еще хорош 2"
            addGroups(listOf(group2))
        }

        route = listOf(
            Coordinate(60.020903623637274,30.228957962190844),
            Coordinate(60.023545581819505,30.229022335207212),
            Coordinate(60.02495317510064,30.234096810760526)
        )
        objectManager.addRoute(route).apply {
            name.value = "Бим 3"
            imagesArrayId = R.array.route_images_1
            addGroups(listOf(group1, group2))
        }

//        MapManager.instance.map.addPlacemark(Coordinate(60.0229774804831,30.233355569284313))

        objectManager.addPlace(Coordinate(60.0229774804831,30.233355569284313)).apply {
            name.value = "Точка"
            addGroups(listOf(group1))
        }
        objectManager.addPlace(Coordinate(60.0229774804831,30.233355569284313)).apply {
            name.value = "Точка"
            addGroups(listOf(group1))
        }
        objectManager.addPlace(Coordinate(60.0229774804831,30.233355569284313)).apply {
            name.value = "Точка"
            addGroups(listOf(group1))
        }
        objectManager.addPlace(Coordinate(60.0229774804831,30.233355569284313)).apply {
            name.value = "Точка"
            addGroups(listOf(group1))
        }
        objectManager.addPlace(Coordinate(60.0229774804831,30.233355569284313)).apply {
            name.value = "Точка"
            addGroups(listOf(group1))
        }
        objectManager.addPlace(Coordinate(60.0229774804831,30.233355569284313)).apply {
            name.value = "Точка"
            addGroups(listOf(group1))
        }
        objectManager.addPlace(Coordinate(60.0229774804831,30.233355569284313)).apply {
            name.value = "Точка"
            addGroups(listOf(group1))
        }
        objectManager.addPlace(Coordinate(60.0229774804831,30.233355569284313)).apply {
            name.value = "Точка"
            addGroups(listOf(group1))
        }
        objectManager.addPlace(Coordinate(60.0229774804831,30.233355569284313)).apply {
            name.value = "Точка"
            addGroups(listOf(group1))
        }
        objectManager.addPlace(Coordinate(60.0229774804831,30.233355569284313)).apply {
            name.value = "Точка"
            addGroups(listOf(group1))
        }
        objectManager.addPlace(Coordinate(60.0229774804831,30.233355569284313)).apply {
            name.value = "Точка"
            addGroups(listOf(group1))
        }
        objectManager.addPlace(Coordinate(60.0229774804831,30.233355569284313)).apply {
            name.value = "Точка"
            addGroups(listOf(group1))
        }
        objectManager.addPlace(Coordinate(60.0229774804831,30.233355569284313)).apply {
            name.value = "Точка"
            addGroups(listOf(group1))
        }
        objectManager.addPlace(Coordinate(60.0229774804831,30.233355569284313)).apply {
            name.value = "ПОСЛЕДНЯЯ"
            addGroups(listOf(group1))
        }

        objectManager.updateShown()
    }
}