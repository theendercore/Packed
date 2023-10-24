package org.teamvoided.templatemod.api

import org.teamvoided.templatemod.items.InvImpl

interface HasInventory {
    fun getInventory(): InvImpl
    fun setInventory(inv: InvImpl)
    fun genDefault(): InvImpl
}