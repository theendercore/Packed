package com.theendercore.packed.data.gen.tags

import com.theendercore.packed.init.PakItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.tag.ItemTags
import java.util.concurrent.CompletableFuture

class ItemTagProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>, tags: BlockTagProvider) :
    FabricTagProvider.ItemTagProvider(o, r, tags) {
    override fun configure(lookup: HolderLookup.Provider) {
        getOrCreateTagBuilder(ItemTags.DYEABLE)
            .add(PakItems.PACK)
    }
}