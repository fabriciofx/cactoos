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
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasContent;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link TeeOutput}.
 * @since 0.16
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class TeeOutputTest {

    @Test
    void copiesContent() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new Assertion<>(
            "Can't copy Output to Output and return Input",
            new TeeInput(
                new InputOf("Hello, товарищ!"),
                    new TeeOutput(
                        new OutputTo(baos),
                        new OutputTo(new ByteArrayOutputStream())
                    )
            ),
            new HasContent(
                new TextOf(baos::toByteArray, StandardCharsets.UTF_8)
            )
        ).affirm();
    }

    @Test
    void copiesWithWriter() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new Assertion<>(
            "Can't copy Output with writer",
            new TeeInput(
                new InputOf("Hello, товарищ! writer"),
                new TeeOutput(
                    new OutputTo(baos),
                    new WriterTo(new ByteArrayOutputStream())
                )
            ),
            new HasContent(
                new TextOf(baos::toByteArray, StandardCharsets.UTF_8)
            )
        ).affirm();
    }

    @Test
    void copiesWithWriterAndCharset() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new Assertion<>(
            "Can't copy Output with writer and charset",
            new TeeInput(
                new InputOf(
                    "Hello, товарищ! writer and charset"
                ),
                new TeeOutput(
                    new OutputTo(baos),
                    new WriterTo(new ByteArrayOutputStream()),
                    StandardCharsets.UTF_8
                )
            ),
            new HasContent(
                new TextOf(baos::toByteArray, StandardCharsets.UTF_8)
            )
        ).affirm();
    }

    @Test
    void copiesWithPath(@TempDir final Path wdir) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final File file = wdir.resolve("tree1.txt").toFile();
        new Assertion<>(
            "Must copy Output with path",
            new TextOf(
                new TeeInput(
                    new InputOf("Hello, товарищ! with path"),
                    new TeeOutput(
                        new OutputTo(baos),
                        file.toPath()
                    )
                )
            ),
            new IsText(
                new TextOf(file.toPath())
            )
        ).affirm();
    }

    @Test
    void copiesWithFile(@TempDir final Path wdir) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final File file = wdir.resolve("tree2.txt").toFile();
        new Assertion<>(
            "Must copy Output with file",
            new TextOf(
                new TeeInput(
                    new InputOf("Hello, товарищ! with file"),
                    new TeeOutput(
                        new OutputTo(baos),
                        file
                    )
                )
            ),
            new IsText(
                new TextOf(file.toPath())
            )
        ).affirm();
    }

    @Test
    void copiesWithOutputStream() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new Assertion<>(
            "Can't copy Output with output stream",
            new TeeInput(
                new InputOf(
                    "Hello, товарищ! with output stream"
                ),
                new TeeOutput(
                    new OutputTo(baos),
                    new ByteArrayOutputStream()
                )
            ),
            new HasContent(
                new TextOf(baos::toByteArray, StandardCharsets.UTF_8)
            )
        ).affirm();
    }

}
