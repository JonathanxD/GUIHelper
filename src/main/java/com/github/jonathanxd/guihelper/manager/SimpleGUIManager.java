/*
 *      GUIHelper - Simple and interactive GUI constructor and manager. <https://github.com/JonathanxD/GUIHelper>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2021 JonathanxD <jhrldev@gmail.com>
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
package com.github.jonathanxd.guihelper.manager;

import com.github.jonathanxd.guihelper.gui.GUI;
import com.github.jonathanxd.guihelper.gui.Input;
import com.github.jonathanxd.guihelper.gui.InputView;
import com.github.jonathanxd.guihelper.gui.ViewSection;
import com.github.jonathanxd.guihelper.view.ResultReceiver;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by jonathan on 22/07/16.
 */
public class SimpleGUIManager implements GUIManager {

    private final Plugin plugin;
    private final Set<GUI> registeredGuis = new HashSet<>();
    private final Map<Player, ViewSection> playerViewSection = new HashMap<>();

    public SimpleGUIManager(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void registerGui(GUI gui) {
        this.registeredGuis.add(gui);
    }

    @Override
    public void unregisterGui(GUI gui) {
        this.registeredGuis.remove(gui);
    }

    @Override
    public Optional<GUI> getGui(Predicate<? super GUI> predicate) {
        return registeredGuis.stream().filter(predicate).findAny();
    }

    /**
     * Close current GUI and open ANOTHER Gui
     *
     * @param gui            GUI
     * @param page           Page Number
     * @param player         Player
     * @param resultReceiver Result Receiver
     */
    @Override
    public void openGui(GUI gui, int page, Player player, ResultReceiver resultReceiver) {
        this.exitCurrentView(player);

        player.closeInventory();

        Input input = gui.getFactory().create(gui, player, page);

        InputView inputView = input.getInventoryOpenHandler().apply(player);

        ViewSection viewSection = new ViewSection(gui, this, input, inputView, page, null, resultReceiver);

        this.playerViewSection.put(player, viewSection);
    }

    @Override
    public void openParentGui(ViewSection current, GUI gui, int page, Player player, ResultReceiver resultReceiver) {
        this.exitCurrentView(player);

        player.closeInventory();

        Input input = gui.getFactory().create(gui, player, page);

        InputView inputView = input.getInventoryOpenHandler().apply(player);

        ViewSection viewSection = new ViewSection(gui, this, input, inputView, page, current, resultReceiver);

        this.playerViewSection.put(player, viewSection);
    }

    @Override
    public void openView(Player player, ViewSection viewSection) {
        this.exitCurrentView(player);

        player.closeInventory();

        Input input = viewSection.gui.getFactory().create(viewSection.gui, player, viewSection.currentPage);

        InputView inputView = input.getInventoryOpenHandler().apply(player);

        ViewSection updatedViewSection = viewSection.copy(inputView, input);

        this.playerViewSection.put(player, updatedViewSection);
    }

    @Override
    public Optional<ViewSection> getCurrentView(Player player) {

        return Optional.ofNullable(this.playerViewSection.get(player));
    }

    @Override
    public boolean exitCurrentView(Player player) {
        return this.playerViewSection.remove(player) != null;
    }

    @Override
    public boolean closeCurrentView(Player player, boolean openParent) {
        if (!this.playerViewSection.containsKey(player))
            return false;

        Optional<ViewSection> currentView = this.getCurrentView(player);

        boolean exit = this.exitCurrentView(player);

        if (currentView.isPresent()) {
            ViewSection viewSection = currentView.get();

            if (viewSection.input instanceof Input.InventoryInput) {
                player.closeInventory();
            }

            viewSection.consumeResult(player);

            if (viewSection.parent != null && openParent) {
                Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                    if (!this.getCurrentView(player).isPresent())
                        this.openView(player, viewSection.parent);
                }, 10L);
            } else {
                return exit;
            }
        } else {
            return exit;
        }

        return true;
    }

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }
}
