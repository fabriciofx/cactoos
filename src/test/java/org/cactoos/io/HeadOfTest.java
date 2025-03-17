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

import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test cases for {@link HeadOf}.
 *
 * @since 0.31
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class HeadOfTest {

    @Test
    void readsHeadOfLongerInput() {
        new Assertion<>(
            "must limit exactly the number of read bytes",
            new TextOf(
                new HeadOf(
                    new InputOf("readsHeadOfLongerInput"),
                    5
                )
            ),
            new HasString("reads")
        ).affirm();
    }

    @Test
    void readsEmptyHeadOfInput() {
        new Assertion<>(
            "must limit to 0 the number of read bytes",
            new TextOf(
                new HeadOf(
                    new InputOf("readsEmptyHeadOfInput"),
                    0
                )
            ),
            new HasString("")
        ).affirm();
    }

    @Test
    void readsHeadOfShorterInput() {
        final String input = "readsHeadOfShorterInput";
        new Assertion<>(
            "must limit to at most the number of available bytes",
            new TextOf(
                new HeadOf(
                    new InputOf(input),
                    35
                )
            ),
            new HasString(input)
        ).affirm();
    }
}
