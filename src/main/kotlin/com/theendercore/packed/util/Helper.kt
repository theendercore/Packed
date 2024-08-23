@file:Suppress("unused")

package com.theendercore.packed.util

import com.theendercore.packed.component.BackpackContentsComponent
import com.theendercore.packed.init.PakDataComponents
import net.minecraft.component.DataComponentTypes.DYED_COLOR
import net.minecraft.component.type.DyedColorComponent
import net.minecraft.item.Item
import net.minecraft.item.Item.Settings
import net.minecraft.item.ItemStack
import net.minecraft.registry.Holder
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.tag.TagKey
import net.minecraft.screen.ScreenHandlerFactory
import net.minecraft.screen.SimpleNamedScreenHandlerFactory
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList

fun <T> Registry<T>.register(id: Identifier, entry: T): T = Registry.register(this, id, entry)
fun <T> Registry<T>.registerHolder(id: Identifier, entry: T): Holder<T> = Registry.registerHolder(this, id, entry)

fun ItemStack.strictMatch(stack: ItemStack): Boolean = ItemStack.itemsAndComponentsMatch(this, stack)
fun ItemStack.loseMatch(stack: ItemStack): Boolean = ItemStack.itemsMatch(this, stack)

fun <T> RegistryKey<Registry<T>>.tag(id: Identifier) = TagKey.of(this, id)


fun ItemStack.charAt(id: Int) = this.item.id.path[id].code
val Item.id get() = Registries.ITEM.getId(this)


fun defaultedList(size:Int): DefaultedList<ItemStack> = DefaultedList.ofSize(size, ItemStack.EMPTY)
fun NamedScreenMaker(name: Text, screenMaker: ScreenHandlerFactory) = SimpleNamedScreenHandlerFactory(screenMaker, name)

// Component Helpers
fun Settings.dyeColor(color: Int, showInToolTip: Boolean = false): Settings =
    this.component(DYED_COLOR, DyedColorComponent(color, showInToolTip))

fun Settings.backpack(size: Int = 9): Settings =
    this.component(PakDataComponents.BACKPACK_CONTENTS, BackpackContentsComponent(size))


fun ItemStack.isBackpack() = this.get(PakDataComponents.BACKPACK_CONTENTS) != null
fun ItemStack.getBackpackContents(): BackpackContentsComponent? = this.get(PakDataComponents.BACKPACK_CONTENTS)
fun ItemStack.setBackpackContents(stacks: DefaultedList<ItemStack>) =
    this.set(PakDataComponents.BACKPACK_CONTENTS, BackpackContentsComponent(stacks))
