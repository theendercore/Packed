package org.teamvoided.templatemod.api

import org.teamvoided.templatemod.iInv

interface HasInventory {
    fun getInventory(): iInv
    fun setInventory(inv: iInv)
    fun genDefault():iInv
}