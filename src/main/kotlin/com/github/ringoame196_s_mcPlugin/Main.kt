package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.events.InventoryClickEvent
import com.github.ringoame196_s_mcPlugin.events.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private val plugin = this
    override fun onEnable() {
        super.onEnable()
        server.pluginManager.registerEvents(PlayerInteractEvent(), plugin)
        server.pluginManager.registerEvents(InventoryClickEvent(), plugin)
    }
}
