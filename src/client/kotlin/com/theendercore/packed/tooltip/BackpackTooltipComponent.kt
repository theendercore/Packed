package com.theendercore.packed.tooltip

import com.theendercore.packed.component.BackpackContentsComponent
import com.theendercore.packed.util.toCollectedStacks
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.tooltip.TooltipComponent
import net.minecraft.client.render.VertexConsumerProvider
import org.joml.Matrix4f
import kotlin.math.max

class BackpackTooltipComponent(private val backpackContents: BackpackContentsComponent) : TooltipComponent {
    override fun getHeight(): Int {
        return if (Screen.hasShiftDown()) {
            val stacks = toCollectedEntries()
            if (stacks.isEmpty()) 0
            else (max(1, (stacks.size / 6)) * 18)
        } else 0
    }

    override fun getWidth(textRenderer: TextRenderer): Int = if (Screen.hasShiftDown()) 18 * columns() else 0
    override fun drawText(
        textRenderer: TextRenderer,
        x: Int,
        y: Int,
        modelMatrix: Matrix4f?,
        vertexConsumer: VertexConsumerProvider.Immediate?
    ) = Unit

    override fun drawItems(textRenderer: TextRenderer?, x: Int, y: Int, graphics: GuiGraphics) {
        super.drawItems(textRenderer, x, y, graphics)
        if (Screen.hasShiftDown()) {
            toCollectedEntries().forEachIndexed { idx, item ->
                graphics.drawItem(item, x + idx * 18, y, 0)
                graphics.drawItemInSlot(textRenderer, item, x + idx * 18, y)
            }
        }
    }

    private fun toCollectedEntries() = backpackContents.stacks.toCollectedStacks()

    fun columns(): Int {
        val stacks = toCollectedEntries()
        return if (stacks.size > 6) 6
        else stacks.size
    }
}
