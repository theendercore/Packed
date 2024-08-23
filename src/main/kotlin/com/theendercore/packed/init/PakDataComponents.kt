package com.theendercore.packed.init

import com.theendercore.packed.Packed.id
import com.theendercore.packed.component.BackpackContentsComponent
import com.theendercore.packed.util.register
import net.minecraft.component.DataComponentType
import net.minecraft.registry.Registries
import java.util.function.UnaryOperator

object PakDataComponents {
    fun init() = Unit
    val BACKPACK_CONTENTS = register("backpack_contents_v1a") {
        it.codec(BackpackContentsComponent.CODEC).packetCodec(BackpackContentsComponent.PACKET_CODEC).method_59871()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> register(id: String, operator: UnaryOperator<DataComponentType.Builder<T>>): DataComponentType<T> {
        return Registries.DATA_COMPONENT_TYPE.register(
            id(id),
            operator.apply(DataComponentType.builder()).build()
        ) as DataComponentType<T>
    }
}
