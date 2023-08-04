package org.teamvoided.templatemod.bag

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.screen.SimpleNamedScreenHandlerFactory
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World
import org.teamvoided.templatemod.screen.w.BackpackScreenHandler
import java.util.stream.Stream


class BagItem() : Item(FabricItemSettings()) {

    override fun use(world: World, player: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val item = player.getStackInHand(hand)

        return if (!world.isClient){
            TypedActionResult.success(item)
        } else {
            player.openHandledScreen(
                SimpleNamedScreenHandlerFactory(
                    { syncId: Int, inventory: PlayerInventory?, player1: PlayerEntity? ->
                        BackpackScreenHandler(
                            syncId,
                            inventory!!,
                            getInventory(item),
                            54
                        )
                    },
                    item.name
                )
            )
            TypedActionResult.consume(item)
        }
    }

   companion object {
       fun getInventory(itemStack: ItemStack): BagInv {
           return BagInv(itemStack)
       }
   }


    class BagInv constructor(private val backpack: ItemStack) : Inventory {
        private val itemStacks = DefaultedList.ofSize(54, ItemStack.EMPTY)

        init { Inventories.readNbt(backpack.getOrCreateNbt(), itemStacks) }

        fun getItemStacks(): Stream<ItemStack> = itemStacks.stream().map { obj: ItemStack -> obj.copy() }
        override fun size(): Int = itemStacks.size

        override fun isEmpty(): Boolean {
            for (i in 0 until size()) {
                val itemStack = getStack(i)
                if (!itemStack.isEmpty) {
                    return false
                }
            }
            return true
        }

        override fun getStack(slot: Int): ItemStack {
            return itemStacks[slot]
        }

        override fun removeStack(slot: Int, amount: Int): ItemStack {
            val result = Inventories.splitStack(itemStacks, slot, amount)
            if (!result.isEmpty) {
                markDirty()
            }
            return result
        }

        override fun removeStack(slot: Int): ItemStack {
            return Inventories.removeStack(itemStacks, slot)
        }

        override fun setStack(slot: Int, stack: ItemStack) {
            itemStacks[slot] = stack
            if (stack.count > this.maxCountPerStack) {
                stack.count = this.maxCountPerStack
            }
        }

        override fun markDirty() {
            val nbt = backpack.getOrCreateNbt()
            Inventories.writeNbt(nbt, itemStacks)
        }

        override fun canPlayerUse(player: PlayerEntity): Boolean {
            return true
        }

        override fun clear() {
            itemStacks.clear()
        }
    }

}