package com.theendercore.packed.data

import com.theendercore.packed.Packed.id
import com.theendercore.packed.util.tag
import net.minecraft.item.Item
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier

object PakItemTags {
    val ITEM_TAGS = mutableSetOf<TagKey<Item>>()

    val BACKPACKS = create("backpacks")

    fun create(id: String): TagKey<Item> {
        val regTag = itemTag(id(id))
        ITEM_TAGS.add(regTag)
        return regTag
    }

    fun itemTag(id: Identifier) = RegistryKeys.ITEM.tag(id)
}
