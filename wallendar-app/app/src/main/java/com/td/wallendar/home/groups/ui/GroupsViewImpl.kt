package com.td.wallendar.home.groups.ui

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.td.wallendar.home.groups.GroupAdapter
import com.td.wallendar.models.Group

class GroupsViewImpl<T: Group> @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : RecyclerView(context, attrs, defStyleAttr), GroupsView<T> {
    override fun bind(groupAdapter: GroupAdapter<T>, addChargeFAB: ExtendedFloatingActionButton) {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = groupAdapter

        // Shrink floating button when scrolling, extend at the top. Just fancy fab
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == SCROLL_STATE_IDLE) {
                    addChargeFAB.extend()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && addChargeFAB.isExtended) {
                    addChargeFAB.shrink()
                }
            }
        })
    }

    override fun bind(groupAdapter: GroupAdapter<T>) {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = groupAdapter
    }
}