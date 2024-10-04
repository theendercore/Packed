package com.theendercore.packed_up.data

import com.theendercore.packed_up.PackedUp.id
import com.theendercore.packed_up.data.PakItemTags.itemTag

object RefItemTags {

    val CHEST_BACK = trinkets("chest/back")

    fun trinkets(id: String) = itemTag(id("trinkets", id))
}
