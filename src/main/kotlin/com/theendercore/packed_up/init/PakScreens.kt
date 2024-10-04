package com.theendercore.packed_up.init

import com.theendercore.packed_up.PackedUp.id
import com.theendercore.packed_up.screen.BackpackScreenHandler
import com.theendercore.packed_up.util.register
import net.minecraft.feature_flags.FeatureFlags
import net.minecraft.registry.Registries
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.screen.ScreenHandlerType.Factory

@Suppress("SameParameterValue", "UNCHECKED_CAST")
object PakScreens {
    fun init() = Unit
    val PACK_HANDLER = reg("pack_handler", ::BackpackScreenHandler)

    private fun <T : ScreenHandler> reg(id: String, factory: Factory<T>): ScreenHandlerType<T> =
        Registries.SCREEN_HANDLER_TYPE.register(id(id), ScreenHandlerType(factory, FeatureFlags.DEFAULT_SET))
                as ScreenHandlerType<T>

}
