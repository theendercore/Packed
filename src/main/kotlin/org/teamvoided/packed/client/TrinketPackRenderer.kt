package org.teamvoided.packed.client

import dev.emi.trinkets.api.SlotReference
import dev.emi.trinkets.api.client.TrinketRenderer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.model.EntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack


class TrinketPackRenderer : TrinketRenderer {
    override fun render(
        stack: ItemStack, slotReference: SlotReference, contextModel: EntityModel<out LivingEntity>,
        matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int,
        entity: LivingEntity, limbAngle: Float, limbDistance: Float, tickDelta: Float,
        animationProgress: Float, headYaw: Float, headPitch: Float,
    ) {
        PackRenderer.render(matrices, vertexConsumers, entity, stack, light)
    }

}