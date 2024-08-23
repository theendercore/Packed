package com.theendercore.packed

import com.theendercore.packed.init.*
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused", "MemberVisibilityCanBePrivate")
object Packed {
    val log: Logger = LoggerFactory.getLogger(Packed::class.java)
    const val MODID = "packed"
    var trinketsInstalled = false

    fun init() {
        if (FabricLoader.getInstance().isModLoaded("trinkets")) trinketsInstalled = true
        PakItems.init()
        PakDataComponents.init()
        PakScreens.init()
        PakNetwork.init()
        PakTabs.init()
    }

    fun id(path: String): Identifier = Identifier.of(MODID, path)
    fun id(namespace: String, path: String): Identifier = Identifier.of(namespace, path)
}
