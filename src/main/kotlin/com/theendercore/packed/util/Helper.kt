@file:Suppress("unused")

package com.theendercore.packed.util

import net.minecraft.component.DataComponentTypes.DYED_COLOR
import net.minecraft.component.type.DyedColorComponent
import net.minecraft.item.Item.Settings
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier

fun ItemStack.strictMatch(stack: ItemStack): Boolean = ItemStack.itemsAndComponentsMatch(this, stack)
fun ItemStack.loseMatch(stack: ItemStack): Boolean = ItemStack.itemsMatch(this, stack)

fun <T> RegistryKey<Registry<T>>.tag(id: Identifier) = TagKey.of(this, id)


fun Settings.dyeColor(color: Int, showInToolTip: Boolean = false): Settings =
    this.component(DYED_COLOR, DyedColorComponent(color, showInToolTip))
