package com.example.historicalpetersburg.map.main.views.listeners

import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.filters.GroupFilterChain
import com.example.historicalpetersburg.map.main.objects.Group

class GroupListSpinnerSelectedListener(
    private val filter: GroupFilterChain,
    private val listAdapter: RecyclerView.Adapter<*>
) : AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val curSelected = parent.adapter.getItem(position) as Group?
        filter.group = curSelected

        MapManager.instance.objectManager.apply {
            updateShown()
            zoomShown()
        }

        listAdapter.notifyDataSetChanged() // TODO
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Вызывается, когда ничего не выбрано
    }
}