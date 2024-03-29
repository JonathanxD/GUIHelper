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

import com.github.jonathanxd.guihelper.data.SlotData;
import com.github.jonathanxd.guihelper.gui.InputView;
import com.github.jonathanxd.guihelper.gui.Page;
import com.github.jonathanxd.guihelper.manager.GUIManager;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.List;

public final class DynamicGuiUpdater implements Runnable {
    private final GUIManager guiManager;
    private final Server server;

    public DynamicGuiUpdater(GUIManager guiManager,
                             Server server) {
        this.guiManager = guiManager;
        this.server = server;
    }

    @Override
    public void run() {
        for (Player player : server.getOnlinePlayers()) {
            guiManager.getCurrentView(player).ifPresent(p -> {
                if (p.inputView instanceof InputView.InvView) {
                    InputView.InvView inputView = (InputView.InvView) p.inputView;
                    int currentPage = p.currentPage;
                    Page page = p.gui.getPages().get(currentPage);
                    List<SlotData> slotDatas = page.getSlotDatas();
                    for (int i = 0; i < slotDatas.size(); i++) {
                        SlotData slotData = slotDatas.get(i);

                        if (slotData.canUpdate()) {
                            inputView.getInventoryView().setItem(i, slotData.update());
                        }
                    }

                }

            });
        }
    }
}
