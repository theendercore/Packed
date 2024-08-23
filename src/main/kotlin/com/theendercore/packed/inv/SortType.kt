package com.theendercore.packed.inv

import com.theendercore.packed.util.charAt
import net.minecraft.item.ItemStack

enum class SortType {
    NORMAL,
    REVERSED,
    SPECIAL;

    fun getSort(): (ItemStack, ItemStack) -> Int {
        return when (this) {
            NORMAL -> { a, b -> a.charAt(0) - b.charAt(0) }
            REVERSED -> { a, b -> b.charAt(0) - a.charAt(0) }
            else -> { _, _ -> 0 }
        }
    }
}
