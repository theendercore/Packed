package org.teamvoided.packed

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory
import org.teamvoided.packed.client.ArmorPackRenderer
import org.teamvoided.packed.compat.Trinkets
import org.teamvoided.packed.init.PaItems
import org.teamvoided.packed.init.PaKeybinds
import org.teamvoided.packed.init.PaNetwork
import org.teamvoided.packed.init.PaScreens

@Suppress("unused")
object Packed {
    val log = LoggerFactory.getLogger(Packed::class.java)
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
        ArmorRenderer.register(ArmorPackRenderer(), PaItems.PACK)
        if (trinketsInstalled) Trinkets.initClient()
    }


}