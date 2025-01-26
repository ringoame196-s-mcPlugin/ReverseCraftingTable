package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.managers.RecipeGUIManager
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class PlayerInteractEvent : Listener {
    private val recipeGUIManager = RecipeGUIManager()

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
}
