package org.teamvoided.templatemod

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.client.gui.screen.ingame.HandledScreens
import net.minecraft.feature_flags.FeatureFlags
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory
import org.teamvoided.templatemod.bag.BagItem
import org.teamvoided.templatemod.bag.p.BackPack
import org.teamvoided.templatemod.reg.ScreenRegistry
import org.teamvoided.templatemod.screen.BagScreen
import org.teamvoided.templatemod.screen.BagScreenHandler
import org.teamvoided.templatemod.screen.w.BackpackScreen
import org.teamvoided.templatemod.screen.w.temp.TempScreen


@Suppress("unused")

object TemplateMod {
    val LOGGER = LoggerFactory.getLogger(TemplateMod::class.java)
    const val MODID = "packed"
    fun id(path: String): Identifier = Identifier(MODID, path)

    val Backpack: BackPack = Registry.register(Registries.ITEM, id("backpack"), BackPack(FabricItemSettings()))
    val BAG_ITEM: BagItem = Registry.register(Registries.ITEM, id("bag"), BagItem())

    val BAG = Registry
        .register(
            Registries.SCREEN_HANDLER_TYPE,
            id("bag"),
            ScreenHandlerType(::BagScreenHandler, FeatureFlags.VANILLA_SET)
        )


    fun commonInit() {
        LOGGER.info("Ba Backed")
        ScreenRegistry.init()
    }

    fun clientInit() {
        HandledScreens.register(BAG, ::BagScreen);
        HandledScreens.register(ScreenRegistry.TEMP_SCREEN_HANDLER, ::TempScreen);
        HandledScreens.register(ScreenRegistry.BackPack, ::BackpackScreen)
    }
}