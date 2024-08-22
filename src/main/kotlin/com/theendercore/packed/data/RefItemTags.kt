package com.theendercore.packed.data

import com.theendercore.packed.Packed.id
import com.theendercore.packed.data.PakItemTags.itemTag

object RefItemTags {

    val CHEST_BACK = trinkets("chest/back")

    fun trinkets(id: String) = itemTag(id("trinkets", id))
}
