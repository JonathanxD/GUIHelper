/*
 *      GUIHelper - Simple and interactive GUI constructor and manager. <https://github.com/JonathanxD/GUIHelper>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2017 JonathanxD <jonathan.scripter@programmer.net>
 *      Copyright (c) contributors
 *
 *
 *      Permission is hereby granted, free of charge, to any person obtaining a copy
 *      of this software and associated documentation files (the "Software"), to deal
 *      in the Software without restriction, including without limitation the rights
 *      to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *      copies of the Software, and to permit persons to whom the Software is
 *      furnished to do so, subject to the following conditions:
 *
 *      The above copyright notice and this permission notice shall be included in
 *      all copies or substantial portions of the Software.
 *
 *      THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *      IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *      FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *      AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *      LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *      OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *      THE SOFTWARE.
 */
package com.github.jonathanxd.guihelper.listener;

import com.github.jonathanxd.guihelper.gui.Input;
import com.github.jonathanxd.guihelper.gui.ViewSection;
import com.github.jonathanxd.guihelper.manager.GUIManager;
import com.github.jonathanxd.guihelper.util.ItemHelper;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Optional;

/**
 * Created by jonathan on 22/07/16.
 */
public class GUIListener implements Listener {

    private final GUIManager guiManager;

    public GUIListener(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        Optional<ViewSection> currentViewOpt = this.guiManager.getCurrentView(player);

        if(currentViewOpt.isPresent()) {
            ViewSection viewSection = currentViewOpt.get();

            if(viewSection.input instanceof Input.TextInput) {

                viewSection.handleClick(player, 0, ItemHelper.stack(Material.BLAZE_ROD, event.getMessage()));


                event.setMessage("");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void playerDisconnect(PlayerQuitEvent event) {
        this.guiManager.exitCurrentView(event.getPlayer());
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        HumanEntity whoClicked = event.getWhoClicked();

        if (whoClicked instanceof Player) {
            Player player = (Player) whoClicked;
            int slot = event.getSlot();

            Optional<ViewSection> currentView = this.guiManager.getCurrentView(player);

            if (currentView.isPresent()) {

                ViewSection section = currentView.get();

                if(section.input instanceof Input.InventoryInput) {
                    Input.InventoryInput input = (Input.InventoryInput) section.input;

                    if (slot > -1 && slot < inventory.getSize()) {
                        ItemStack currentItem = event.getCurrentItem();

                        if (inventory.equals(input.getInventory())) {
                            section.handleClick(player, slot, currentItem);
                        }
                    }
                }

                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @EventHandler
    public void close(InventoryCloseEvent event) {
        HumanEntity player = event.getPlayer();

        if (player instanceof Player) {
            guiManager.closeCurrentView((Player) player);
        }
    }

}
