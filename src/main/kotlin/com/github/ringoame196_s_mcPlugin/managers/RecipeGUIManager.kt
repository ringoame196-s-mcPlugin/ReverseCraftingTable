package com.github.ringoame196_s_mcPlugin.managers

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class RecipeGUIManager {
    private lateinit var sideItem: ItemStack
    private lateinit var clickItem: ItemStack

    val guiTitle = "逆作業台"
    val inItemSlot = 4
    val clickItemSlot = 5

    init {
        sideItem = makeSideItem()
        clickItem = makeClickItem()
    }

    fun makeGUI(): Inventory {
        val guiSize = 9
        val gui = Bukkit.createInventory(null, guiSize, guiTitle)

        for (i in 0..gui.size - 1) {
            gui.setItem(i, sideItem)
        }
        gui.setItem(inItemSlot, ItemStack(Material.AIR))
        gui.setItem(clickItemSlot, clickItem)

        return gui
    }

    private fun makeSideItem(): ItemStack {
        val item = ItemStack(Material.WHITE_STAINED_GLASS_PANE)
        val itemName = ""
        val meta = item.itemMeta
        meta?.setDisplayName(itemName)
        item.setItemMeta(meta)
        return item
    }

    private fun makeClickItem(): ItemStack {
        val item = ItemStack(Material.CHEST)
        val itemName = "${ChatColor.GOLD}逆クラフト"
        val meta = item.itemMeta
        meta?.setDisplayName(itemName)
        item.setItemMeta(meta)
        return item
    }
}
