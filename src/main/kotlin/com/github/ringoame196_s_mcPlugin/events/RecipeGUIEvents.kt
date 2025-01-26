package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.managers.RecipeGUIManager
import com.github.ringoame196_s_mcPlugin.managers.RecipeManager
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.InventoryView

class RecipeGUIEvents : Listener {
    private val recipeGUIManager = RecipeGUIManager()
    private val inSlot = recipeGUIManager.inItemSlot

    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        val player = e.player
        val block = e.clickedBlock ?: return
        val sneak = player.isSneaking

        if (!sneak) return
        if (e.action != Action.RIGHT_CLICK_BLOCK) return
        if (block.type != Material.CRAFTING_TABLE) return
        val gui = recipeGUIManager.makeGUI()
        e.isCancelled = true
        player.openInventory(gui)
    }

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
        val recipeItemData = RecipeManager.acquisitionRecipeItem(selectItem ?: return)
        val recipeItemList = recipeItemData?.recipeItemList
        val multiple = recipeItemData?.multiple ?: 0
        val inItem = recipeItemData?.inItem

        if (recipeItemList == null || recipeItemList.isEmpty()) {
            val sound = Sound.BLOCK_NOTE_BLOCK_BELL
            player.playSound(player, sound, 1f, 1f)
        } else {
            for (item in recipeItemList) {
                val amount = item.amount * multiple
                item.amount = amount
                player.inventory.addItem(item)
            }
            val sound = Sound.BLOCK_ANVIL_USE
            player.playSound(player, sound, 1f, 1f)
            gui.setItem(inItemSlot, inItem)
        }
    }

    @EventHandler
    fun onInventoryClose(e: InventoryCloseEvent) {
        val player = e.player
        val gui = e.view
        val guiTitle = gui.title

        if (guiTitle != recipeGUIManager.guiTitle) return
        val item = gui.getItem(inSlot) ?: return
        player.inventory.addItem(item)
    }
}
