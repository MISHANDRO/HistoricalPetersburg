package com.example.historicalpetersburg.map.views

import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.historicalpetersburg.databinding.BottomSheetGroupsListBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class GroupsRoutesListBottomSheet(private val binding: BottomSheetGroupsListBinding) {
    val behavior: BottomSheetBehavior<LinearLayout> = BottomSheetBehavior.from(binding.routeGroupBottomSheet)

    var peekHeight: Int
        get() = behavior.peekHeight
        set(value) { behavior.peekHeight = value }

    var maxHeight: Int
        get() = behavior.maxHeight
        set(value) { behavior.maxHeight = value }

    var state: Int
        get() = behavior.state
        set(value) { behavior.state = value }

    var halfExpandedRatio: Float
        get() = behavior.halfExpandedRatio
        set(value) { behavior.halfExpandedRatio = value }

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