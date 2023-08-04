package org.teamvoided.templatemod.reg

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType.ExtendedFactory
import net.minecraft.feature_flags.FeatureFlags
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import org.teamvoided.templatemod.TemplateMod.id
import org.teamvoided.templatemod.screen.w.BackpackScreenHandler
import org.teamvoided.templatemod.screen.w.temp.TempScreenHandler

object ScreenRegistry {

    val BackPack = register("backpack") { i, pInv -> BackpackScreenHandler( pInv, i) }
    val TEMP_SCREEN_HANDLER = register("temp") { i, inv, buf-> TempScreenHandler(inv, i) }

    fun init() {}
    private fun <T : ScreenHandler> register(string: String, factory: ExtendedFactory<T>): ScreenHandlerType<T> =
        Registry.register(Registries.SCREEN_HANDLER_TYPE, id(string), ExtendedScreenHandlerType(factory))

    private fun <T : ScreenHandler> register(string: String, factory: ScreenHandlerType.Factory<T>): ScreenHandlerType<T> =
        Registry.register(Registries.SCREEN_HANDLER_TYPE, id(string), ScreenHandlerType(factory, FeatureFlags.VANILLA_SET))
}