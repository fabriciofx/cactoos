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
package org.cactoos.iterable;

import org.cactoos.list.ListOf;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasSize;

/**
 * Test case for {@link Reversed}.
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class ReversedTest {

    @Test
    void reversesIterable() {
        new Assertion<>(
            "Must reverse an iterable",
            new Reversed<>(
                new IterableOf<>(
                    "h", "w", "d"
                )
            ),
            new IsEqual<>(new IterableOf<>("d", "w", "h"))
        ).affirm();
    }

    @Test
    void iteratesMultipleTimes() {
        final Iterable<String> itr = new Reversed<>(
            new IterableOf<>("h", "w", "d")
        );
        final Iterable<String> expected = new IterableOf<>("d", "w", "h");
        new Assertion<>(
            "Must iterates once",
            itr,
            new IsEqual<>(expected)
        ).affirm();
        new Assertion<>(
            "Must iterates twice",
            itr,
            new IsEqual<>(expected)
        ).affirm();
    }

    @Test
    void reverseList() {
        final String last = "last";
        new Assertion<>(
            "Must reverse list",
            new Reversed<>(
                new ListOf<>(
                    "item", last
                )
            ).iterator().next(),
            new IsEqual<>(last)
        ).affirm();
    }

    @Test
    void reverseEmptyList() {
        new Assertion<>(
            "Must reverse empty list",
            new Reversed<>(
                new ListOf<>()
            ),
            new IsEmptyIterable<>()
        ).affirm();
    }

    @Test
    void size() {
        new Assertion<>(
            "Size must be the same",
            new Reversed<>(
                new IterableOf<>(
                    "0", "1", "2"
                )
            ),
            new HasSize(3)
        ).affirm();
    }

    @Test
    void isEmpty() {
        new Assertion<>(
            "Must be not empty",
            new Reversed<>(
                new IterableOf<>(
                    6, 16
                )
            ),
            new IsNot<>(new IsEmptyIterable<>())
        ).affirm();
    }
}
