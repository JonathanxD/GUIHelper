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
package com.github.jonathanxd.guihelper.handler;

import com.github.jonathanxd.guihelper.data.ClickData;
import com.github.jonathanxd.guihelper.result.Result;
import com.github.jonathanxd.guihelper.result.ResultIDs;

/**
 * Created by jonathan on 23/07/16.
 */
public abstract class SubHandler {

    public static final SubHandler NONE = SubHandler.DEFAULT(ClickHandler.NONE);

    private final ClickHandler clickHandler;

    protected SubHandler(ClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    public abstract boolean onClickSlot(ClickData clickData);

    public boolean direct(ClickData clickData) {
        return this.getClickHandler().onClick(clickData);
    }

    public ClickHandler getClickHandler() {
        return clickHandler;
    }

    public static SubHandler none() {
        return SubHandler.NONE;
    }

    public static SubHandler DEFAULT(ClickHandler clickHandler) {
        return new SubHandler.Default(clickHandler);
    }

    public static SubHandler SELECTION(ClickHandler clickHandler, int selections) {
        return new SubHandler.Selection(clickHandler, selections);
    }

    private static final class Default extends SubHandler {

        Default(ClickHandler handler) {
            super(handler);
        }

        @Override
        public boolean onClickSlot(ClickData clickData) {
            return this.getClickHandler().onClick(clickData);
        }
    }

    private static final class Selection extends SubHandler {

        private final int selections;

        protected Selection(ClickHandler clickHandler, int selections) {
            super(clickHandler);
            this.selections = selections;
        }

        @Override
        public boolean onClickSlot(ClickData clickData) {
            if(this.getClickHandler().onClick(clickData)) {

                int selectionCount = clickData.viewSection.result.get(ResultIDs.SELECTION).size();

                if(selectionCount >= selections) {
                    clickData.viewSection.close(clickData.player);
                } else {
                    clickData.viewSection.result.add(ResultIDs.SELECTION, clickData);

                    if(selectionCount + 1 == selections)
                        clickData.viewSection.close(clickData.player);
                }
            }

            return true;
        }
    }
}
