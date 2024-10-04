package com.theendercore.packed_up.items

import com.theendercore.packed_up.inv.BackpackInventory
import com.theendercore.packed_up.screen.BackpackScreenHandler
import com.theendercore.packed_up.util.NamedScreenMaker
import com.theendercore.packed_up.util.getBackpackContents
import com.theendercore.packed_up.util.openBackpack
import net.minecraft.client.item.TooltipData
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Equippable
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.Holder
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import java.util.*

class BackpackItem(settings: Settings) : Item(settings), Equippable {
    override fun use(world: World, player: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = player.getStackInHand(hand)

        return if (world.isClient) TypedActionResult.pass(stack)
        else if (player.openBackpack(stack)) TypedActionResult.success(stack)
        else TypedActionResult.pass(stack)
    }

    override fun getTooltipData(stack: ItemStack): Optional<TooltipData> =
        Optional.ofNullable(stack.getBackpackContents())

    override fun getPreferredSlot(): EquipmentSlot = EquipmentSlot.CHEST
    override fun getEquipSound(): Holder<SoundEvent> = SoundEvents.ITEM_ARMOR_EQUIP_LEATHER
    override fun canBeNested(): Boolean = false

    companion object {
        fun makeBackpackScreen(stack: ItemStack): NamedScreenHandlerFactory = NamedScreenMaker(stack.name) { syncId, inv, _ ->
            BackpackScreenHandler(syncId, inv, BackpackInventory(stack))
        }
    }
}
