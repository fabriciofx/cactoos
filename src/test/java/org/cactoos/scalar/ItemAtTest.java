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
package org.cactoos.scalar;

import java.io.IOException;
import org.cactoos.iterable.IterableOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test Case for {@link ItemAt}.
 *
 * @since 0.7
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals" })
final class ItemAtTest {

    @Test
    void elementByPosIterableTest() {
        new Assertion<>(
            "must take the item by position from the iterable",
            new ItemAt<>(
                1, new IterableOf<>(1, 2, 3)
            ),
            new HasValue<>(2)
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void elementByPosFallbackIterableTest() {
        final int fallback = 5;
        new Assertion<>(
            "must fallback to default one",
            new ItemAt<>(
                1, fallback, new IterableOf<>()
            ),
            new HasValue<>(fallback)
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void elementByPosNoFallbackIterableTest() {
        new Assertion<>(
            "must take the item by position from the iterable",
            new ItemAt<>(
                1, 5, new IterableOf<>(0, 1)
            ),
            new HasValue<>(1)
        ).affirm();
    }

    @Test
    void elementByPosTest() {
        new Assertion<>(
            "must take the item by position from the iterator",
            new ItemAt<>(
                1,
                new IterableOf<>(1, 2, 3)
            ),
            new HasValue<>(2)
        ).affirm();
    }

    @Test
    void failForNegativePositionTest() {
        new Assertion<>(
            "Must fail for negative position",
            () -> new ItemAt<>(
                -1,
                new IterableOf<>(1, 2, 3)
            ).value(),
            new Throws<>(
                "The position must be non-negative: -1",
                IOException.class
            )
        ).affirm();
    }

    @Test
    void failForPosMoreLengthTest() {
        new Assertion<>(
            "Must fail for greater than length position",
            () -> new ItemAt<>(
                3,
                new IterableOf<>(1, 2, 3)
            ).value(),
            new Throws<>(
                "The iterable doesn't have the position #3",
                IOException.class
            )
        ).affirm();
    }

    @Test
    void sameValueTest() throws Exception {
        final ItemAt<Integer> item = new ItemAt<>(
            1,
            new IterableOf<>(1, 2, 3)
        );
        new Assertion<>(
            "Not the same value",
            item,
            new HasValue<>(item.value())
        ).affirm();
    }
}
