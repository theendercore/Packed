package com.theendercore.packed.items

import com.theendercore.packed.api.InvImpl
import com.theendercore.packed.screen.PackScreenHandler
import net.minecraft.client.item.TooltipConfig
import net.minecraft.client.item.TooltipData
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.Equippable
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.Holder
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World
import java.util.*

class PackItem : Item(Settings().maxCount(1)), NamedScreenHandlerFactory, InvImpl, Equippable {
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
            cStack = stack
            this.items = stack.getInventory().items
            player.openHandledScreen(stack.item as PackItem)
            true
        } else false
    }

    override fun appendTooltip(
        stack: ItemStack?, context: TooltipContext?, tooltip: MutableList<Text>?, config: TooltipConfig?
    ) {
        super.appendTooltip(stack, context, tooltip, config)
        tooltip?.add(Text.literal("6/9").formatted(Formatting.GRAY))
    }

    override fun getTooltipData(stack: ItemStack): Optional<TooltipData> {
//        var inv = DefaultedList.of<ItemStack>()
//        if (stack.item is PackItem && stack.hasNbt()) {
//            inv = stack.getInventory().items
//        }
//        return Optional.of<TooltipData>(BundleTooltipData(inv, 1))
        return Optional.empty()
    }

    override fun createMenu(i: Int, pInv: PlayerInventory, playerEntity: PlayerEntity): ScreenHandler =
        PackScreenHandler(i, pInv, this, cStack)

    override fun getDisplayName(): Text = cStack.name
    override fun getPreferredSlot(): EquipmentSlot = EquipmentSlot.CHEST
    override fun getEquipSound(): Holder<SoundEvent> = SoundEvents.ITEM_ARMOR_EQUIP_LEATHER
    override fun canBeNested(): Boolean = false
    override fun getDefaultStack(): ItemStack {
        val stack = super.getDefaultStack()
        stack.setInventory(genDefault())
        return stack
    }

    override fun markDirty() {
        cStack.setInventory(this)
    }

    override fun sort(type: InvImpl.SortType) {
        val toSort = mutableListOf<ItemStack>()
        items.stream().forEach { if (!it.isEmpty) toSort.add(it) }

        val sorted = when (type) {
            InvImpl.SortType.NORMAL -> toSort.stream().sorted { a, b -> a.charAt(0) - b.charAt(0) }
            InvImpl.SortType.REVERSED -> toSort.stream().sorted { a, b -> b.charAt(0) - a.charAt(0) }
            else -> toSort.stream()
        }.toArray()
        items.clear()
        sorted.forEachIndexed { i: Int, x: Any -> items[i] = x as ItemStack }
        super.sort(type)
    }

    companion object {
        fun ItemStack.getInventory(): InvImpl {
//            if (this.contains("Items", Type.LIST_TYPE)) {
//                val inv = DefaultedList.ofSize(9, ItemStack.EMPTY)
//                Inventories.readNbt(this.orCreateNbt, inv)
//                return InvImpl.of(inv)
//            }
            return genDefault()
        }

        fun ItemStack.setInventory(inv: InvImpl) {
//            Inventories.writeNbt(this.orCreateNbt, inv.items)
        }

        fun genDefault(): InvImpl = InvImpl.ofSize(9)

        fun ItemStack.charAt(id: Int) = this.item.name.string[id].code
    }
}