package com.theendercore.packed_up

import com.theendercore.packed_up.init.*
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused", "MemberVisibilityCanBePrivate")
object PackedUp {
    val log: Logger = LoggerFactory.getLogger(PackedUp::class.java)
    const val MODID = "packed_up"
    var trinketsInstalled = false

    fun init() {
        log.info("Packing up your things")

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
