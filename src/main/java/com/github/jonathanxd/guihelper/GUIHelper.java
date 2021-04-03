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
package com.github.jonathanxd.guihelper;

import com.github.jonathanxd.guihelper.listener.GUIListener;
import com.github.jonathanxd.guihelper.manager.GUIManager;
import com.github.jonathanxd.guihelper.manager.SimpleGUIManager;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;

public class GUIHelper {


    /**
     * Initialize GUI Helper
     *
     * @param plugin Plugin
     * @return GUIManager
     */
    public static GUIManager init(Plugin plugin) {

        plugin.getLogger().info("Initialized GUIHelper (created by JonathanxD)!");

        GUIManager guiManager = new SimpleGUIManager(plugin);

        plugin.getServer().getPluginManager().registerEvents(new GUIListener(guiManager), plugin);

        plugin.getServer().getServicesManager().register(GUIManager.class, guiManager, plugin, ServicePriority.Normal);

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new DynamicGuiUpdater(guiManager, plugin.getServer()),
                20L, 20L);

        return guiManager;
    }

}
