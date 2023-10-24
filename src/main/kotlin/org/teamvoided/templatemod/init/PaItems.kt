package org.teamvoided.templatemod.init

import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import org.teamvoided.templatemod.TemplateMod.id
import org.teamvoided.templatemod.items.PackItem

object PaItems {
    val PACK: PackItem = reg("pack", PackItem()) as PackItem
    fun init(){}
    private fun reg(id: String, item: Item): Item = Registry.register(Registries.ITEM, id(id), item)
}