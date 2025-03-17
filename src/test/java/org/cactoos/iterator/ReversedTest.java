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

import org.cactoos.list.ListOf;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasSize;

/**
 * Test case for {@link Reversed}.
 *
 * @since 0.45
 */
final class ReversedTest {

    @Test
    void reversesIterator() {
        new Assertion<>(
            "Must reverse the iterator",
            new ListOf<>(
                new Reversed<>(
                    new IteratorOf<>("c", "a", "c", "t", "o", "o", "s")
                )
            ),
            new IsEqual<>(
                new ListOf<>(
                    new IteratorOf<>("s", "o", "o", "t", "c", "a", "c")
                )
            )
        ).affirm();
    }

    @Test
    void reversesEmptyList() {
        new Assertion<>(
            "Must reverse empty list",
            new ListOf<>(
                new Reversed<>(
                    new IteratorOf<>()
                )
            ),
            new IsEmptyCollection<>()
        ).affirm();
    }

    @Test
    void iteratorSizeReversed() {
        new Assertion<>(
            "Must be the same size",
            new ListOf<>(
                new Reversed<>(
                    new IteratorOf<>("c", "a", "c", "t", "o", "o", "s")
                )
            ),
            new HasSize(7)
        ).affirm();
    }

}
