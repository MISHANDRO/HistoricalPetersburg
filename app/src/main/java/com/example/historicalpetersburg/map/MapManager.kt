package com.example.historicalpetersburg.map

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.historicalpetersburg.map.models.Coordinate
import com.example.historicalpetersburg.map.services.factories.YandexRouteFactory
import com.example.historicalpetersburg.map.services.locationManagers.AvailableUseLocationProxy
import com.example.historicalpetersburg.map.services.locationManagers.ILocationManager
import com.example.historicalpetersburg.map.services.locationManagers.YandexLocationManager
import com.example.historicalpetersburg.map.services.mapServices.IMapService
import com.example.historicalpetersburg.map.services.mapServices.YandexMapService
import com.example.historicalpetersburg.map.services.routeManagers.IRouteManager
import com.example.historicalpetersburg.map.services.routeManagers.RouteManager
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.i18n.I18nManagerFactory

class MapManager {
    private var _activity: Activity? = null

    private var _fragmentManager: FragmentManager? = null

    private var _map: IMapService? = null

    private var _routeManager: IRouteManager? = null

    private var _locationManager: ILocationManager? = null


    val activity: Activity
        get() = _activity!!

    val fragmentManager: FragmentManager
        get() = _fragmentManager!!

    val map: IMapService
        get() = _map!!

    val routeManager: IRouteManager
        get() = _routeManager!!

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
            activity: Activity,
            mapView: MapView?,
            fragmentManager: FragmentManager)
        {
            if (mapView == null) {
                throw NullPointerException("MapView can't be null!")
            }

            instance._activity = activity
            instance._map = YandexMapService(mapView)
            instance._fragmentManager = fragmentManager

//            MapKitFactory.initialize(activity)

            val rm = RouteManager()
            rm.initialize(
                YandexRouteFactory()
            )

            instance._routeManager = rm
            instance._locationManager = AvailableUseLocationProxy(YandexLocationManager())

            MapKitFactory.getInstance().onStart()
            mapView.onStart()
        }
    }

    fun clear() {
        _activity = null
        _fragmentManager = null
        _map = null
        _routeManager = null
        _locationManager = null
    }

    fun toast(message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(activity, message, length).show()
    }

    fun createDefaultRoutes() {
        var route = listOf(
            Coordinate(60.02296865460042, 30.232057123587953),
            Coordinate(60.021416771156964, 30.233827381537782),
            Coordinate(60.02191617415349, 30.23556545297943),
            Coordinate(60.022469267560666, 30.235801487372743)
        )

        routeManager.addGroup().apply {
            name = "ХУЙ"
        }

        routeManager.addGroup().apply {
            name = "Пизда"
        }
        routeManager.addRoute(route, listOf(1)).apply {
            name = "Маршрут 1 харош"
        }

        route = listOf(
            Coordinate(60.023376971379676,30.232946668237947),
            Coordinate(60.024683921240154,30.237026046907634)
        )
        routeManager.addRoute(route, listOf(2)).apply {
            name = "Еще хорош 2"
        }

        route = listOf(
            Coordinate(60.020903623637274,30.228957962190844),
            Coordinate(60.023545581819505,30.229022335207212),
            Coordinate(60.02495317510064,30.234096810760526)
        )
        routeManager.addRoute(route, listOf(1, 2)).apply {
            name = "Бим 3"
        }
    }
}