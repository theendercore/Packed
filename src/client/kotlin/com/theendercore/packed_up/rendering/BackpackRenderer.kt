package com.theendercore.packed_up.rendering

import com.theendercore.packed_up.util.isBackpack
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.model.json.ModelTransformationMode
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.Axis


@Suppress("MagicNumber")
object BackpackRenderer {
    private val renderer = MinecraftClient.getInstance().itemRenderer

    fun renderBackpack(
        matrices: MatrixStack, vertexConsumers: VertexConsumerProvider,
        entity: LivingEntity, stack: ItemStack, light: Int,
    ) {
        if (stack.isBackpack()) {
            //change all of this to be a func u can call

            matrices.push()
            matrices.scale(0.6f, 0.6f, 0.6f)

            matrices.rotate(Axis.X_POSITIVE.rotationDegrees(180f))
            matrices.translate(0.0, -0.6, -0.25)

            if (entity.isInSneakingPose) {
                matrices.rotate(Axis.X_POSITIVE.rotationDegrees(28f)) // 28 seams the right one
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
