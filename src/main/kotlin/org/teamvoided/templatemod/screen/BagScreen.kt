package org.teamvoided.templatemod.screen

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class BagScreen(handler: BagScreenHandler, inventory: PlayerInventory, title: Text) :
    HandledScreen<BagScreenHandler>(handler, inventory, title) {
    private val rows: Int

    init {
        rows = handler.getRows()
        this.backgroundHeight = 114 + rows * 18
        this.playerInventoryTitleY = this.backgroundHeight - 94
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(graphics)
        super.render(graphics, mouseX, mouseY, delta)
        this.drawMouseoverTooltip(graphics, mouseX, mouseY)
    }

    override fun drawBackground(graphics: GuiGraphics, delta: Float, mouseX: Int, mouseY: Int) {
        graphics.fillGradient(0, 0, width, height, -1072689136, -804253680)

        val i: Int = (this.width - this.backgroundWidth) / 2
        val j: Int = (this.height - this.backgroundHeight) / 2
        graphics.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, rows * 18 + 17)
        graphics.drawTexture(TEXTURE, i, j + rows * 18 + 17, 0, 126, this.backgroundWidth, 96)
    }
    companion object {
        private val TEXTURE: Identifier = Identifier("textures/gui/container/generic_54.png")
    }
}