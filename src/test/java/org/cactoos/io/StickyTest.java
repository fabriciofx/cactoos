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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import org.cactoos.bytes.BytesOf;
import org.cactoos.func.Repeated;
import org.cactoos.scalar.LengthOf;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.EndsWith;
import org.llorllale.cactoos.matchers.Satisfies;

/**
 * Test case for {@link Sticky}.
 *
 * @since 0.6
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class StickyTest {

    @Test
    void readsFileContent() {
        new Assertion<>(
            "Must read bytes from a file",
            new Sticky(
                new ResourceOf(
                    "org/cactoos/large-text.txt"
                )
            ),
            new Satisfies<>(
                new Repeated<>(
                    input -> new BytesOf(
                        new TeeInput(input, new DeadOutput())
                    ).asBytes().length == 74_536,
                    10
                )
            )
        ).affirm();
    }

    @Test
    void readsRealUrl() throws MalformedURLException, URISyntaxException {
        new Assertion<>(
            "Must fetch text page from the URL",
            new TextOf(
                new Sticky(
                    new InputOf(
                        new URI(
                            "file:src/test/resources/org/cactoos/large-text.txt"
                        ).toURL()
                    )
                )
            ),
            new EndsWith("est laborum.\n")
        ).affirm();
    }

    @Test
    void readsFileContentSlowlyAndCountsLength() throws Exception {
        final long size = 100_000L;
        new Assertion<>(
            "Must read bytes from a large source slowly and count length",
            new LengthOf(
                new Sticky(
                    new SlowInput(size)
                )
            ).value(),
            new IsEqual<>(size)
        ).affirm();
    }

    @Test
    void readsFileContentSlowly() throws Exception {
        final int size = 130_000;
        new Assertion<>(
            "Must read bytes from a large source slowly",
            new BytesOf(
                new Sticky(
                    new SlowInput(size)
                )
            ).asBytes().length,
            new IsEqual<>(size)
        ).affirm();
    }

}
