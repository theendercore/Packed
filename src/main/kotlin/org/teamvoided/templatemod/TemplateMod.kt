package org.teamvoided.templatemod

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.client.gui.screen.ingame.HandledScreens
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList
import org.slf4j.LoggerFactory
import org.teamvoided.templatemod.items.BItem
import org.teamvoided.templatemod.items.InventoryAPI
import org.teamvoided.templatemod.reg.ScreenRegistry
import org.teamvoided.templatemod.screen.BScreenHandler
import org.teamvoided.templatemod.screen.EndScreen


@Suppress("unused")

object TemplateMod {
    val LOGGER = LoggerFactory.getLogger(TemplateMod::class.java)
    const val MODID = "packed"
    fun id(path: String): Identifier = Identifier(MODID, path)

    val BITEM: Item = Registry.register(Registries.ITEM, id("bitem"), BItem())

    val BHANDLER:  ScreenHandlerType<BScreenHandler> = ScreenHandlerRegistry.registerSimple(id("bhand"), ::BScreenHandler);
    fun commonInit() {
        ScreenRegistry.init()
        InventoryAPI.init()
    }

    fun clientInit() {
        HandledScreens.register(BHANDLER, ::EndScreen);
    }


}