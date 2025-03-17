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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import org.cactoos.text.TextOf;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link TeeOutputStream}.
 * @since 0.16
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class TeeOutputStreamTest {

    @Test
    @SuppressWarnings("unchecked")
    void copiesContentByteByByte() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ByteArrayOutputStream copy = new ByteArrayOutputStream();
        final String content = "Hello, товарищ!";
        new Assertion<>(
            "Must copy OutputStream to OutputStream byte by byte",
            new TextOf(
                new ReaderOf(
                    new TeeInputStream(
                        new ByteArrayInputStream(
                            content.getBytes(StandardCharsets.UTF_8)
                        ),
                        new TeeOutputStream(baos, copy)
                    )
                )
            ).asString(),
            new AllOf<>(
                new IsEqual<>(content),
                new IsEqual<>(
                    new String(baos.toByteArray(), StandardCharsets.UTF_8)
                ),
                new IsEqual<>(
                    new String(copy.toByteArray(), StandardCharsets.UTF_8)
                )
            )
        ).affirm();
    }

}
