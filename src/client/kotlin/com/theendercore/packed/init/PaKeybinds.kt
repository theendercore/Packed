package com.theendercore.packed.init

import com.mojang.blaze3d.platform.InputUtil
import com.theendercore.packed.init.PaNetwork.OpenPackPayload
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.option.KeyBind

object PaKeybinds {
    private const val category = "Packed"

    private val openPackKey = KeyBindingHelper.registerKeyBinding(
        KeyBind("Open Pack", InputUtil.KEY_B_CODE, category)
    )

    private var packCooldown = 0

    fun init() {
        ClientTickEvents.END_CLIENT_TICK.register {
            if (openPackKey.isPressed && packCooldown == 0) {
                packCooldown += 5
                ClientPlayNetworking.send(OpenPackPayload)
            }

            if (packCooldown > 0) {
                packCooldown--
            }
        }
    }
}