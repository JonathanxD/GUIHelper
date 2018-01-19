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
package com.github.jonathanxd.guihelper.util;

import com.github.jonathanxd.guihelper.data.SlotData;
import com.github.jonathanxd.guihelper.factory.AnvilFactory;
import com.github.jonathanxd.guihelper.factory.TextInputFactory;
import com.github.jonathanxd.guihelper.gui.GUI;
import com.github.jonathanxd.guihelper.gui.GUIType;
import com.github.jonathanxd.guihelper.gui.Page;
import com.github.jonathanxd.guihelper.gui.Pages;
import com.github.jonathanxd.guihelper.handler.Handler;
import com.github.jonathanxd.guihelper.handler.SubHandler;
import com.github.jonathanxd.iutils.map.MapUtils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Created by jonathan on 22/07/16.
 */
public class Constants {

    public static final SlotData NEXT_PAGE_DATA = new SlotData(ItemHelper.stack(Material.EMPTY_MAP, "Proximo"), click -> click.viewSection.nextPage(click.player));
    public static final SlotData PREVIOUS_PAGE_DATA = new SlotData(ItemHelper.stack(Material.EMPTY_MAP, "Anterior"), click -> click.viewSection.previousPage(click.player));

    /**
     * An empty element that can't store anything.
     */
    public static final SlotData EMPTY_SLOT_DATA = new SlotData(new ItemStack(Material.AIR), SubHandler.none());

    /**
     * An empty element that CAN store something.
     */
    public static final SlotData UNFILLED_SLOT_DATA = new SlotData(new ItemStack(Material.AIR), SubHandler.none());

    public static final Table ITEM_TABLE = new Table(6, 9);

    public static final int MAX_SIZE = 54;
    public static final int ONE_LINE = 9;
    public static final int PAGER = 18;
    public static final int SIZE_WITHOUT_PAGER = MAX_SIZE - PAGER;
    public static final int PAGE_LIMIT = MAX_SIZE * 64;

    public static final int PAGER_START = SIZE_WITHOUT_PAGER + ONE_LINE;
    public static final int PAGER_END = SIZE_WITHOUT_PAGER + PAGER;

    public static final List<SlotData> SEVEN_EMPTY_SLOTS;
    public static final List<SlotData> EMPTY_PAGER;
    public static final List<SlotData> NEXT_PAGER;
    public static final List<SlotData> PREVIOUS_PAGER;
    public static final List<SlotData> PREVIOUS_AND_NEXT_PAGER;

    static {

        SEVEN_EMPTY_SLOTS = Constants.immutable(list -> {
            for (int x = 0; x < 7; ++x) {
                list.add(EMPTY_SLOT_DATA);
            }
        });

        EMPTY_PAGER = Constants.immutable(list -> {
            for (int x = 0; x < 9; ++x) {
                list.add(EMPTY_SLOT_DATA);
            }
        });

        NEXT_PAGER = Constants.immutable(list -> {
            list.add(EMPTY_SLOT_DATA);

            list.addAll(Constants.SEVEN_EMPTY_SLOTS);

            list.add(Constants.NEXT_PAGE_DATA);
        });

        PREVIOUS_PAGER = Constants.immutable(list -> {
            list.add(Constants.PREVIOUS_PAGE_DATA);

            list.addAll(Constants.SEVEN_EMPTY_SLOTS);

            list.add(EMPTY_SLOT_DATA);
        });


        PREVIOUS_AND_NEXT_PAGER = Constants.immutable(list -> {
            list.add(Constants.PREVIOUS_PAGE_DATA);

            list.addAll(Constants.SEVEN_EMPTY_SLOTS);

            list.add(Constants.NEXT_PAGE_DATA);
        });

    }

    private static <T> List<T> immutable(Consumer<List<T>> consumer) {
        List<T> list = new ArrayList<>();

        consumer.accept(list);

        return Collections.unmodifiableList(list);
    }

    public static GUI createTextInput(GUIType guiType, String title, Consumer<String> resultConsumer) {
        return createTextInput(guiType, title, s -> true, resultConsumer);
    }

    public static GUI createTextInput(GUIType guiType, String title, Predicate<String> resultPredicate, Consumer<String> consumer) {

        Pages pages = new Pages();

        pages.add(new Page(Collections.singletonList(
                new SlotData(null, click -> {

                    String text = click.stack.getItemMeta().getDisplayName();

                    if(resultPredicate.test(text)) {
                        click.viewSection.guiManager.closeCurrentView(click.player);
                        consumer.accept(text);
                        return true;
                    }

                    if(text.equals("#cancel")) {
                        click.viewSection.guiManager.closeCurrentView(click.player);
                        return true;
                    }

                    return false;
                })
        )));

        return new GUI(guiType, title, pages, TextInputFactory.INSTANCE);
    }

    /**
     * @deprecated Not working
     */
    @Deprecated
    public static GUI createAnvilGui(GUIType guiType, String title, Consumer<String> resultConsumer) {
        Pages pages = new Pages();


        SlotData inputSlot = new SlotData(ItemHelper.stack(Material.DIAMOND_SWORD, 1, "Sword"), click -> false);
        SlotData secondSlot = new SlotData(ItemHelper.enchantedBook("Enchanted Book", MapUtils.mapOf(Enchantment.DAMAGE_ALL, 1)), click -> false);
        SlotData output = new SlotData(null, click -> {
            if(click.stack.hasItemMeta()) {
                ItemMeta meta = click.stack.getItemMeta();

                if(meta.hasDisplayName())
                    resultConsumer.accept(meta.getDisplayName());
            }

            click.player.closeInventory();
            return true;
        });

        pages.add(new Page(Arrays.asList(inputSlot, secondSlot, output)));

        return new GUI(guiType, title, pages, AnvilFactory.INSTANCE);
    }
}
