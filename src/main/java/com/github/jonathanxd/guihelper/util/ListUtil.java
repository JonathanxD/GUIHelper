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

import java.util.List;

/**
 * Created by jonathan on 22/07/16.
 */
public final class ListUtil {

    private ListUtil() {

    }

    public static void setClearColumn(List<SlotData> slots, int column) {

        for (int i = 0; i < slots.size(); i++) {

            SlotData slotData = slots.get(i);

            if (Constants.ITEM_TABLE.getColumn(i) == column && slotData != Constants.EMPTY_SLOT_DATA) {
                ListUtil.addTo(slots, i, slotData);

                i = 0;
            }

        }

    }

    public static void setClearLine(List<SlotData> slots, int line) {
        for (int i = 0; i < slots.size(); i++) {

            SlotData slotData = slots.get(i);

            if (Constants.ITEM_TABLE.getLine(i) == line && slotData != Constants.EMPTY_SLOT_DATA) {
                ListUtil.addTo(slots, i, slotData);
                i = 0;
            }

        }
    }

    public static void addTo(List<SlotData> slots, int index, SlotData slotData) {
        if(slotData == Constants.UNFILLED_SLOT_DATA) {
            slots.set(index, Constants.EMPTY_SLOT_DATA);
        }else {
            slots.add(index, Constants.EMPTY_SLOT_DATA);
        }
    }

    public static void setPager(List<SlotData> slots, List<SlotData> pagerElements) {

        if (pagerElements.size() != 9)
            throw new IllegalArgumentException("GUI Pager must have 9 elements. Current elements: "+pagerElements);


        ListUtil.fillToEmpty(slots, Constants.SIZE_WITHOUT_PAGER, Constants.SIZE_WITHOUT_PAGER + Constants.ONE_LINE);

        ListUtil.fillTo(slots, Constants.PAGER_START, pagerElements);

    }

    public static void fillToEmpty(List<SlotData> slots, int from, int to) {
        if(slots.size() < from) {
            from = slots.size();
        }
        for (int x = from; x < to; ++x) {
            slots.add(x, Constants.EMPTY_SLOT_DATA);
        }
    }

    public static void fillTo(List<SlotData> slots, int from, List<SlotData> toAdd) {
        int to = from + toAdd.size();

        int pos = 0;

        for (int x = from; x < to; ++x, ++pos) {
            slots.add(x, toAdd.get(pos));
        }
    }
}
