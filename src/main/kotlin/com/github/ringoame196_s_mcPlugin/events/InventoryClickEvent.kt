package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.managers.RecipeGUIManager
import com.github.ringoame196_s_mcPlugin.managers.RecipeManager
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

class InventoryClickEvent : Listener {
    private val recipeGUIManager = RecipeGUIManager()

    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        val player = e.whoClicked as? Player? ?: return
        val gui = e.view
        val guiTitle = gui.title
        val clickInventory = e.clickedInventory
        val slot = e.slot

        if (guiTitle != recipeGUIManager.guiTitle) return
        if (clickInventory == player.inventory) return // プレイヤーのインベントリでキャンセル発生を阻止

        when (slot) {
            recipeGUIManager.inItemSlot -> {} // アイテムが置いたりできるように
            recipeGUIManager.clickItemSlot -> {
                e.isCancelled = true
                recipeCraft(gui, player)
            }
            else -> e.isCancelled = true
        }
    }

    private fun recipeCraft(gui: InventoryView, player: Player) {
        val inItemSlot = recipeGUIManager.inItemSlot
        val selectItem = gui.getItem(inItemSlot)
        val selectItemType = selectItem?.type
        val selectItemAmount = selectItem?.amount ?: 0
        val recipeItemList = RecipeManager.acquisitionRecipeItem(selectItemType ?: Material.AIR)

        if (recipeItemList.isEmpty()) {
            val sound = Sound.BLOCK_NOTE_BLOCK_BELL
            player.playSound(player, sound, 1f, 1f)
        } else {
            for (item in recipeItemList) {
                val amount = item.amount * selectItemAmount
                item.amount = amount
                player.inventory.addItem(item)
            }
            val sound = Sound.BLOCK_ANVIL_USE
            player.playSound(player, sound, 1f, 1f)
            gui.setItem(inItemSlot, ItemStack(Material.AIR))
        }
    }
}
