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
package org.cactoos.text;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link Rotated}.
 *
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class RotatedTest {

    @Test
    void rotateRightText() {
        new Assertion<>(
            "Can't rotate text to right",
            new Rotated(
                new TextOf("Hello!"), 2
            ),
            new HasString("o!Hell")
        ).affirm();
    }

    @Test
    void rotateLeftText() {
        new Assertion<>(
            "Can't rotate text to left",
            new Rotated(
                new TextOf("Hi!"), -1
            ),
            new HasString("i!H")
        ).affirm();
    }

    @Test
    void noRotateWhenShiftZero() {
        final String nonrotate = "Cactoos!";
        new Assertion<>(
            "Rotate text shift zero",
            new Rotated(
                new TextOf(nonrotate), 0
            ),
            new HasString(nonrotate)
        ).affirm();
    }

    @Test
    void noRotateWhenShiftModZero() {
        final String nonrotate = "Rotate";
        new Assertion<>(
            "Rotate text shift mod zero",
            new Rotated(
                new TextOf(nonrotate), nonrotate.length()
            ),
            new HasString(nonrotate)
        ).affirm();
    }

    @Test
    void noRotateWhenEmpty() {
        new Assertion<>(
            "Rotate text when empty",
            new Rotated(
                new TextOf(""), 2
            ),
            new HasString("")
        ).affirm();
    }
}
