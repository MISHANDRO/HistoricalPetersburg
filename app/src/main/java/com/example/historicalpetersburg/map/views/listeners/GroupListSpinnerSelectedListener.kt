package com.example.historicalpetersburg.map.views.listeners

import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.entities.Group

class GroupListSpinnerSelectedListener(private val listAdapter: RecyclerView.Adapter<*>) : AdapterView.OnItemSelectedListener {

    private var curSelected: Group? = null

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        if (curSelected == null) {
            curSelected = parent.adapter.getItem(0) as Group
        }

        curSelected?.let { MapManager.instance.objectManager.shownGroups.remove(curSelected) }
        curSelected = parent.adapter.getItem(position) as Group?

        MapManager.instance.objectManager.apply {
            shownGroups.add(curSelected!!)
            updateShown()
            zoomShown()
        }

        listAdapter.notifyDataSetChanged() // TODO
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Вызывается, когда ничего не выбрано
    }
}