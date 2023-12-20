package com.example.historicalpetersburg.map.main.views.listeners

import androidx.recyclerview.widget.RecyclerView
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.filters.TypeFilterChain
import com.example.historicalpetersburg.map.main.objects.Place
import com.example.historicalpetersburg.map.main.objects.RouteData
import com.google.android.material.tabs.TabLayout

class TypeSelectionListener(
    private val filter: TypeFilterChain,
    private val listAdapter: RecyclerView.Adapter<*>
) : TabLayout.OnTabSelectedListener {

    override fun onTabSelected(tab: TabLayout.Tab) {
        when (tab.position) {
            0 -> { // all
                filter.type = null
            }
            1 -> { // route
                filter.type = RouteData::class.java
            }
            2 -> { // place
                filter.type = Place::class.java
            }
        }

        MapManager.instance.objectManager.apply {
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