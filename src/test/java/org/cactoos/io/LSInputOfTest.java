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
package org.cactoos.io;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link LSInputOf}.
 *
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle AbbreviationAsWordInNameCheck (5 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class LSInputOfTest {

    @Test
    void readsSimpleInput() {
        new Assertion<>(
            "Can't read simple input",
            new LSInputOf(
                new InputOf("hello, world!")
            ).getStringData(),
            new StringContains("world!")
        ).affirm();
    }

    @Test
    void readsBiggerInput() {
        final int size = 400_000;
        new Assertion<>(
            "Can't read bigger input",
            new LSInputOf(
                new InputOf(
                    new SlowInputStream(size)
                )
            ).getStringData().length(),
            new IsEqual<>(size)
        ).affirm();
    }

    @Test
    void countsBytesInBiggerInput() {
        final int size = 300_000;
        new Assertion<>(
            "Can't count bytes in a bigger input",
            new LSInputOf(
                new InputOf(
                    new SlowInputStream(size)
                )
            ).getStringData().length(),
            new IsEqual<>(size)
        ).affirm();
    }

}
