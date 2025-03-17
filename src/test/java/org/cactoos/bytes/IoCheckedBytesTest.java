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
package org.cactoos.bytes;

import java.io.IOException;
import org.cactoos.Fallback;
import org.cactoos.Text;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link IoCheckedBytes}.
 *
 * @since 0.52
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IoCheckedBytesTest {

    @Test
    void rethrowsCheckedToUncheckedException() {
        new Assertion<>(
            "Must rethrow checked to io-checked exception",
            () -> new IoCheckedBytes(
                () -> {
                    throw new IOException("intended");
                }
            ).asBytes(),
            new Throws<>(IOException.class)
        ).affirm();
    }

    @Test
    void worksNormallyWhenNoExceptionIsThrown() throws Exception {
        final Text source = new TextOf("hello, cactoos!");
        new Assertion<>(
            "Must work normally when no exception is thrown",
            new IoCheckedBytes(
                new BytesOf(source)
            ).asBytes(),
            new IsEqual<>(new BytesOf(source).asBytes())
        ).affirm();
    }

    @Test
    void worksWithFallback() throws IOException {
        final byte[] empty = {};
        new Assertion<>(
            "Must work with fallback",
            new IoCheckedBytes(
                () -> {
                    throw new IllegalArgumentException("OK");
                },
                new Fallback.From<>(
                    IllegalArgumentException.class,
                    ex -> empty
                )
            ).asBytes(),
            new IsEqual<>(empty)
        ).affirm();
    }
}
