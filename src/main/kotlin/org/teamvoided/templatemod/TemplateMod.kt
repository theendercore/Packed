package org.teamvoided.templatemod

import net.fabricmc.loader.api.FabricLoader
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory
import org.teamvoided.templatemod.init.PaItems
import org.teamvoided.templatemod.init.PaKeybinds
import org.teamvoided.templatemod.init.PaNetwork
import org.teamvoided.templatemod.init.PaScreens

@Suppress("unused")

object TemplateMod {
    val log = LoggerFactory.getLogger(TemplateMod::class.java)
    const val MODID = "packed"
    var trinketsInstalled = false
    fun id(path: String): Identifier = Identifier(MODID, path)
    fun commonInit() {
        if (FabricLoader.getInstance().isModLoaded("trinkets")) trinketsInstalled = true
        PaItems.init()
        PaScreens.initServer()
        PaNetwork.initServer()
    }

    fun clientInit() {
        PaScreens.initClient()
        PaKeybinds.init()
        PaNetwork.initClient()
    }


}