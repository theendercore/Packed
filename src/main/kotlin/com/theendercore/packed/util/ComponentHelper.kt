package com.theendercore.packed.util

import com.theendercore.packed.component.BackpackContentsComponent
import com.theendercore.packed.init.PakDataComponents
import net.minecraft.component.DataComponentTypes.DYED_COLOR
import net.minecraft.component.type.DyedColorComponent
import net.minecraft.item.Item.Settings
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

fun Settings.dyeColor(color: Int = WHITE, showInToolTip: Boolean = false): Settings =
    this.component(DYED_COLOR, DyedColorComponent(color, showInToolTip))

fun Settings.backpack(size: Int = 9): Settings =
    this.component(PakDataComponents.BACKPACK_CONTENTS, BackpackContentsComponent(size))


fun ItemStack.isBackpack() = this.get(PakDataComponents.BACKPACK_CONTENTS) != null
fun ItemStack.getBackpackContents(): BackpackContentsComponent? = this.get(PakDataComponents.BACKPACK_CONTENTS)
fun ItemStack.setBackpackContents(stacks: DefaultedList<ItemStack>) {
    this.set(PakDataComponents.BACKPACK_CONTENTS, BackpackContentsComponent(stacks))
}

