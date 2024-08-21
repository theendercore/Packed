package com.theendercore.packed

import com.theendercore.packed.Packed.trinketsInstalled
import com.theendercore.packed.init.PaKeybinds
import com.theendercore.packed.init.PaNetwork.OpenPackPayload
import com.theendercore.packed.init.PaScreens.PACK_HANDLER
import com.theendercore.packed.init.PakItems
import com.theendercore.packed.rendering.ArmorPackRenderer
import com.theendercore.packed.rendering.TrinketPackRenderer
import com.theendercore.packed.screen.PackScreen
import dev.emi.trinkets.api.client.TrinketRendererRegistry
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.minecraft.client.gui.screen.ingame.HandledScreens

@Suppress("unused")
object PackedClient {
    fun init() {
        PaKeybinds.init()
        HandledScreens.register(PACK_HANDLER, ::PackScreen)
        ArmorRenderer.register(ArmorPackRenderer(), PakItems.PACK)

        if (trinketsInstalled) TrinketRendererRegistry.registerRenderer(PakItems.PACK, TrinketPackRenderer())
    }
}