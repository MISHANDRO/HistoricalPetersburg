package com.example.historicalpetersburg.map.views.listeners

import androidx.recyclerview.widget.RecyclerView
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.entities.IHistoricalObject
import com.example.historicalpetersburg.map.entities.Place
import com.example.historicalpetersburg.map.entities.Route
import com.google.android.material.tabs.TabLayout
import java.lang.reflect.Type

class TypeSelectionListener(private val listAdapter: RecyclerView.Adapter<*>) : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab) {
        var typeSelect: Type = IHistoricalObject::class.java
        when (tab.position) {
            0 -> { // all
                typeSelect = IHistoricalObject::class.java
            }
            1 -> { // route
                typeSelect = Route::class.java
            }
            2 -> { // place
                typeSelect = Place::class.java
            }
        }

        MapManager.instance.objectManager.apply {
            shownType = typeSelect
            updateShown()
            zoomShown()
        }

        listAdapter.notifyDataSetChanged()
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {
        // TODO
    }

    override fun onTabReselected(tab: TabLayout.Tab) {
        // TODO
    }
}