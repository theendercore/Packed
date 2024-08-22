package com.theendercore.packed.tooltip

import com.theendercore.packed.component.BackpackContentsComponent
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.font.TextRenderer.TextLayerType
import net.minecraft.client.gui.tooltip.TooltipComponent
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.text.Text
import org.joml.Matrix4f

class BackpackTooltipComponent(val backpackContents: BackpackContentsComponent) : TooltipComponent {
    override fun getHeight(): Int = 20

    override fun getWidth(textRenderer: TextRenderer): Int = 40
    override fun drawText(
        textRenderer: TextRenderer,
        x: Int,
        y: Int,
        modelMatrix: Matrix4f?,
        vertexConsumer: VertexConsumerProvider.Immediate?
    ) {
        textRenderer.draw(
            Text.of("${backpackContents.stacks.size}"),
            x.toFloat(),
            y.toFloat(),
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
