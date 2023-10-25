package org.teamvoided.packed.client

import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.model.json.ModelTransformationMode
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.Axis
import org.teamvoided.packed.items.PackItem


object PackRenderer {
    private val renderer = MinecraftClient.getInstance().itemRenderer
    fun render(
        matrices: MatrixStack, vertexConsumers: VertexConsumerProvider,
        entity: LivingEntity, stack: ItemStack, light: Int,
    ) {
        if (stack.item is PackItem) {
            matrices.push()
            matrices.scale(0.6f, 0.6f, 0.6f)

            matrices.multiply(Axis.X_POSITIVE.rotationDegrees(180f))
            matrices.translate(0.0, -0.6, -0.25)

            if (entity.isInSneakingPose) {
                matrices.multiply(Axis.X_POSITIVE.rotationDegrees(28f)) // 28 seams the right one
                matrices.translate(0.0, -0.25, -0.16) //-0.16 real value
            }

            renderer.renderItem(
                stack, ModelTransformationMode.FIXED, light, OverlayTexture.DEFAULT_UV,
                matrices, vertexConsumers, entity.world, 0
            )

            matrices.pop()
        }
    }
}