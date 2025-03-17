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

import org.cactoos.io.InputOf;
import org.cactoos.io.ResourceOf;
import org.cactoos.io.Sticky;
import org.cactoos.text.HexOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link Md5DigestOf}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class Md5DigestOfTest {

    @Test
    void checksumOfEmptyString() {
        new Assertion<>(
            "Can't calculate the empty string's MD5 checksum",
            new HexOf(
                new Md5DigestOf(
                    new InputOf("")
                )
            ),
            new HasString(
                "d41d8cd98f00b204e9800998ecf8427e"
            )
        ).affirm();
    }

    @Test
    void checksumOfString() {
        new Assertion<>(
            "Can't calculate the string's MD5 checksum",
            new HexOf(
                new Md5DigestOf(
                    new InputOf("Hello World!")
                )
            ),
            new HasString(
                "ed076287532e86365e841e92bfc50d8c"
            )
        ).affirm();
    }

    @Test
    void checksumFromFile() throws Exception {
        new Assertion<>(
            "Can't calculate the file's MD5 checksum",
            new HexOf(
                new Md5DigestOf(
                    new Sticky(
                        new InputOf(
                            new ResourceOf(
                                "org/cactoos/digest-calculation.txt"
                            ).stream()
                        )
                    )
                )
            ),
            new HasString(
                "162665ab3d58424724f83f28e7a147d6"
            )
        ).affirm();
    }

}
