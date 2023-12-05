package com.example.historicalpetersburg.map.views.bottomsheet

import android.widget.AdapterView
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.historicalpetersburg.databinding.BottomSheetGroupsListBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior


class GroupsRoutesListBottomSheet(private val binding: BottomSheetGroupsListBinding) :
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

    fun setSpinner1(adapter: BaseAdapter, listener: AdapterView.OnItemSelectedListener) {
        binding.groupListSpinner1.adapter = adapter
        binding.groupListSpinner1.onItemSelectedListener = listener
    }

    fun setRecycleViewList(adapter: RecyclerView.Adapter<*>) {
        binding.recycleRouteList.adapter = adapter
    }

    fun setCallback(callback: BottomSheetBehavior.BottomSheetCallback) {
        behavior.addBottomSheetCallback(callback)
    }
}