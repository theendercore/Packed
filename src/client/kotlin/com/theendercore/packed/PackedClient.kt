package com.theendercore.packed

import com.theendercore.packed.Packed.trinketsInstalled
import com.theendercore.packed.component.BackpackContentsComponent
import com.theendercore.packed.init.PakKeybindings
import com.theendercore.packed.init.PakScreens.PACK_HANDLER
import com.theendercore.packed.init.PakItems
import com.theendercore.packed.rendering.ArmorPackRenderer
import com.theendercore.packed.rendering.TrinketPackRenderer
import com.theendercore.packed.screen.PackScreen
import com.theendercore.packed.tooltip.BackpackTooltipComponent
import dev.emi.trinkets.api.client.TrinketRendererRegistry
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback
import net.minecraft.client.gui.screen.ingame.HandledScreens
import net.minecraft.component.type.DyedColorComponent
import net.minecraft.item.ItemStack

@Suppress("unused")
object PackedClient {
    fun init() {
        PakKeybindings.init()
        HandledScreens.register(PACK_HANDLER, ::PackScreen)
        ArmorRenderer.register(ArmorPackRenderer(), PakItems.PACK)

        ColorProviderRegistry.ITEM.register(::getDyedColor, PakItems.PACK)

        TooltipComponentCallback.EVENT.register {
            if (it is BackpackContentsComponent) BackpackTooltipComponent(it)
            else null
        }
        if (trinketsInstalled) TrinketRendererRegistry.registerRenderer(PakItems.PACK, TrinketPackRenderer())
    }

    fun getDyedColor(item: ItemStack, ignored: Int): Int = DyedColorComponent.getColorOrDefault(item, 0xFFFFFF)
}
