package com.theendercore.packed.init

import com.theendercore.packed.Packed.id
import com.theendercore.packed.items.PackItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object PakItems {
    val PACK: PackItem = reg("pack", PackItem()) as PackItem
    fun init(){}
    private fun reg(id: String, item: Item): Item = Registry.register(Registries.ITEM, id(id), item)
}