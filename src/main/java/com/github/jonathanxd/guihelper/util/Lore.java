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
package com.github.jonathanxd.guihelper.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 22/07/16.
 */
public class Lore {
    private final List<String> lore = new ArrayList<>();

    private Lore() {

    }

    public List<String> getLore() {
        return lore;
    }

    public Lore line(int line, String lore) {
        if(line < 0) {
            return this;
        }

        if(line == 0)
            return this.setTitle(lore);

        if(line > this.getLore().size())
            for(int x = this.getLore().size(); x <= line; ++x)
                this.getLore().add("");

        this.getLore().set(line, lore);

        return this;
    }

    public Lore line0(String lore) {
        return this.setTitle(lore);
    }

    public Lore line1(String lore) {
        return this.line(1, lore);
    }

    public Lore line2(String lore) {
        return this.line(2, lore);
    }

    public Lore line3(String lore) {
        return this.line(3, lore);
    }

    public Lore add(String lore) {
        this.getLore().add(lore);

        return this;
    }

    public Lore setTitle(String title) {
        if(this.getLore().isEmpty())
            this.getLore().add(title);
        else
            this.getLore().set(0, title);

        return this;
    }

    public static Lore first(String lore) {
        Lore lore1 = new Lore();

        lore1.getLore().add(lore);

        return lore1;
    }

}
