package com.theendercore.packed.data.gen

import com.theendercore.packed.data.gen.tags.ItemTagProvider
import com.theendercore.packed.init.PakItems
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.model.BlockStateModelGenerator
import net.minecraft.data.client.model.Models
import net.minecraft.item.Items
import net.minecraft.registry.HolderLookup
import java.util.concurrent.CompletableFuture

@Suppress("unused")
object PackedData : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
        val pack = gen.createPack()

        pack.addProvider(::ModelProvider)
        pack.addProvider(::EnLangProvider)

        val blockTags = pack.addProvider(::BlockTagProvider)
        pack.addProvider { o, r -> ItemTagProvider(o, r, blockTags) }
    }

    class ModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {
        override fun generateBlockStateModels(gen: BlockStateModelGenerator) = Unit
        override fun generateItemModels(gen: ItemModelGenerator) {
            gen.register(PakItems.PACK, Items.APPLE, Models.SINGLE_LAYER_ITEM)
        }
    }

    class BlockTagProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) :
        FabricTagProvider.BlockTagProvider(o, r) {
        override fun configure(lookup: HolderLookup.Provider) = Unit
    }

    class EnLangProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) :
        FabricLanguageProvider(o, r) {
        override fun generateTranslations(lookup: HolderLookup.Provider, gen: TranslationBuilder) {
            gen.add("item.packed.pack", "Pack")
        }
    }
}