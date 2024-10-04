package com.theendercore.packed_up.rendering

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.model.BipedEntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack


class ArmorBackpackRenderer : ArmorRenderer {
    override fun render(
        matrices: MatrixStack, vertexConsumers: VertexConsumerProvider,
        stack: ItemStack, entity: LivingEntity, slot: EquipmentSlot,
        light: Int, contextModel: BipedEntityModel<LivingEntity>,
    ) {
//        if (trinketsInstalled && Trinkets.shouldRenderTrinket(entity)) return
        BackpackRenderer.renderBackpack(matrices, vertexConsumers, entity, stack, light)
    }
}
