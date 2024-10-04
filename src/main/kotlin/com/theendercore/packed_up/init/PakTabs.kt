package com.theendercore.packed_up.init

import com.theendercore.packed_up.PackedUp.MODID
import com.theendercore.packed_up.PackedUp.id
import com.theendercore.packed_up.util.registerHolder
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemGroup
import net.minecraft.registry.Holder
import net.minecraft.registry.Registries
import net.minecraft.text.Text

object PakTabs {
    val PACKED_TAB = register(MODID, FabricItemGroup.builder()
        .name(Text.translatable("itemGroup.$MODID"))
        .icon { PakItems.PACK.defaultStack }
        .entries { _, entries -> entries.addItem(PakItems.PACK) }
    )

    fun init() = Unit

    @Suppress("SameParameterValue")
    private fun register(id: String, group: ItemGroup.Builder): Holder<ItemGroup> =
        Registries.ITEM_GROUP.registerHolder(id(id), group.build())
}
