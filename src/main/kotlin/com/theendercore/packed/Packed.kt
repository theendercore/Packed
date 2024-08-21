package com.theendercore.packed

import com.theendercore.packed.init.PaNetwork
import com.theendercore.packed.init.PaScreens
import com.theendercore.packed.init.PakItems
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory

@Suppress("unused")
object Packed {
    val log = LoggerFactory.getLogger(Packed::class.java)
    const val MODID = "packed"
    var trinketsInstalled = false

    fun id(path: String): Identifier = Identifier.of(MODID, path)
    fun init() {
        if (FabricLoader.getInstance().isModLoaded("trinkets")) trinketsInstalled = true
        PakItems.init()
        PaScreens.init()
        PaNetwork.init()
    }
}
