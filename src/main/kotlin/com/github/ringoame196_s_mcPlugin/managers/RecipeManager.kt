package com.github.ringoame196_s_mcPlugin.managers

import com.github.ringoame196_s_mcPlugin.RecipeItemData
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe
import kotlin.random.Random

object RecipeManager {
    fun acquisitionRecipeItem(item: ItemStack): RecipeItemData? {
        val itemType = item.type
        val amount = item.amount
        val recipes = acquisitionRecipes(itemType)
        if (recipes.isEmpty()) return null
        val recipe = recipes[Random.nextInt(0, recipes.size)]
        val requiredCount = recipe.result.amount

        // 必要なアイテム数がない場合
        if (amount < requiredCount) {
            return RecipeItemData(item, mutableListOf(), 0)
        }

        val recipeItem = when (recipe) { // レシピ内容
            is ShapedRecipe -> acquisitionRecipeItem(recipe)
            is ShapelessRecipe -> acquisitionRecipeItem(recipe)
            else -> mutableListOf()
        }
        val multiple = amount / requiredCount
        item.amount = amount % requiredCount
        return RecipeItemData(item, recipeItem, multiple)
    }

    private fun acquisitionRecipes(itemMaterial: Material): List<Recipe> {
        val recipes = Bukkit.recipeIterator()
        val matchingRecipes = mutableListOf<Recipe>()

        // レシピイテレータを繰り返し処理
        while (recipes.hasNext()) {
            val recipe = recipes.next()
            // レシピの結果が指定されたアイテムか確認
            if (recipe.result.type == itemMaterial && (recipe is ShapedRecipe || recipe is ShapelessRecipe)) {
                matchingRecipes.add(recipe) // 一致するレシピをリストに追加
            }
        }
        return matchingRecipes // 一致したレシピのリストを返す
    }

    private fun acquisitionRecipeItem(recipe: ShapedRecipe): List<ItemStack> {
        val recipeItemList = mutableListOf<ItemStack>()
        val ingredientMap = recipe.ingredientMap
        ingredientMap.forEach { (key, ingredient) ->
            val recipeItem = ingredient ?: ItemStack(Material.AIR)
            recipeItemList.add(recipeItem)
        }
        return recipeItemList
    }

    private fun acquisitionRecipeItem(recipe: ShapelessRecipe): List<ItemStack> {
        val recipeItemList = mutableListOf<ItemStack>()
        recipe.ingredientList.forEach { ingredient ->
            val recipeItem = ingredient ?: ItemStack(Material.AIR)
            recipeItemList.add(recipeItem)
        }
        return recipeItemList
    }
}
