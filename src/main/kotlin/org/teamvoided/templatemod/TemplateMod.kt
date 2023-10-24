package org.teamvoided.templatemod

import net.fabricmc.loader.api.FabricLoader
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory
import org.teamvoided.templatemod.init.PaKeybinds
import org.teamvoided.templatemod.init.PaNetwork
import org.teamvoided.templatemod.init.PaScreens
import org.teamvoided.templatemod.items.BItem
import org.teamvoided.templatemod.items.InventoryAPI
import org.teamvoided.templatemod.items.PackItem


@Suppress("unused")

object TemplateMod {
    val log = LoggerFactory.getLogger(TemplateMod::class.java)
    const val MODID = "packed"
    var trinketsInstalled = false
    fun id(path: String): Identifier = Identifier(MODID, path)

    val BITEM: Item = Registry.register(Registries.ITEM, id("bitem"), BItem())

    val PACK: PackItem = Registry.register(Registries.ITEM, id("pack"), PackItem())

    fun commonInit() {
        if (FabricLoader.getInstance().isModLoaded("trinkets")) trinketsInstalled = true
        PaScreens.initServer()
        PaNetwork.initServer()
        InventoryAPI.init()
    }

    fun clientInit() {
        PaScreens.initClient()
        PaKeybinds.init()
        PaNetwork.initClient()
    }


}