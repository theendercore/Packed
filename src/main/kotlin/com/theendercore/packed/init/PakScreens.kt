package com.theendercore.packed.init

import com.theendercore.packed.Packed.id
import com.theendercore.packed.screen.PackScreenHandler
import com.theendercore.packed.util.register
import net.minecraft.feature_flags.FeatureFlags
import net.minecraft.registry.Registries
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.screen.ScreenHandlerType.Factory

object PakScreens {
    fun init() = Unit
    val PACK_HANDLER = reg("pack_handler", ::PackScreenHandler)

    private fun <T : ScreenHandler> reg(id: String, factory: Factory<T>): ScreenHandlerType<T> =
        Registries.SCREEN_HANDLER_TYPE.register(id(id), ScreenHandlerType(factory, FeatureFlags.DEFAULT_SET))
                as ScreenHandlerType<T>

}
