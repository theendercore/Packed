package org.teamvoided.packed.init

import com.mojang.blaze3d.platform.InputUtil
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.option.KeyBind
import net.minecraft.network.PacketByteBuf
import org.teamvoided.packed.init.PaNetwork.OPEN_PACK_WITH_KEY

object PaKeybinds {
    private const val category = "Packed"

    private val openPackKey: KeyBind = KeyBindingHelper.registerKeyBinding(
        KeyBind("Open Pack", InputUtil.KEY_B_CODE, category)
    )

    private var packCooldown = 0

    fun init() {
        ClientTickEvents.END_CLIENT_TICK.register {
            if (openPackKey.isPressed && packCooldown == 0) {
                packCooldown += 5
                ClientPlayNetworking.send(OPEN_PACK_WITH_KEY, PacketByteBuf(Unpooled.buffer()))
            }

            if (packCooldown > 0) {
                packCooldown--
            }
        }
    }
}