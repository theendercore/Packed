package com.theendercore.packed_up.inv

import com.theendercore.packed_up.util.id
import net.minecraft.item.ItemStack

enum class SortType {
    NORMAL,
    REVERSED,
    SPECIAL;

    fun getSort(): Comparator<ItemStack> {
        return when (this) {
            NORMAL -> compareBy { it.item.id.path }
            REVERSED -> compareBy<ItemStack> { it.item.id.path }.reversed()
            else -> compareBy { 0 }
        }
    }
}
