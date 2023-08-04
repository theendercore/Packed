package org.teamvoided.templatemod.screen.w.temp

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier


class TempScreen(handler: TempScreenHandler, inventory: PlayerInventory, title: Text) :
    HandledScreen<TempScreenHandler>(handler, inventory, title) {
    private val TEXTURE: Identifier = Identifier("minecraft", "textures/gui/container/dispenser.png")
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

    override fun init() {
        super.init()
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2
    }
}