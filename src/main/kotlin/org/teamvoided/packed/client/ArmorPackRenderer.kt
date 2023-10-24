package org.teamvoided.packed.client

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.model.BipedEntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import org.teamvoided.packed.Packed.trinketsInstalled
import org.teamvoided.packed.compat.Trinkets


class ArmorPackRenderer : ArmorRenderer {
    override fun render(
        matrices: MatrixStack, vertexConsumers: VertexConsumerProvider,
        stack: ItemStack, entity: LivingEntity, slot: EquipmentSlot,
        light: Int, contextModel: BipedEntityModel<LivingEntity>,
    ) {
        if (trinketsInstalled) if (Trinkets.shouldRenderTrinket(entity)) return
        PackRenderer.render(matrices, vertexConsumers, entity, stack, light)
    }
}