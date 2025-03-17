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
package org.cactoos.list;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test cases for {@link ListIteratorEnvelope}.
 *
 * @since 0.35
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocTypeCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class ListIteratorEnvelopeTest {
    @Test
    void mustReturnPreviousIndex() {
        new Assertion<>(
            "List Iterator must return previous index",
            new StringListIterator(
                "1"
            ).previousIndex(),
            new IsEqual<>(-1)
        ).affirm();
    }

    @Test
    void mustReturnPreviousElement() {
        new Assertion<>(
            "List Iterator must return previous element",
            new StringListIterator(
                1,
                "3", "7"
            ).previous(),
            new IsEqual<>("3")
        ).affirm();
    }

    @Test
    void mustReturnNextIndex() {
        new Assertion<>(
            "List iterator must return next index",
            new StringListIterator(
                "1"
            ).nextIndex(),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    void mustReturnNextElement() {
        new Assertion<>(
            "List iterator must return next item",
            new StringListIterator(
                1,
                "5", "11", "13"
            ).next(),
            new IsEqual<>("11")
        ).affirm();
    }

    private static final class StringListIterator extends ListIteratorEnvelope<String> {
        StringListIterator(final int index, final String... elements) {
            super(new ListOf<>(elements).listIterator(index));
        }

        StringListIterator(final String... elements) {
            super(new ListOf<>(elements).listIterator());
        }
    }
}
