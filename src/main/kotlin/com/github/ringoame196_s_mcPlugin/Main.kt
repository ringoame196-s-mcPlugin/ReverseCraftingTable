package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.events.RecipeGUIEvents
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private val plugin = this
    override fun onEnable() {
        super.onEnable()
        server.pluginManager.registerEvents(RecipeGUIEvents(), plugin)
    }
}
