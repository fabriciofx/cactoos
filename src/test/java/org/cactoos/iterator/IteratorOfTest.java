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

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link IteratorOf}.
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IteratorOfTest {

    @Test
    void emptyIteratorDoesNotHaveNext() {
        new Assertion<>(
            "Must create empty iterator",
            new IteratorOf<>().hasNext(),
            new IsNot<>(new IsTrue())
        ).affirm();
    }

    @Test
    void emptyIteratorThrowsException() {
        new Assertion<>(
            "Must throw an exception if empty",
            () -> new IteratorOf<>().next(),
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }

    @Test
    void nonEmptyIteratorDoesNotHaveNext() {
        final Iterator<Integer> iterator = new IteratorOf<>(
            1, 2, 3
        );
        while (iterator.hasNext()) {
            iterator.next();
        }
        new Assertion<>(
            "Must create non empty iterator",
            iterator.hasNext(),
            new IsNot<>(new IsTrue())
        ).affirm();
    }

    @Test
    void nonEmptyIteratorThrowsException() {
        final Iterator<Character> iterator = new IteratorOf<>(
            'a', 'b'
        );
        while (iterator.hasNext()) {
            iterator.next();
        }
        new Assertion<>(
            "Must throw an exception if consumed",
            iterator::next,
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }

    @Test
    void convertStringsToIterator() {
        new Assertion<>(
            "Must create an iterator of strings",
            new IterableOf<>(
                new IteratorOf<>(
                    "a", "b", "c"
                )
            ),
            new HasValues<>(
                "a", "b", "c"
            )
        ).affirm();
    }
}
