package com.theendercore.packed.component

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.theendercore.packed.api.InvImpl
import com.theendercore.packed.api.SortType
import com.theendercore.packed.screen.PackScreenHandler
import com.theendercore.packed.util.setBackpackContents
import com.theendercore.packed.util.toCollectedStacks
import net.minecraft.client.item.TooltipData
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.text.Text
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.dynamic.Codecs
import kotlin.math.min

data class BackpackContentsComponent(override val stacks: DefaultedList<ItemStack> = DefaultedList.ofSize(9)) :
    TooltipData, NamedScreenHandlerFactory, InvImpl {
    //  figure out how to remove this later
    var TEMPORARY_INTERNAL_STACK: ItemStack = ItemStack.EMPTY


    constructor(size: Int) : this(DefaultedList.ofSize(size, ItemStack.EMPTY))
    constructor(items: List<ItemStack>) : this() {
        items.forEachIndexed(stacks::set)
    }
    fun toSlots(): List<PackSlot> = stacks.mapIndexed(::PackSlot)


    override fun createMenu(syncId: Int, pInv: PlayerInventory, playerEntity: PlayerEntity): ScreenHandler =
        PackScreenHandler(syncId, pInv, TEMPORARY_INTERNAL_STACK)

    override fun getDisplayName(): Text = TEMPORARY_INTERNAL_STACK.name


    override fun markDirty() {
        TEMPORARY_INTERNAL_STACK.setBackpackContents(stacks)
    }

    override fun sort(type: SortType) {
        val sortedItems = stacks.toCollectedStacks().toSortedMap(type.getSort())
            .flatMap { (item, count) ->
                val itemCounts = mutableListOf<ItemStack>()
                if (count <= 64) {
                    itemCounts.add(item.copyWithCount(count))
                } else {
                    var remaining = count
                    while (remaining > 0) {
                        itemCounts.add(item.copyWithCount(min(remaining, 64)))
                        remaining -= 64
                    }
                }
                itemCounts
            }
        stacks.clear()
        sortedItems.forEachIndexed(stacks::set)
        super.sort(type)
    }

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
