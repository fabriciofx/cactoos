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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.scalar.LengthOf;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasContent;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * Test case for {@link WriterAsOutputStream}.
 *
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestsShouldIncludeAssert"})
final class WriterAsOutputStreamTest {

    @Test
    void writesToByteArray() {
        final String content = "Hello, товарищ! How are you?";
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new Assertion<>(
            "Can't copy Input to Writer",
            new TeeInput(
                new InputOf(content),
                new OutputTo(
                    new WriterAsOutputStream(
                        new OutputStreamWriter(
                            baos, StandardCharsets.UTF_8
                        ),
                        StandardCharsets.UTF_8,
                        13
                    )
                )
            ),
            new HasContent(
                new TextOf(baos::toByteArray, StandardCharsets.UTF_8)
            )
        ).affirm();
    }

    @Test
    void writesLargeContentToFile(@TempDir final Path wdir) throws IOException {
        final Path temp = wdir.resolve("writestream1.txt");
        try (OutputStreamWriter writer = new OutputStreamWriter(
            Files.newOutputStream(temp.toAbsolutePath()),
            StandardCharsets.UTF_8
        )) {
            new Assertion<>(
                "Can't copy Input to Output and return Input",
                new TeeInput(
                    new ResourceOf("org/cactoos/large-text.txt"),
                    new OutputTo(
                        new WriterAsOutputStream(
                            writer,
                            StandardCharsets.UTF_8,
                            345
                        )
                    )
                ),
                new HasContent(
                    new TextOf(temp)
                )
            ).affirm();
        }
    }

    @Test
    void writesToFileAndRemovesIt(@TempDir final Path wdir) throws Exception {
        final Path temp = wdir.resolve("writestream2.txt");
        try (OutputStreamWriter writer = new OutputStreamWriter(
            Files.newOutputStream(temp.toAbsolutePath()),
            StandardCharsets.UTF_8
        )) {
            final String content = "Hello, товарищ! How are you?";
            new LengthOf(
                new TeeInput(
                    new InputOf(content),
                    new OutputTo(
                        new WriterAsOutputStream(
                            writer,
                            StandardCharsets.UTF_8,
                            345
                        )
                    )
                )
            ).value();
        }
        Files.delete(temp);
        new Assertion<>(
            "file must not exist anymore",
            Files.exists(temp),
            new IsNot<>(new IsTrue())
        ).affirm();
    }
}
