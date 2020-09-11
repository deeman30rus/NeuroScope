package com.theroom101.neuroscopekit.menu

class Menu private constructor(val items: List<MenuItem>): List<MenuItem> by items {

    class Builder {
        val items = mutableListOf<MenuItem>()

        fun build() = Menu(items)
    }
}