package org.teamvoided.packed.screen

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class PackScreen(handler: PackScreenHandler, inventory: PlayerInventory, title: Text?) :
    HandledScreen<PackScreenHandler>(handler, inventory, title) {
    override fun init() {
        super.init()
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2
    }

    override fun drawBackground(graphics: GuiGraphics, delta: Float, mouseX: Int, mouseY: Int) {
        val i = (width - backgroundWidth) / 2
        val j = (height - backgroundHeight) / 2
        graphics.drawTexture(TEXTURE, i, j, 0, 0, backgroundWidth, backgroundHeight)
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(graphics)
        super.render(graphics, mouseX, mouseY, delta)
        drawMouseoverTooltip(graphics, mouseX, mouseY)
    }

    companion object {
        private val TEXTURE = Identifier("textures/gui/container/dispenser.png")
    }
}
