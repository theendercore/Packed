package org.teamvoided.templatemod.bag.p

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import org.teamvoided.templatemod.screen.w.BackpackScreenHandler


class BackPack(settings: Settings) : Item(settings), NamedScreenHandlerFactory {
    //    override val items: DefaultedList<ItemStack> = DefaultedList.ofSize(9, ItemStack.EMPTY);
    val darkMagic = SimpleInventory(9)

    override fun use(world: World, player: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val item = player.getStackInHand(hand)
        return if (world.isClient) TypedActionResult.success(item)
        else {
            player.openHandledScreen(this)
            TypedActionResult.consume(item)
        }
    }

    override fun createMenu(i: Int, playInv: PlayerInventory, playerEntity: PlayerEntity): ScreenHandler =
        BackpackScreenHandler(i, playInv, darkMagic, 1)

    override fun getDisplayName(): Text = name
    override fun shouldCloseCurrentScreen(): Boolean = false
//    override fun writeScreenOpeningData(player: ServerPlayerEntity, buf: PacketByteBuf) {
//        LOGGER.info(":)")
//    }

}