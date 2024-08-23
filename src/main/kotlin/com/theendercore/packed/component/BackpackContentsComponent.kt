package com.theendercore.packed.component

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.theendercore.packed.util.defaultedList
import net.minecraft.client.item.TooltipData
import net.minecraft.item.ItemStack
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.dynamic.Codecs

@Suppress("MagicNumber")
data class BackpackContentsComponent(val stacks: DefaultedList<ItemStack>) : TooltipData {
    constructor(size: Int) : this(defaultedList(size))
    constructor(items: List<ItemStack>) : this(items.size) {
        items.forEachIndexed(stacks::set)
    }

    fun slotList(): List<PackSlot> = stacks.mapIndexedNotNull(PackSlot::slotOrNull)

    companion object {
        private const val MAX_SIZE = 2048

        @JvmStatic
        val EMPTY = BackpackContentsComponent(defaultedList(9))
        val CODEC: Codec<BackpackContentsComponent> = PackSlot.CODEC.sizeLimitedListOf(MAX_SIZE)
            .xmap(BackpackContentsComponent::fromSlots, BackpackContentsComponent::slotList)
        val PACKET_CODEC: PacketCodec<RegistryByteBuf, BackpackContentsComponent> =
            ItemStack.OPTIONAL_PACKET_CODEC.apply(PacketCodecs.toCollection(MAX_SIZE))
                .map(::BackpackContentsComponent, BackpackContentsComponent::stacks)


        @Suppress("MemberVisibilityCanBePrivate")
        fun fromSlots(slots: List<PackSlot>): BackpackContentsComponent {
            val size = slots.maxOf { it.index + 1 }
            return BackpackContentsComponent(slots.fold(defaultedList(if (size < 9) 9 else size)) { acc, slot ->
                acc[slot.index] = slot.item
                acc
            })
        }

        data class PackSlot(val index: Int, val item: ItemStack) {
            companion object {
                fun slotOrNull(index: Int, stack: ItemStack) = if (stack.isEmpty) null else PackSlot(index, stack)
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
