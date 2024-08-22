package com.theendercore.packed.tooltip

import com.theendercore.packed.component.BackpackContentsComponent
import com.theendercore.packed.util.toCollectedStacks
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.font.TextRenderer.TextLayerType
import net.minecraft.client.gui.tooltip.TooltipComponent
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.text.Text
import org.joml.Matrix4f

class BackpackTooltipComponent(private val backpackContents: BackpackContentsComponent) : TooltipComponent {
    override fun getHeight(): Int = (collectedText().size * 9) + 1
    override fun getWidth(textRenderer: TextRenderer): Int {
        val stacks = collectedText()
        return if (stacks.isEmpty()) 0
        else collectedText().maxOf(textRenderer::getWidth)
    }

    override fun drawText(
        textRenderer: TextRenderer,
        x: Int,
        y: Int,
        modelMatrix: Matrix4f?,
        vertexConsumer: VertexConsumerProvider.Immediate?
    ) {
        collectedText().forEachIndexed { idx, text ->
            textRenderer.draw(
                text,
                x.toFloat(),
                y.toFloat() + idx * textRenderer.fontHeight,
                -1,
                true,
                modelMatrix,
                vertexConsumer,
                TextLayerType.NORMAL,
                0,
                0xF000F0
            )
        }
    }

    private fun collectedText() = backpackContents.stacks.toCollectedStacks().entries
        .map { (item, count) -> Text.translatable("%sx %s", count, item.item) }
}
