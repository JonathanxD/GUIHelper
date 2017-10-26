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
package com.github.jonathanxd.guihelper.data;

import com.github.jonathanxd.guihelper.handler.ClickHandler;
import com.github.jonathanxd.guihelper.handler.SubHandler;
import com.github.jonathanxd.guihelper.util.Constants;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * Data about slot.
 */
public final class SlotData {
    private final ItemStack itemStack;
    private final SubHandler handler;

    public SlotData(ItemStack itemStack, SubHandler handler) {
        this.itemStack = itemStack;
        this.handler = handler;
    }

    public SlotData(ItemStack itemStack, ClickHandler handler) {
        this.itemStack = itemStack;
        this.handler = SubHandler.DEFAULT(handler);
    }

    public Optional<ItemStack> getItemStack() {
        return Optional.ofNullable(this.itemStack);
    }

    public SubHandler getHandler() {
        return handler;
    }

    @Override
    public String toString() {
        if (this == Constants.UNFILLED_SLOT_DATA)
            return "SlotData[unfilled]";

        if (this == Constants.EMPTY_SLOT_DATA)
            return "SlotData[empty]";

        return "SlotData[itemStack=" + this.toString(itemStack) + "]";
    }

    private String toString(ItemStack itemStack) {
        if (Bukkit.getServer() == null)
            return itemStack.getType() + "x" + itemStack.getAmount();

        return itemStack.toString();
    }
}
