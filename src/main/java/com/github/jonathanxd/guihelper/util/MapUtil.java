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

import com.github.jonathanxd.iutils.container.primitivecontainers.IntContainer;
import com.github.jonathanxd.iutils.function.stream.BiStreams;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Created by jonathan on 22/07/16.
 */
public class MapUtil {

    public static <T> void removeIfKey(Map<T, ?> map, Predicate<? super T> predicate) {
        BiStreams.mapStream(map).filter((t, o) -> predicate.test(t)).forEachOrdered((k, v) -> map.remove(k));
    }

    public static <K> void removeIfValue(Map<?, K> map, Predicate<? super K> predicate) {
        BiStreams.mapStream(map).filter((t, o) -> predicate.test(o)).forEachOrdered((k, v) -> map.remove(k));
    }

    public static <T, K> void removeIf(Map<T, K> map, BiPredicate<? super T, ? super K> predicate) {
        BiStreams.mapStream(map).filter(predicate).forEachOrdered(map::remove);
    }

    public static <V> void putAt(Map<Integer, V> map, int slot, V value) {
        Map<Integer, V> map2 = new HashMap<>();

        BiStreams.mapStream(map).forEachOrdered((integer, v) -> {
            if (integer == slot) {
                map2.put(slot, value);
            } else if (integer > slot) {
                map2.put(integer + 1, v);
            } else {
                map2.put(integer, v);
            }
        });

        map.clear();
        map.putAll(map2);
    }

    public static <V> void clearColumn(Map<Integer, V> map, int column, Table table) {
        Map<Integer, V> map2 = new HashMap<>();
        IntContainer offset = IntContainer.of(0);

        BiStreams.mapStream(map).forEachOrdered((integer, v) -> {
            if (table.getColumn(integer) == column) {
                offset.add();
            } else {
                map2.put(integer + offset.get(), v);
            }
        });

        map.clear();
        map.putAll(map2);
    }
}