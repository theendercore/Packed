package org.teamvoided.packed.init

import net.minecraft.client.gui.screen.ingame.HandledScreens
import net.minecraft.feature_flags.FeatureFlags
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.screen.ScreenHandlerType.Factory
import org.teamvoided.packed.Packed.id
import org.teamvoided.packed.screen.PackScreen
import org.teamvoided.packed.screen.PackScreenHandler

object PaScreens {
    val PACK_HANDLER = reg("pack_handler", ::PackScreenHandler)

    fun initServer() {}
    fun initClient() {
        HandledScreens.register(PACK_HANDLER, ::PackScreen)
    }

    private fun <T : ScreenHandler> reg(id: String, factory: Factory<T>): ScreenHandlerType<T> = Registry.register(
        Registries.SCREEN_HANDLER_TYPE, id(id), ScreenHandlerType(factory, FeatureFlags.DEFAULT_SET)
    )

}