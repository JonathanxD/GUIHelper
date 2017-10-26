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
package com.github.jonathanxd.guihelper.gui;

import com.github.jonathanxd.guihelper.data.ClickData;
import com.github.jonathanxd.guihelper.data.SlotData;
import com.github.jonathanxd.guihelper.manager.GUIManager;
import com.github.jonathanxd.guihelper.result.Result;
import com.github.jonathanxd.guihelper.view.ResultReceiver;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by jonathan on 22/07/16.
 */
public class ViewSection {

    public final GUI gui;
    public final GUIManager guiManager;
    public final Input input;
    public final InputView inputView;
    public final int currentPage;
    public final ViewSection parent;
    public final Result result = new Result();
    public final ResultReceiver resultReceiver;

    public ViewSection(GUI gui, GUIManager guiManager, Input input, InputView inputView, int currentPage, ViewSection parent, ResultReceiver resultReceiver) {
        this.gui = gui;
        this.guiManager = guiManager;
        this.input = input;
        this.inputView = inputView;
        this.currentPage = currentPage;
        this.parent = parent;
        this.resultReceiver = resultReceiver;
    }

    public ViewSection copy(InputView inputView, Input input) {
        return new ViewSection(this.gui, this.guiManager, input, inputView, this.currentPage, this.parent, this.resultReceiver);
    }

    public ViewSection copy(int page) {
        return new ViewSection(this.gui, this.guiManager, this.input, this.inputView, page, this.parent, this.resultReceiver);
    }

    public void consumeResult(Player player) {
        this.resultReceiver.accept(player, this, this.result);
    }

    public boolean previousPage(Player player) {
        if(this.currentPage - 1 <= -1)
            return false;

        this.guiManager.openView(player, this.copy(this.currentPage - 1));

        return true;
    }

    public boolean nextPage(Player player) {
        if(this.currentPage + 1 > gui.getPages().size())
            return false;

        this.guiManager.openView(player, this.copy(this.currentPage + 1));

        return true;
    }

    public boolean handleClick(Player player, int slot, ItemStack stack) {

        if(stack == null)
            stack = new ItemStack(Material.AIR);

        Page page = gui.getPages().get(currentPage);

        List<SlotData> slotDatas = page.getSlotDatas();

        if(slot >= slotDatas.size())
            return false;

        SlotData slotData = slotDatas.get(slot);

        ClickData clickData = new ClickData(player, this.gui, slot, stack, input, this);

        return slotData.getHandler().onClickSlot(clickData);
    }

    public void close(Player player) {
        this.guiManager.closeCurrentView(player);
    }
}
