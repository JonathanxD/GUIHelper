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

import com.github.jonathanxd.guihelper.handler.ClickHandler;
import com.github.jonathanxd.guihelper.handler.SubHandler;

/**
 * Created by jonathan on 23/07/16.
 */
public final class GUITypes {

    private GUITypes() {

    }

    public static GUIType view() {
        return new View();
    }

    public static GUIType selection(int selections) {
        return new Selection(selections);
    }

    private static final class View implements GUIType {

        @Override
        public SubHandler createSubHandler(ClickHandler handler) {
            return SubHandler.DEFAULT(handler);
        }
    }

    private static final class Selection implements GUIType {

        private final int selections;

        private Selection(int selections) {
            this.selections = selections;
        }

        @Override
        public SubHandler createSubHandler(ClickHandler handler) {
            return SubHandler.SELECTION(handler, this.selections);
        }
    }
}
