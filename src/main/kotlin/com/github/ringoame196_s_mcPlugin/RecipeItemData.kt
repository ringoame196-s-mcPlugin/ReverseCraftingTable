package com.github.ringoame196_s_mcPlugin

import org.bukkit.inventory.ItemStack

data class RecipeItemData(
    val inItem: ItemStack,
    val recipeItemList: List<ItemStack>,
    val multiple: Int
)
