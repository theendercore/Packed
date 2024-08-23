package com.theendercore.packed.screen

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.gui.tooltip.Tooltip
import net.minecraft.client.gui.widget.button.ButtonWidget
import net.minecraft.client.gui.widget.button.TexturedButtonWidget
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class PackScreen(handler: PackScreenHandler, inventory: PlayerInventory, title: Text?) :
    HandledScreen<PackScreenHandler>(handler, inventory, title) {
    var sortBtn: ButtonWidget? = null

    override fun init() {
        super.init()
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2
        remove(sortBtn)
        sortBtn = TexturedButtonWidget
            .builder(Text.of("Sort")) {
                val id = if (hasShiftDown()) 2 else 1
                if (handler.onButtonClick(client?.player!!, id))
                    client?.interactionManager?.clickButton(this.handler.syncId, id)
            }
            .size(BTN_W, BTN_H)
            .tooltip(Tooltip.create(Text.of("Sort")))
            .position((backgroundWidth - 10) / 2, titleY + 10)
            .build()
        addDrawableSelectableElement(sortBtn)
    }

    override fun drawBackground(graphics: GuiGraphics, delta: Float, mouseX: Int, mouseY: Int) {
        graphics.drawTexture(
            BACKGROUND,
            (width - backgroundWidth) / 2, (height - backgroundHeight) / 2,
            0, 0,
            backgroundWidth, backgroundHeight
        )
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(graphics, mouseX, mouseY, delta)
        super.render(graphics, mouseX, mouseY, delta)
        drawMouseoverTooltip(graphics, mouseX, mouseY)
//        sortBtn?.setPosition((graphics.scaledWindowWidth / 2) + 45, 120)
    }

    companion object {
        private val BACKGROUND = Identifier.ofDefault("textures/gui/container/dispenser.png")
        private val BUTTON = Identifier.ofDefault("textures/gui/container/creative_inventory/tabs.png")
        private const val BTN_W = 34
        private const val BTN_H = 16
    }

}
