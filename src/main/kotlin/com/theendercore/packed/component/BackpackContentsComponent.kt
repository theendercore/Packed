package com.theendercore.packed.component

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.client.item.TooltipData
import net.minecraft.item.ItemStack
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.dynamic.Codecs

data class BackpackContentsComponent(val stacks: DefaultedList<ItemStack> = DefaultedList.of()) : TooltipData {
    constructor(items: List<ItemStack>) : this(DefaultedList.of()) {
        items.forEachIndexed(stacks::set)
    }

    fun toSlots(): List<PackSlot> = stacks.mapIndexed(::PackSlot)

    companion object {
        @JvmStatic
        val EMPTY = BackpackContentsComponent()
        val CODEC = PackSlot.CODEC.listOf()
            .xmap(BackpackContentsComponent::fromSlots, BackpackContentsComponent::toSlots)
        val PACKET_CODEC = ItemStack.OPTIONAL_PACKET_CODEC
            .apply(PacketCodecs.toCollection())
            .map(::BackpackContentsComponent, BackpackContentsComponent::stacks)


        fun fromSlots(slots: List<PackSlot>): BackpackContentsComponent {
            return BackpackContentsComponent(
                slots.fold(DefaultedList.of()) { acc, slot ->
                    acc[slot.index] = slot.item
                    acc
                }
            )
        }

        data class PackSlot(val index: Int, val item: ItemStack) {
            companion object {
                val CODEC: Codec<PackSlot> = RecordCodecBuilder.create {
                    it.group(
                        Codecs.NONNEGATIVE_INT.fieldOf("slot").forGetter(PackSlot::index),
                        ItemStack.CODEC.fieldOf("item").forGetter(PackSlot::item)
                    ).apply(it, ::PackSlot)
                }
            }
        }
    }
}
