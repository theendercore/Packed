package com.theendercore.packed

import com.theendercore.packed.Packed.trinketsInstalled
import com.theendercore.packed.component.BackpackContentsComponent
import com.theendercore.packed.init.PakItems
import com.theendercore.packed.init.PakKeybindings
import com.theendercore.packed.init.PakScreens.PACK_HANDLER
import com.theendercore.packed.rendering.ArmorBackpackRenderer
import com.theendercore.packed.rendering.TrinketBackpackRenderer
import com.theendercore.packed.screen.BackpackScreen
import com.theendercore.packed.tooltip.BackpackTooltipComponent
import com.theendercore.packed.util.WHITE
import dev.emi.trinkets.api.client.TrinketRendererRegistry
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback
import net.minecraft.client.gui.screen.ingame.HandledScreens
import net.minecraft.component.type.DyedColorComponent
import net.minecraft.item.ItemStack

@Suppress("unused", "MemberVisibilityCanBePrivate")
object PackedClient {
    fun init() {
        PakKeybindings.init()
        HandledScreens.register(PACK_HANDLER, ::BackpackScreen)
        ArmorRenderer.register(ArmorBackpackRenderer(), PakItems.PACK)

        ColorProviderRegistry.ITEM.register(::getDyedColor, PakItems.PACK)

        TooltipComponentCallback.EVENT.register {
            if (it is BackpackContentsComponent) BackpackTooltipComponent(it)
            else null
        }
        if (trinketsInstalled) TrinketRendererRegistry.registerRenderer(PakItems.PACK, TrinketBackpackRenderer())
    }

    @Suppress("MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")
    fun getDyedColor(item: ItemStack, ignored: Int): Int = DyedColorComponent.getColorOrDefault(item, WHITE)
}
