package com.example.historicalpetersburg.map.main.views.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.map.main.objects.Group
import com.example.historicalpetersburg.tools.value.IValue
import com.example.historicalpetersburg.tools.value.StringVal

class GroupListAdapter(
    private val nameOfDefaultGroup: IValue<String>,
    private val items: MutableList<Group>
) : BaseAdapter()
{
    init {
        val defaultGroup = Group().apply {
            name = nameOfDefaultGroup
        }
        items.add(0, defaultGroup)
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Group? {
        if (position == 0) return null
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_list_group, parent, false)

        val textView = view.findViewById<TextView>(R.id.group_name_item)
        textView.text = getItem(position)?.name?.value ?: nameOfDefaultGroup.value
        if (position == 0) {
            textView.typeface = Typeface.defaultFromStyle(Typeface.BOLD_ITALIC);
        }

        return view
    }
}