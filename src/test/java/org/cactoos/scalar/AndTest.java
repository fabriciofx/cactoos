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

import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link And}.
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class AndTest {

    @Test
    void allTrue() {
        new Assertion<>(
            "Each object must be True",
            new And(
                new True(),
                new True(),
                new True()
            ),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void oneFalse() {
        new Assertion<>(
            "One object must be False",
            new And(
                new True(),
                new False(),
                new True()
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void allFalse() {
        new Assertion<>(
            "Each object must be False",
            new And(
                new IterableOf<Scalar<Boolean>>(
                    new False(),
                    new False(),
                    new False()
                )
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void emptyIterator() {
        new Assertion<>(
            "Iterator must be empty",
            new And(new IterableOf<Scalar<Boolean>>()),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void testFuncIterable() {
        new Assertion<>(
            "lambda should be called for iterable",
            new And(
                input -> input > 0,
                new IterableOf<>(1, -1, 0)
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void testFuncVarargs() {
        new Assertion<>(
            "lambda should be called for varargs",
            new And(
                input -> input > 0,
                -1, -2, 0
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void testMultipleFuncConditionTrue() {
        new Assertion<>(
            "Can't compare subject with true conditions",
            new And(
                3,
                input -> input > 0,
                input -> input > 5,
                input -> input > 4
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void testMultipleFuncConditionFalse() {
        new Assertion<>(
            "Can't compare subject with false conditions",
            new And(
                "cactoos",
                input -> input.contains("singleton"),
                input -> input.contains("static")
            ),
            new HasValue<>(false)
        ).affirm();
    }
}
