package org.teamvoided.templatemod.init

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType.ExtendedFactory
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.client.gui.screen.ingame.HandledScreens
import net.minecraft.feature_flags.FeatureFlags
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import org.teamvoided.templatemod.TemplateMod.id
import org.teamvoided.templatemod.screen.BScreenHandler
import org.teamvoided.templatemod.screen.EndScreen
import org.teamvoided.templatemod.screen.PackScreen
import org.teamvoided.templatemod.screen.PackScreenHandler

object PaScreens {
    val BHANDLER: ScreenHandlerType<BScreenHandler> = ScreenHandlerRegistry.registerSimple(
        id("bhand"),
        ::BScreenHandler
    )

    val PACK_HANDLER: ScreenHandlerType<PackScreenHandler> = ScreenHandlerRegistry.registerSimple(
        id("pack_handler"),
        ::PackScreenHandler
    )


    fun initServer() {}
    fun initClient() {
        HandledScreens.register(BHANDLER, ::EndScreen)
        HandledScreens.register(PACK_HANDLER, ::PackScreen)
    }

    private fun <T : ScreenHandler> register(string: String, factory: ExtendedFactory<T>): ScreenHandlerType<T> =
        Registry.register(Registries.SCREEN_HANDLER_TYPE, id(string), ExtendedScreenHandlerType(factory))

    private fun <T : ScreenHandler> register(
        string: String,
        factory: ScreenHandlerType.Factory<T>,
    ): ScreenHandlerType<T> =
        Registry.register(
            Registries.SCREEN_HANDLER_TYPE,
            id(string),
            ScreenHandlerType(factory, FeatureFlags.VANILLA_SET)
        )
}