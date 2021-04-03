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
package com.github.jonathanxd.guihelper.factory;

import com.github.jonathanxd.guihelper.data.SlotData;
import com.github.jonathanxd.guihelper.gui.GUI;
import com.github.jonathanxd.guihelper.gui.Input;
import com.github.jonathanxd.guihelper.gui.Page;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

public class AnvilFactory implements Factory {

    public static AnvilFactory INSTANCE = new AnvilFactory();

    private AnvilFactory() {
    }

    @Override
    public Input create(GUI gui, Player player, int pageNumber) {
        Inventory inventory = Bukkit.createInventory(player, InventoryType.ANVIL, gui.getTitle());

        Page page = gui.getPages().get(pageNumber);

        List<SlotData> slotDatas = page.getSlotDatas();

        for (int i = 0; i < slotDatas.size(); i++) {
            Optional<ItemStack> itemStack = slotDatas.get(i).getItemStack();

            if(itemStack.isPresent()) {
                inventory.setItem(i, itemStack.get());
            }
        }

        return new Input.InventoryInput(inventory);
    }
}
