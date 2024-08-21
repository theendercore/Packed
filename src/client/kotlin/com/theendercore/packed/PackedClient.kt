package com.theendercore.packed

import com.theendercore.packed.Packed.trinketsInstalled
import com.theendercore.packed.init.PaKeybinds
import com.theendercore.packed.init.PaScreens.PACK_HANDLER
import com.theendercore.packed.init.PakItems
import com.theendercore.packed.rendering.ArmorPackRenderer
import com.theendercore.packed.rendering.TrinketPackRenderer
import com.theendercore.packed.screen.PackScreen
import dev.emi.trinkets.api.client.TrinketRendererRegistry
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.client.gui.screen.ingame.HandledScreens
import net.minecraft.component.type.DyedColorComponent
import net.minecraft.item.ItemStack

@Suppress("unused")
object PackedClient {
    fun init() {
        PaKeybinds.init()
        HandledScreens.register(PACK_HANDLER, ::PackScreen)
        ArmorRenderer.register(ArmorPackRenderer(), PakItems.PACK)

        ColorProviderRegistry.ITEM.register(::getDyedColor, PakItems.PACK)

        if (trinketsInstalled) TrinketRendererRegistry.registerRenderer(PakItems.PACK, TrinketPackRenderer())
    }

    fun getDyedColor(item: ItemStack, layer: Int): Int = DyedColorComponent.getColorOrDefault(item, 0xFFFFFF)
}