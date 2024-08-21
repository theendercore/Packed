package com.theendercore.packed.util

import net.minecraft.component.DataComponentTypes.DYED_COLOR
import net.minecraft.component.type.DyedColorComponent
import net.minecraft.item.Item.Settings
import net.minecraft.item.ItemStack

fun ItemStack.strictMatch(stack: ItemStack): Boolean = ItemStack.itemsAndComponentsMatch(this, stack)
fun ItemStack.loseMatch(stack: ItemStack): Boolean = ItemStack.itemsMatch(this, stack)


fun Settings.dyeColor(color: Int, showInToolTip: Boolean = false): Settings = this.component(DYED_COLOR, DyedColorComponent(color, showInToolTip))
