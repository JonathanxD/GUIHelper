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
package com.github.jonathanxd.guihelper.gui;

import com.github.jonathanxd.guihelper.data.SlotData;
import com.github.jonathanxd.guihelper.factory.Factory;
import com.github.jonathanxd.guihelper.factory.InventoryFactory;
import com.github.jonathanxd.guihelper.handler.ClickHandler;
import com.github.jonathanxd.guihelper.handler.Handler;
import com.github.jonathanxd.guihelper.util.Constants;
import com.github.jonathanxd.guihelper.util.ListUtil;
import com.github.jonathanxd.guihelper.util.PageUtil;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * GUI class
 */
public class GUI {

    private final GUIType guiType;
    private final Pages pages;
    private final String title;
    private final Factory factory;

    public GUI(GUIType guiType, String title, Pages pages, Factory factory) {
        this.guiType = guiType;
        this.title = title;
        this.pages = pages;
        this.factory = factory;
    }

    public static GUI.Builder create(String title) {
        return GUI.create(GUITypes.view(), title);
    }

    public static GUI.Builder selection(String title) {
        return GUI.create(GUITypes.view(), title);
    }

    public static GUI.Builder create(GUIType guiType, String title) {
        return new GUI.Builder(guiType, title);
    }

    /**
     * @deprecated Not Working
     */
    @Deprecated
    public static GUI createAnvil(GUIType guiType, String title, Consumer<String> resultConsumer) {
        return Constants.createAnvilGui(guiType, title, resultConsumer);
    }

    /**
     * @deprecated Not Working
     */
    @Deprecated
    public static GUI createAnvil(String title, Consumer<String> resultConsumer) {
        return GUI.createAnvil(GUITypes.view(), title, resultConsumer);
    }

    public static GUI createTextInput(String title, Consumer<String> resultConsumer) {
        return Constants.createTextInput(GUITypes.view(), title, resultConsumer);
    }

    public static GUI createTextInput(String title, Predicate<String> resultPredicate, Consumer<String> consumer) {
        return Constants.createTextInput(GUITypes.view(), title, resultPredicate, consumer);
    }

    public GUIType getGuiType() {
        return this.guiType;
    }

    public String getTitle() {
        return this.title;
    }

    public Pages getPages() {
        return this.pages;
    }

    public Factory getFactory() {
        return this.factory;
    }

    public static class Builder {

        private final GUIType guiType;
        private final String title;
        private final List<SlotData> slots = new ArrayList<>();

        private Builder(GUIType guiType, String title) {
            this.guiType = guiType;
            this.title = title;
        }

        public int getEmptySlot() {
            for (int i = 0; i < this.slots.size(); i++) {
                SlotData aSlot = this.slots.get(i);

                if (aSlot == Constants.UNFILLED_SLOT_DATA) {
                    return i;
                }
            }

            return this.slots.size();
        }

        public GUI.Builder addSlotData(SlotData slotData) {
            if (!this.fillUnfilledSlots(slotData)) {
                this.slots.add(slotData);
            }


            this.checkSize();

            return this;
        }

        public GUI.Builder addItem(ItemStack itemStack, Handler clickHandler) {
            return this.addItem(itemStack, clickHandler, null);
        }

        public GUI.Builder addItem(ItemStack itemStack, ClickHandler clickHandler) {
            return this.addItem(itemStack, clickHandler, null);
        }

        public GUI.Builder addItem(ItemStack itemStack, Handler clickHandler, UnaryOperator<ItemStack> updater) {
            return this.addSlotData(new SlotData(itemStack, guiType.createSubHandler(ClickHandler.fromLegacy(clickHandler)), updater));
        }

        public GUI.Builder addItem(ItemStack itemStack, ClickHandler clickHandler, UnaryOperator<ItemStack> updater) {
            return this.addSlotData(new SlotData(itemStack, guiType.createSubHandler(clickHandler), updater));
        }

        private boolean fillUnfilledSlots(SlotData slotData) {
            for (int i = 0; i < this.slots.size(); i++) {
                SlotData aSlot = this.slots.get(i);

                if (aSlot == Constants.UNFILLED_SLOT_DATA) {
                    this.slots.set(i, slotData);
                    return true;
                }
            }

            return false;
        }

        public GUI.Builder addSlotData(int slot, SlotData slotData) {
            if (slot >= this.slots.size()) {
                this.createSlot(slot);
            }

            if (this.slots.get(slot) == Constants.UNFILLED_SLOT_DATA)
                this.slots.set(slot, slotData);
            else
                this.slots.add(slot, slotData);

            this.checkSize();

            return this;
        }

        public GUI.Builder addItem(int slot, @NotNull ItemStack itemStack, @NotNull Handler clickHandler) {
            return this.addItem(slot, itemStack, clickHandler, null);
        }

        public GUI.Builder addItem(int slot, @NotNull ItemStack itemStack, @NotNull ClickHandler clickHandler) {
            return this.addItem(slot, itemStack, clickHandler, null);
        }

        public GUI.Builder addItem(int slot, @NotNull ItemStack itemStack, @NotNull Handler clickHandler,
                                   @Nullable UnaryOperator<ItemStack> updater) {
            return this.addSlotData(slot, new SlotData(itemStack, guiType.createSubHandler(ClickHandler.fromLegacy(clickHandler)), updater));
        }

        public GUI.Builder addItem(int slot, @NotNull ItemStack itemStack, @NotNull ClickHandler clickHandler,
                                   @Nullable UnaryOperator<ItemStack> updater) {
            return this.addSlotData(slot, new SlotData(itemStack, guiType.createSubHandler(clickHandler), updater));
        }

        private void createSlot(int slot) {
            for (int x = this.slots.size(); x <= slot; ++x) {
                this.slots.add(x, Constants.UNFILLED_SLOT_DATA);
            }
        }

        public GUI.Builder setClearColumn(int column) {

            ListUtil.setClearColumn(this.slots, column);

            this.checkSize();

            return this;
        }

        public GUI.Builder setClearLine(int line) {
            ListUtil.setClearLine(this.slots, line);

            this.checkSize();

            return this;
        }

        public GUI.Builder removeItem(ItemStack itemStack) {
            slots.removeIf(slotData -> {
                Optional<ItemStack> stack = slotData.getItemStack();

                return stack.isPresent() && stack.get().equals(itemStack);
            });

            return this;
        }


        public GUI build() {
            this.checkSize();

            Pages pages = PageUtil.toPages(this.slots);

            return new GUI(this.guiType, this.title, pages, InventoryFactory.INSTANCE);
        }

        private void checkSize() {
            if (slots.size() > Constants.PAGE_LIMIT) {
                throw new IllegalStateException("Page limit exceeded! Page limit: " + Constants.PAGE_LIMIT + "!");
            }
        }


    }

}
