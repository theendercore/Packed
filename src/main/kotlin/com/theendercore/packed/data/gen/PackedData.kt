package com.theendercore.packed.data.gen

import com.theendercore.packed.init.PakItems
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.model.BlockStateModelGenerator
import net.minecraft.data.client.model.Models
import net.minecraft.item.Items

@Suppress("unused")
object PackedData : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
        val pack = gen.createPack()
        pack.addProvider(::ModelProvider)
    }

    class ModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {
        override fun generateBlockStateModels(gen: BlockStateModelGenerator) = Unit
        override fun generateItemModels(gen: ItemModelGenerator) {
            gen.register(PakItems.PACK, Items.APPLE, Models.SINGLE_LAYER_ITEM)
        }
    }
}