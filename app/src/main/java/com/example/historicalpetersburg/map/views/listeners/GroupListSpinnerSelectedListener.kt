package com.example.historicalpetersburg.map.views.listeners

import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.entities.Group

class GroupListSpinnerSelectedListener(private val routeListAdapter: RecyclerView.Adapter<*>) : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

        val tmp = MapManager.instance.routeManager.selectedRoutes.toList()
        println(tmp.map { it.id })
        MapManager.instance.routeManager.selectIntersectionOfGroupsById(listOf((parent.adapter.getItem(position) as Group).id))
        var i = 0
        var j = 0
        var sdv = 0;
        val cur = MapManager.instance.routeManager.selectedRoutes
        println(cur.map { it.id })

        while (i < tmp.size && j < cur.size) {
            when {
                tmp[i].id < cur[j].id -> {
                    routeListAdapter.notifyItemRemoved(i + sdv)
                    --sdv;
                    i++
                }
                tmp[i].id > cur[j].id -> {
                    // Элемент был добавлен
                    routeListAdapter.notifyItemInserted(i + sdv)
                    ++sdv;
                    j++
                }
                else -> {
                    i++
                    j++
                }
            }
        }

        while (i < tmp.size) {
            routeListAdapter.notifyItemRemoved(i + sdv)
            --sdv;
            i++
        }

        while (j < cur.size) {
            routeListAdapter.notifyItemInserted(i + sdv)
            ++sdv;
            j++
        }

//                        behavior.peekHeight = 150
//                        behavior.maxHeight = 1500
        println("GOOD")

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Вызывается, когда ничего не выбрано
    }
}