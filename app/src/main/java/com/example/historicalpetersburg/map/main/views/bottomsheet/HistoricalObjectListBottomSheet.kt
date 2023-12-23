package com.example.historicalpetersburg.map.main.views.bottomsheet

import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.historicalpetersburg.databinding.BottomSheetGroupsListBinding
import com.example.historicalpetersburg.map.main.objects.UnionGroup
import com.example.historicalpetersburg.map.main.views.adapters.GroupListAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior


class HistoricalObjectListBottomSheet(private val binding: BottomSheetGroupsListBinding) :
    IBottomSheet {
    override val behavior = BottomSheetBehavior.from(binding.routeGroupBottomSheet)

    init {
        binding.routeGroupBottomSheet.setOnClickListener {
            if (state == BottomSheetBehavior.STATE_COLLAPSED) {
                state = BottomSheetBehavior.STATE_HALF_EXPANDED
            } else {
                state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    fun setSpinner1(unionGroup: UnionGroup, listener: AdapterView.OnItemSelectedListener) {
        binding.spinner1Name.text = unionGroup.name.value

        binding.groupListSpinner1.adapter = GroupListAdapter(unionGroup.allGroupName, unionGroup.groups.toMutableList())
        binding.groupListSpinner1.onItemSelectedListener = listener
    }

    fun setSpinner2(unionGroup: UnionGroup, listener: AdapterView.OnItemSelectedListener) {
        binding.spinner2Name.text = unionGroup.name.value

        binding.groupListSpinner2.adapter = GroupListAdapter(unionGroup.allGroupName, unionGroup.groups.toMutableList())
        binding.groupListSpinner2.onItemSelectedListener = listener
    }

    fun setRecycleViewList(adapter: RecyclerView.Adapter<*>) {
        binding.recycleRouteList.adapter = adapter
    }

    fun setCallback(callback: BottomSheetBehavior.BottomSheetCallback) {
        behavior.addBottomSheetCallback(callback)
    }
}