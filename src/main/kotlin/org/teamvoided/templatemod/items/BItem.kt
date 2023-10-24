package org.teamvoided.templatemod.items

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.client.item.BundleTooltipData
import net.minecraft.client.item.TooltipContext
import net.minecraft.client.item.TooltipData
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World
import org.teamvoided.templatemod.api.HasInventory
import org.teamvoided.templatemod.screen.BScreenHandler
import java.util.*

class BItem : Item(FabricItemSettings().maxCount(1)), NamedScreenHandlerFactory, InvImpl {
    override var items: DefaultedList<ItemStack> = DefaultedList.ofSize(9, ItemStack.EMPTY)
//    fun getItems(): DefaultedList<ItemStack> = items

    override fun use(world: World, player: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val item = player.getStackInHand(hand)
        return if (world.isClient) {
            TypedActionResult.success(item)
        } else {
            val stack = InventoryAPI.findInvItem(item)
            if (stack != null) {
                this.items = stack.getInventory().items
                player.openHandledScreen(this)
            }
            TypedActionResult.consume(item)
        }
    }


    override fun appendTooltip(item: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        super.appendTooltip(item, world, tooltip, context)
        tooltip.add(Text.literal("yo mother").formatted(Formatting.GRAY))
    }

    override fun getTooltipData(item: ItemStack): Optional<TooltipData> {
        val iInvK = DefaultedList.of<ItemStack>()
        if (item.hasNbt()) {
            val stack = InventoryAPI.findInvItem(item)
            if (stack != null && !stack.getInventory().isEmpty) {
                val inv = stack.getInventory()
                for (i in 0 until 9) iInvK.add(inv.getStack(i))
            }
        }
        return Optional.of<TooltipData>(BundleTooltipData(iInvK, 1))
    }

    override fun createMenu(i: Int, playInv: PlayerInventory, playerEntity: PlayerEntity): ScreenHandler =
        BScreenHandler(i, playInv, this)

    override fun getDisplayName(): Text = this.name
    class InventoryIml(val stack: ItemStack) : HasInventory {
        override fun getInventory(): InvImpl {
            if (stack.orCreateNbt.contains("Items", 9)) {
                val inv = DefaultedList.ofSize(9, ItemStack.EMPTY)
                Inventories.readNbt(stack.orCreateNbt, inv)
                return InvImpl.of(inv)
            }
            return genDefault()
        }

        override fun setInventory(inv: InvImpl) {
            Inventories.writeNbt(stack.orCreateNbt, inv.items)
        }

        override fun genDefault(): InvImpl = InvImpl.ofSize(9)
    }

}