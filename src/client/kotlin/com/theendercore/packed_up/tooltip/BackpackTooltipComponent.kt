package com.theendercore.packed_up.tooltip

import com.theendercore.packed_up.component.BackpackContentsComponent
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.tooltip.TooltipComponent
import net.minecraft.item.ItemStack
import kotlin.math.ceil
import kotlin.math.max

@Suppress("MagicNumber")
class BackpackTooltipComponent(backpackContents: BackpackContentsComponent) : TooltipComponent {
    private var compiled = backpackContents.stacks.toList().filterNot(ItemStack::isEmpty)

    override fun getHeight(): Int {
        return if (Screen.hasShiftDown()) {
            if (compiled.isEmpty()) 0
            else (max(1, ceil(compiled.size / 8.0).toInt()) * 14)
        } else 0
    }

    override fun getWidth(textRenderer: TextRenderer): Int = if (Screen.hasShiftDown()) 14 * columns() else 0
    override fun drawItems(textRenderer: TextRenderer?, x: Int, y: Int, graphics: GuiGraphics) {
        if (Screen.hasShiftDown()) {
            super.drawItems(textRenderer, x, y, graphics)
            compiled.chunked(8).forEachIndexed { rowIdx, list ->
                list.forEachIndexed { colIdx, item ->
                    graphics.matrices.push()
                    graphics.matrices.translate(x.toDouble(), y.toDouble(), 1.0)
                    graphics.matrices.scale(0.8f, 0.8f, 0.8f)
                    graphics.drawItem(item, colIdx * 17, rowIdx * 17, 0)
                    graphics.drawItemInSlot(textRenderer, item, colIdx * 17, rowIdx * 17)
                    graphics.matrices.pop()
                }
            }
        }
    }

    fun columns(): Int = if (compiled.size > 8) 8 else compiled.size
}
