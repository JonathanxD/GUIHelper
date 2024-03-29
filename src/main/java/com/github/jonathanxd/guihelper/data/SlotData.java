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
package com.github.jonathanxd.guihelper.data;

import com.github.jonathanxd.guihelper.handler.ClickHandler;
import com.github.jonathanxd.guihelper.handler.SubHandler;
import com.github.jonathanxd.guihelper.util.Constants;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * Data about slot.
 */
public final class SlotData {
    private ItemStack itemStack;
    private final UnaryOperator<ItemStack> updater;
    private final SubHandler handler;

    public SlotData(@NotNull ItemStack itemStack, @NotNull SubHandler handler, @Nullable UnaryOperator<ItemStack> updater) {
        this.itemStack = itemStack;
        this.handler = handler;
        this.updater = updater;
    }

    public SlotData(@NotNull ItemStack itemStack, @NotNull ClickHandler handler, @Nullable UnaryOperator<ItemStack> updater) {
        this.itemStack = itemStack;
        this.handler = SubHandler.DEFAULT(handler);
        this.updater = updater;
    }

    public SlotData(@NotNull ItemStack itemStack, @NotNull SubHandler handler) {
        this(itemStack, handler, null);
    }

    public SlotData(@NotNull ItemStack itemStack, @NotNull ClickHandler handler) {
        this(itemStack, handler, null);

    }

    public Optional<ItemStack> getItemStack() {
        return Optional.ofNullable(this.itemStack);
    }

    public SubHandler getHandler() {
        return handler;
    }

    public boolean canUpdate() {
        return this.getUpdater().isPresent();
    }

    public Optional<UnaryOperator<ItemStack>> getUpdater() {
        return Optional.ofNullable(this.updater);
    }

    public ItemStack update() {
        this.itemStack = this.getUpdater().orElse(UnaryOperator.identity()).apply(this.itemStack);
        return this.itemStack;
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
