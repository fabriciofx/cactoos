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

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.zip.GZIPOutputStream;
import org.cactoos.scalar.LengthOf;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link GzipInput}.
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class GzipInputTest {

    @Test
    void readFromGzipInput() throws Exception {
        final String content = "Hello!";
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (
            Writer writer = new BufferedWriter(
                new OutputStreamWriter(
                    new GZIPOutputStream(out)
                )
            )
        ) {
            writer.write(content);
        }
        final byte[] bytes = out.toByteArray();
        new Assertion<>(
            "Can't read from a gzip input",
            new TextOf(
                new GzipInput(new InputOf(bytes))
            ),
            new IsText(content)
        ).affirm();
    }

    @Test
    void readFromDeadGzipInput() {
        new Assertion<>(
            "Can't read from empty input",
            () -> new LengthOf(
                new GzipInput(new DeadInput())
            ).value(),
            new Throws<>(EOFException.class)
        ).affirm();
    }
}
