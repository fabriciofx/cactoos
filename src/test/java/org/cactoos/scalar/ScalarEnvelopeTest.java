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
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link ScalarEnvelope}.
 *
 * @since 0.41
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocTypeCheck (500 lines)
 */
final class ScalarEnvelopeTest {

    @Test
    void envelopeDelegatesCalls() {
        new Assertion<>(
            "must delegate calls to apply",
            new Static(1),
            new HasValue<>(1)
        ).affirm();
    }

    @Test
    void propagatesException() {
        final String message = "ok";
        final Scalar<Integer> scalar = () -> {
            throw new UnsupportedOperationException(message);
        };
        new Assertion<>(
            "must not alter the exception thrown by original Scalar",
            new ScalarEnvelope<Integer>(scalar) {
            },
            new Throws<>(
                new IsEqual<>(message),
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    private static final class Static extends ScalarEnvelope<Integer> {
        Static(final int result) {
            super(() -> result);
        }
    }
}
