package com.example.historicalpetersburg.map.views

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.entities.Route
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RouteInfoDialog(private val route: Route): BottomSheetDialogFragment() {

    private lateinit var view: View

    override fun getTheme(): Int { // TODO
        return R.style.InsetBottomSheetDialogTheme
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = layoutInflater.inflate(R.layout.bottom_sheet_dialog_route_info, container)

        val nameText: TextView = view.findViewById(R.id.bottom_sheet_route_name)
        val shortDescText: TextView = view.findViewById(R.id.bottom_sheet_route_short_desc)
        val longDescText: TextView = view.findViewById(R.id.bottom_sheet_route_long_desc)

        nameText.text = route.name
        shortDescText.text = route.shortDesc
        longDescText.text = route.longDesc

        return view
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)

        MapManager.instance.routeManager.returnSelectedRoutes()
    }

}