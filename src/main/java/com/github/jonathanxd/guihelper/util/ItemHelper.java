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

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by jonathan on 22/07/16.
 */
public class ItemHelper {

    public static ItemStack stack(Material material, String name) {
        return stack(material, 1, name);
    }

    public static ItemStack stack(Material material, String name, Lore lore) {
        return stack(material, 1, name, lore);
    }

    public static ItemStack stack(Material material, int amount, String name) {
        ItemStack itemStack = new ItemStack(material, amount);

        return ItemHelper.manipulateMeta(itemStack, itemMeta -> {
            if (name != null) itemMeta.setDisplayName(name);

        });
    }


    public static ItemStack stack(Material material, int amount, String name, Lore lore) {
        ItemStack itemStack = new ItemStack(material, amount);

        return ItemHelper.manipulateMeta(itemStack, itemMeta -> {
            if (name != null) itemMeta.setDisplayName(name);

            itemMeta.setLore(lore.getLore());
        });
    }

    public static ItemStack enchantedBook(String name, Map<Enchantment, Integer> enchantments) {
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);

        return ItemHelper.manipulateMeta(itemStack, itemMeta -> {
            if (name != null) itemMeta.setDisplayName(name);

            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemMeta;

            enchantments.forEach((enchantment, integer) -> meta.addStoredEnchant(enchantment, integer, true));
        });
    }

    public static ItemStack manipulateMeta(ItemStack itemStack, Consumer<ItemMeta> itemMetaConsumer) {
        if (itemStack == null || itemStack.getType() == Material.AIR)
            return itemStack;

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMetaConsumer.accept(itemMeta);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}
