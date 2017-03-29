package me.odinaris.searchcar.utils

import android.content.Context
import android.support.v7.widget.LinearLayoutManager

class DisScrollLinearLayoutManager(context: Context) : LinearLayoutManager(context) {
    private var isScrollEnabled = true

    fun setScrollEnabled(flag : Boolean) { isScrollEnabled = flag }

    override fun canScrollVertically(): Boolean {
        return isScrollEnabled && super.canScrollVertically()
    }
}