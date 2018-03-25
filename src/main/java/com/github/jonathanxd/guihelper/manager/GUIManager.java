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
package com.github.jonathanxd.guihelper.manager;

import com.github.jonathanxd.guihelper.gui.GUI;
import com.github.jonathanxd.guihelper.gui.ViewSection;
import com.github.jonathanxd.guihelper.view.ResultReceiver;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by jonathan on 22/07/16.
 */
public interface GUIManager {

    /**
     * Register a GUI.
     *
     * @param gui GUI to register.
     */
    void registerGui(GUI gui);

    /**
     * Unregister a GUI
     *
     * @param gui GUI to Unregister
     */
    void unregisterGui(GUI gui);

    /**
     * Get a registered GUI.
     *
     * @param predicate Predicate
     * @return registered GUI.
     */
    Optional<GUI> getGui(Predicate<? super GUI> predicate);

    /**
     * Open a GUI
     *
     * @param gui            GUI
     * @param page           Page
     * @param player         Player
     * @param resultReceiver Receiver of result
     */
    void openGui(GUI gui, int page, Player player, ResultReceiver resultReceiver);

    default void openGui(GUI gui, Player player) {
        this.openGui(gui, 0, player);
    }

    default void openGui(GUI gui, int page, Player player) {
        this.openGui(gui, page, player, ResultReceiver.EMPTY);
    }

    default void openGui(GUI gui, Player player, ResultReceiver resultReceiver) {
        this.openGui(gui, 0, player, resultReceiver);
    }

    void openParentGui(ViewSection current, GUI parent, int page, Player player, ResultReceiver resultReceiver);

    default void openParentGui(ViewSection current, GUI parent, Player player) {
        this.openParentGui(current, parent, 0, player);
    }

    default void openParentGui(ViewSection current, GUI parent, int page, Player player) {
        this.openParentGui(current, parent, page, player, ResultReceiver.EMPTY);
    }

    default void openParentGui(ViewSection current, GUI parent, Player player, ResultReceiver resultReceiver) {
        this.openParentGui(current, parent, 0, player, resultReceiver);
    }

    void openView(Player player, ViewSection viewSection);


    Optional<ViewSection> getCurrentView(Player player);

    boolean exitCurrentView(Player player);

    default boolean closeCurrentView(Player player) {
        return this.closeCurrentView(player, true);
    }

    boolean closeCurrentView(Player player, boolean openParent);


}
