package com.example.historicalpetersburg.map.main.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.map.main.objects.IHistoricalObjectData


class HistoricalObjectListAdapter(private val dataSet: MutableList<IHistoricalObjectData>) :
    RecyclerView.Adapter<HistoricalObjectListAdapter.ViewHolder>() {

    var actionOnClick: (() -> Unit)? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            textView = view.findViewById(R.id.route_name_item)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_list_route, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position].name.value

        val route = dataSet[position];
        viewHolder.itemView.setOnClickListener {
            route.select()
            actionOnClick?.invoke()
        }
    }

    override fun getItemCount() = dataSet.size
}

//class ItemAnim : DefaultItemAnimator

//class ItemAnim : DefaultItemAnimator() {
//    override fun animateDisappearance(
//        viewHolder: RecyclerView.ViewHolder,
//        preLayoutInfo: ItemHolderInfo,
//        postLayoutInfo: ItemHolderInfo?
//    ): Boolean {
//        MapManager.instance.toast("runPendingAnimations")
//        return super.animateDisappearance(viewHolder, preLayoutInfo, postLayoutInfo)
//    }
//
//    override fun animateAppearance(
//        viewHolder: RecyclerView.ViewHolder,
//        preLayoutInfo: ItemHolderInfo?,
//        postLayoutInfo: ItemHolderInfo
//    ): Boolean {
//        MapManager.instance.toast("runPendingAnimations")
//        return super.animateAppearance(viewHolder, preLayoutInfo, postLayoutInfo)
//    }
//
//    override fun animatePersistence(
//        viewHolder: RecyclerView.ViewHolder,
//        preLayoutInfo: ItemHolderInfo,
//        postLayoutInfo: ItemHolderInfo
//    ): Boolean {
//        MapManager.instance.toast("animatePersistence")
//        return super.animatePersistence(viewHolder, preLayoutInfo, postLayoutInfo)
//    }
//
//    override fun animateChange(
//        oldHolder: RecyclerView.ViewHolder,
//        newHolder: RecyclerView.ViewHolder,
//        preLayoutInfo: ItemHolderInfo,
//        postLayoutInfo: ItemHolderInfo
//    ): Boolean {
//        MapManager.instance.toast("animateChange")
//        return super.animateChange(oldHolder, newHolder, preLayoutInfo, postLayoutInfo)
//    }
//
//    override fun runPendingAnimations() {
//        super.runPendingAnimations()
//        MapManager.instance.toast("runPendingAnimations")
//    }
//
//    override fun endAnimation(item: RecyclerView.ViewHolder) {
//        super.endAnimation(item)
//        MapManager.instance.toast("runPendingAnimations")
//    }
//
//    override fun endAnimations() {
//        super.endAnimations()
//        MapManager.instance.toast("runPendingAnimations")
//    }
//
//    override fun isRunning(): Boolean {
//        return super.isRunning()
//    }
//
//    override fun canReuseUpdatedViewHolder(
//        viewHolder: RecyclerView.ViewHolder, payloads: MutableList<Any>
//    ) = true
//
//    override fun canReuseUpdatedViewHolder(
//        viewHolder: RecyclerView.ViewHolder)= true
//
//}
