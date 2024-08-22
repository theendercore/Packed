package com.theendercore.packed.items

import com.theendercore.packed.api.InvImpl
import com.theendercore.packed.component.BackpackContentsComponent
import com.theendercore.packed.init.PakDataComponents
import com.theendercore.packed.screen.PackScreenHandler
import com.theendercore.packed.util.toCollectedStacks
import net.minecraft.client.item.TooltipConfig
import net.minecraft.client.item.TooltipData
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.Equippable
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.Holder
import net.minecraft.registry.Registries
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World
import java.util.*
import kotlin.math.min

class PackItem(settings: Settings) : Item(settings), NamedScreenHandlerFactory, InvImpl, Equippable {
    override var items: DefaultedList<ItemStack> = DefaultedList.ofSize(9, ItemStack.EMPTY)
    private var cStack: ItemStack = ItemStack.EMPTY

    override fun use(world: World, player: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = player.getStackInHand(hand)

        return if (world.isClient) TypedActionResult.pass(stack)
        else if (openPack(stack, player)) TypedActionResult.success(stack)
        else TypedActionResult.pass(stack)
    }

    fun openPack(stack: ItemStack, player: PlayerEntity): Boolean {
        return if (stack.item is PackItem) {
            this.items = stack.getBackpackContents()?.stacks ?: return false
            cStack = stack
            player.openHandledScreen(stack.item as PackItem)
            true
        } else false
    }

    //remove
    override fun appendTooltip(
        stack: ItemStack?, context: TooltipContext?, tooltip: MutableList<Text>?, config: TooltipConfig?
    ) {
        super.appendTooltip(stack, context, tooltip, config)
//        tooltip?.add(Text.literal("6/9").formatted(Formatting.GRAY))
    }

    override fun getTooltipData(stack: ItemStack): Optional<TooltipData> {
        return Optional.ofNullable(stack.getBackpackContents())
    }

    override fun createMenu(i: Int, pInv: PlayerInventory, playerEntity: PlayerEntity): ScreenHandler =
        PackScreenHandler(i, pInv, this, cStack)

    override fun getDisplayName(): Text = cStack.name
    override fun getPreferredSlot(): EquipmentSlot = EquipmentSlot.CHEST
    override fun getEquipSound(): Holder<SoundEvent> = SoundEvents.ITEM_ARMOR_EQUIP_LEATHER
    override fun canBeNested(): Boolean = false
    override fun markDirty() {
        cStack.setBackpackContents(this.items)
    }

    override fun sort(type: InvImpl.SortType) {
        val sortedItems = items.toCollectedStacks().toSortedMap(type.getSort())
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
        items.clear()
        sortedItems.forEachIndexed(items::set)
        super.sort(type)
    }

    companion object {
        fun ItemStack.getBackpackContents(): BackpackContentsComponent? = this.get(PakDataComponents.BACKPACK_CONTENTS)
        fun ItemStack.setBackpackContents(stacks: DefaultedList<ItemStack>) =
            this.set(PakDataComponents.BACKPACK_CONTENTS, BackpackContentsComponent(stacks))

        fun ItemStack.charAt(id: Int) = this.item.id.path[id].code //.name.string[id].code
        val Item.id get() = Registries.ITEM.getId(this)
    }
}
