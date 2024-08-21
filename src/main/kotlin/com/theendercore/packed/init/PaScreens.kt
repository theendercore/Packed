package com.theendercore.packed.init

import net.minecraft.feature_flags.FeatureFlags
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.screen.ScreenHandlerType.Factory
import com.theendercore.packed.Packed.id
import com.theendercore.packed.screen.PackScreenHandler

object PaScreens {
    val PACK_HANDLER = reg("pack_handler", ::PackScreenHandler)

    fun init() {}
    private fun <T : ScreenHandler> reg(id: String, factory: Factory<T>): ScreenHandlerType<T> = Registry.register(
        Registries.SCREEN_HANDLER_TYPE, id(id), ScreenHandlerType(factory, FeatureFlags.DEFAULT_SET)
    )
}
