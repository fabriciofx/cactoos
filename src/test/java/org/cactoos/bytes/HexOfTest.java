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
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Satisfies;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link HexOf}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 line)
 */
final class HexOfTest {

    @Test
    void emptyText() throws Exception {
        new Assertion<>(
            "Must represent an empty hexadecimal text",
            new HexOf(new TextOf("")).asBytes(),
            new Satisfies<>(array -> array.length == 0)
        ).affirm();
    }

    @Test
    void validHex() throws Exception {
        final byte[] bytes = new byte[256];
        for (int index = 0; index < 256; ++index) {
            bytes[index] = (byte) (index + (int) Byte.MIN_VALUE);
        }
        new Assertion<>(
            "Must convert hexadecimal text to bytes",
            new HexOf(
                new org.cactoos.text.HexOf(
                    new BytesOf(bytes)
                )
            ).asBytes(),
            new IsEqual<>(bytes)
        ).affirm();
    }

    @Test
    void invalidHexLength() {
        new Assertion<>(
            "Must invalid hex length",
            () -> new HexOf(new TextOf("ABF")).asBytes(),
            new Throws<>(
                "Length of hexadecimal text is odd",
                IOException.class
            )
        ).affirm();
    }

    @Test
    void invalidHex() {
        new Assertion<>(
            "Must invalid hex",
            () -> new HexOf(new TextOf("ABG!")).asBytes(),
            new Throws<>(
                "Unexpected character 'G'",
                IOException.class
            )
        ).affirm();
    }
}
