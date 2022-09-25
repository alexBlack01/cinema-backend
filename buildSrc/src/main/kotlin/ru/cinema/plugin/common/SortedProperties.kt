package ru.cinema.plugin.common

import java.util.*

class SortedProperties : Properties() {
    override val entries: MutableSet<MutableMap.MutableEntry<Any, Any>>
        get() = super.entries.sortedBy { it.key.toString() }.toMutableSet()
}