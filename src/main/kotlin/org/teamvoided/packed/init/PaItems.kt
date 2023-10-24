package org.teamvoided.packed.init

import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import org.teamvoided.packed.Packed.id
import org.teamvoided.packed.items.PackItem

object PaItems {
    val PACK: PackItem = reg("pack", PackItem()) as PackItem
    fun init(){}
    private fun reg(id: String, item: Item): Item = Registry.register(Registries.ITEM, id(id), item)
}