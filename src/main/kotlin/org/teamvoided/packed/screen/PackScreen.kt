package org.teamvoided.packed.screen

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.gui.tooltip.Tooltip
import net.minecraft.client.gui.widget.TexturedButtonWidget
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class PackScreen(handler: PackScreenHandler, inventory: PlayerInventory, title: Text?) :
    HandledScreen<PackScreenHandler>(handler, inventory, title) {
    val sortBtn = TexturedButtonWidget
        .builder(Text.of("sort")) {
            val id = if (hasShiftDown()) 2 else 1
            if (handler.onButtonClick(client?.player!!, id))
                client?.interactionManager?.clickButton(this.handler.syncId, id)
        }
        .size(BTN_W, BTN_H)
        .tooltip(Tooltip.create(Text.of("hello")))
        .position(100, 100)
        .build()

    override fun init() {
        super.init()
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2
//        sortBtn.setPosition()
        this.addDrawableChild(sortBtn)
    }

    override fun drawBackground(graphics: GuiGraphics, delta: Float, mouseX: Int, mouseY: Int) {
        val i = (width - backgroundWidth) / 2
        val j = (height - backgroundHeight) / 2
        graphics.drawTexture(BG_TEXTURE, i, j, 0, 0, backgroundWidth, backgroundHeight)
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(graphics)
        super.render(graphics, mouseX, mouseY, delta)
        drawMouseoverTooltip(graphics, mouseX, mouseY)
    }

    companion object {
        private val BG_TEXTURE = Identifier("textures/gui/container/dispenser.png")
        private val BTN_TEXTURE = Identifier("textures/gui/container/creative_inventory/tabs.png")
        private const val BTN_W = 26
        private const val BTN_H = 32
    }

}
