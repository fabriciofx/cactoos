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

import java.util.Collections;
import java.util.NoSuchElementException;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Reduced}.
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class ReducedTest {

    @Test
    void failsForEmptyIterable() {
        new Assertion<>(
            "Exception is expected for empty iterable",
            () -> new Reduced<>(
                (first, last) -> first,
                Collections.emptyList()
            ).value(),
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }

    @Test
    void singleAtSingleIterable() {
        final Integer single = 10;
        new Assertion<>(
            "Must find the single",
            new Reduced<>(
                (first, last) -> first,
                new IterableOf<Scalar<Integer>>(() -> single)
            ),
            new HasValue<>(single)
        ).affirm();
    }

    @Test
    void firstAtIterable() {
        final String one = "Apple";
        final String two = "Banana";
        final String three = "Orange";
        new Assertion<>(
            "Must find the first",
            new Reduced<>(
                (first, last) -> first,
                new IterableOf<Scalar<String>>(
                    () -> one,
                    () -> two,
                    () -> three
                )
            ),
            new HasValue<>(one)
        ).affirm();
    }

    @Test
    void lastAtIterable() {
        final Character one = 'A';
        final Character two = 'B';
        final Character three = 'O';
        new Assertion<>(
            "Must find the last",
            new Reduced<>(
                (first, last) -> last,
                new IterableOf<Scalar<Character>>(
                    () -> one,
                    () -> two,
                    () -> three
                )
            ),
            new HasValue<>(three)
        ).affirm();
    }

    @Test
    void lastAtIterableOfValues() {
        final Character one = 'A';
        final Character two = 'B';
        final Character three = 'O';
        new Assertion<>(
            "Must find the last character",
            new Reduced<>(
                new IterableOf<>(one, two, three),
                (first, last) -> last
            ),
            new HasValue<>(three)
        ).affirm();
    }

    @Test
    void constructedFromVarargs() {
        final String one = "One";
        final String two = "Two";
        final String three = "Three";
        new Assertion<>(
            "Must concatenate the strings in vararg array",
            new Reduced<>(
                (first, last) -> first + last,
                one,
                two,
                three
            ),
            new HasValue<>("OneTwoThree")
        ).affirm();
    }
}
