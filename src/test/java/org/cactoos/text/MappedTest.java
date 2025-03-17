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

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Tests for @{link Mapped}.
 *
 * @since 0.47
 */
final class MappedTest {

    @Test
    void resultShouldBeEqual() {
        new Assertion<>(
            "must be equal to the same text",
            new Mapped(
                String::toUpperCase,
                new TextOf("hi")
            ),
            new IsEqual<>(new TextOf("HI"))
        ).affirm();
    }

    @Test
    void mapsWithFormat() {
        new Assertion<>(
            "must apply lambda to a string",
            new Mapped(
                s -> String.format("<%s>", s),
                new TextOf("hi")
            ),
            new IsText("<hi>")
        ).affirm();
    }

    @Test
    void maps() {
        new Assertion<>(
            "must apply method reference to a string",
            new Mapped(
                String::toLowerCase,
                new TextOf("ABC")
            ),
            new IsText("abc")
        ).affirm();
    }

}
