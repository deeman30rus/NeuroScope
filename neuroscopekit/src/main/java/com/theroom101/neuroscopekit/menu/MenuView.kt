package com.theroom101.neuroscopekit.menu

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.theroom101.neuroscopekit.R

class MenuView(
    activity: Activity,
) : RecyclerView(activity, null, 0) {

    init {
        layoutManager = LinearLayoutManager(context)
        adapter = MenuAdapter(activity)
    }

    fun setMenu(menu: Menu) {
        (adapter as MenuAdapter).items = menu.items
    }
}

private const val SCREEN_VIEW_TYPE = 1

private class MenuAdapter(
    context: Activity
) : RecyclerView.Adapter<ScreenViewHolder>() {

    private val factory = VHFactory(context)
    var items = listOf<MenuItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ScreenMenuItem<*> -> SCREEN_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        SCREEN_VIEW_TYPE -> factory.screen(parent)
        else -> error("Unsupported type $viewType")

    }

    override fun onBindViewHolder(holder: ScreenViewHolder, position: Int) {
        val item = items[position]
        when (items[position]) {
            is ScreenMenuItem<*> -> holder.bind(
                item as? ScreenMenuItem<*> ?: error("invalid menu item $item")
            )
        }
    }

    override fun getItemCount() = items.size

    private inner class VHFactory(private val activity: Activity) {

        fun screen(parent: ViewGroup) = ScreenViewHolder(
            LayoutInflater.from(activity).inflate(R.layout.kit_viewholder_screen, parent, false)
        ).also { holder -> holder.itemView.setOnClickListener {
            val item = items[holder.adapterPosition] as? ScreenMenuItem<*> ?: error("bad cast")

            val intent = Intent(activity, item.kls.java)
            activity.startActivity(intent)
        } }
    }}

private class ScreenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(screenMenuItem: ScreenMenuItem<*>) {
        (itemView as TextView).text = screenMenuItem.name
    }
}
