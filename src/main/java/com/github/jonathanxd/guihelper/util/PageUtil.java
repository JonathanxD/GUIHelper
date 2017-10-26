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
import com.github.jonathanxd.guihelper.gui.Page;
import com.github.jonathanxd.guihelper.gui.Pages;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 22/07/16.
 */
public final class PageUtil {

    private PageUtil() {

    }

    public static Pages toPages(List<SlotData> slotDatas) {
        Pages pages = new Pages();

        if(slotDatas.size() > Constants.MAX_SIZE) {

            int completed = 0;

            do {
                int pos = completed + Constants.SIZE_WITHOUT_PAGER;

                if(pos > slotDatas.size())
                    pos = slotDatas.size();

                List<SlotData> subSlots = new ArrayList<>(slotDatas.subList(completed, pos));// + 1 ??

                if(completed + Constants.SIZE_WITHOUT_PAGER < slotDatas.size()) {
                    ListUtil.setPager(subSlots, PageUtil.pager(completed, slotDatas.size()));
                } else {
                    ListUtil.setPager(subSlots, PageUtil.pager(slotDatas.size(), slotDatas.size()));
                }

                pages.add(new Page(subSlots));

                completed += Constants.SIZE_WITHOUT_PAGER;

            } while (completed < slotDatas.size());
            // Divide
        } else {
            pages.add(new Page(slotDatas));
        }

        return pages;
    }


    public static List<SlotData> pager(int pos, int size) {

        boolean hasPrevious = pos > 0;
        boolean hasNext = pos > -1 && (pos + Constants.SIZE_WITHOUT_PAGER) < size;

        boolean hasPreviousAndNext = hasPrevious && hasNext;

        if(hasPreviousAndNext)
            return Constants.PREVIOUS_AND_NEXT_PAGER;

        if(hasNext)
            return Constants.NEXT_PAGER;

        if(hasPrevious)
            return Constants.PREVIOUS_PAGER;

        return Constants.EMPTY_PAGER;
    }

}
