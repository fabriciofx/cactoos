/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2025 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Tests for {@link MappedWithIndex}.
 * @since 1.0.0
 */
final class MappedWithIndexTest {
    @Test
    void iteratesOver() {
        new Assertion<>(
            "must map values of iterator",
            new IterableOf<>(
                new MappedWithIndex<>(
                    (item, index) -> new FormattedText(
                        "%1$s - %2$s",
                        index,
                        item
                    ),
                    new IterableOf<>("1", "2", "0").iterator()
                )
            ),
            new HasValues<>(
                new TextOf("0 - 1"),
                new TextOf("1 - 2"),
                new TextOf("2 - 0")
            )
        ).affirm();
    }

    @Test
    void failsIfIteratorExhausted() {
        final Iterator<Text> iterator = new MappedWithIndex<>(
            (item, index) -> new FormattedText(
                "%1$s X %2$s",
                index,
                item
            ),
            new IterableOf<>("1").iterator()
        );
        iterator.next();
        new Assertion<>(
            "must throw NSEE",
            iterator::next,
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }

    @Test
    void removingElementsFromIterator() {
        final Iterator<Text> iterator = new MappedWithIndex<>(
            (item, index) -> new FormattedText(
                "%1$s : %2$s",
                index,
                item
            ),
            new ArrayList<>(Arrays.asList("1", "2", "3")).iterator()
        );
        iterator.next();
        iterator.remove();
        new Assertion<>(
            "must map values of changed iterator",
            new IterableOf<>(
                iterator
            ),
            new HasValues<>(
                new TextOf("1 : 2"),
                new TextOf("2 : 3")
            )
        ).affirm();
    }
}
