package org.teamvoided.templatemod.screen.w

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class BackpackScreen(
    handler: BackpackScreenHandler, inventory: PlayerInventory, title: Text,
) :  HandledScreen<BackpackScreenHandler>(handler, inventory, title), ScreenHandlerProvider<BackpackScreenHandler> {
    private val TEXTURE = Identifier("textures/gui/container/generic_54.png")
    private var rows = 1;
    init {
        rows = handler.rows
        backgroundHeight = 114 + rows * 18
        playerInventoryTitleY = backgroundHeight - 94
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(graphics)
        super.render(graphics, mouseX, mouseY, delta)
        drawMouseoverTooltip(graphics, mouseX, mouseY)
    }

    override fun drawBackground(graphics: GuiGraphics, delta: Float, mouseX: Int, mouseY: Int) {
        val i = (width - backgroundWidth) / 2
        val j = (height - backgroundHeight) / 2
        graphics.drawTexture(TEXTURE, i, j, 0, 0, backgroundWidth, rows * 18 + 17)
        graphics.drawTexture(TEXTURE, i, j + rows * 18 + 17, 0, 126, backgroundWidth, 96)
    }

}