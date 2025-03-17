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

import java.io.IOException;
import java.util.Arrays;
import org.cactoos.Text;
import org.cactoos.bytes.BytesOf;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.EndsWith;
import org.llorllale.cactoos.matchers.StartsWith;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link ResourceOf}.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class ResourceOfTest {

    @Test
    void readsBinaryResource() throws Exception {
        new Assertion<>(
            "Can't read bytes from a classpath resource",
            Arrays.copyOfRange(
                new BytesOf(
                    new ResourceOf(
                        "org/cactoos/io/ResourceOfTest.class"
                    )
                ).asBytes(),
                0,
                4
            ),
            new IsEqual<>(
                new byte[]{
                    (byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE,
                }
            )
        ).affirm();
    }

    @Test
    void readsTextResource() {
        new Assertion<>(
            "Must read a text resource from classpath",
            ResourceOfTest.large(),
            new EndsWith("est laborum.\n")
        ).affirm();
    }

    @Test
    void readsTextResourceThroughClassloader() {
        new Assertion<>(
            "Must read a text resource from classloader",
            ResourceOfTest.large(),
            new EndsWith(" laborum.\n")
        ).affirm();
    }

    @Test
    void readAbsentResourceTest() {
        new Assertion<>(
            "Can't replace an absent resource with a text",
            new TextOf(
                new BytesOf(
                    new ResourceOf(
                        "foo/this-resource-is-definitely-absent.txt",
                        "the replacement"
                    )
                )
            ),
            new EndsWith("replacement")
        ).affirm();
    }

    @Test
    void throwsWhenResourceIsAbsent() {
        new Assertion<>(
            "Doesn't fail for absent resource",
            () -> new TextOf(
                new ResourceOf(
                    "bar/this-resource-is-definitely-absent.txt"
                )
            ).asString(),
            new Throws<>(IOException.class)
        ).affirm();
    }

    @Test
    void acceptsTextAsResourceName() {
        new Assertion<>(
            "Can't accept Text as resource name",
            new TextOf(
                new ResourceOf(
                    new TextOf("org/cactoos/small-text.txt")
                )
            ),
            new EndsWith("ex ea commodo")
        ).affirm();
    }

    @Test
    void acceptsTextsAsResourceNameAndFallback() {
        new Assertion<>(
            "Can't use Texts as parameters",
            new TextOf(
                new ResourceOf(
                    new FormattedText("%s/absent.txt", "baz"),
                    new TextOf("another replacement")
                )
            ),
            new StartsWith("another")
        ).affirm();
    }

    /**
     * Large text resource.
     * @return The content of the large resource
     */
    private static Text large() {
        return new TextOf(
            new ResourceOf(
                "org/cactoos/large-text.txt",
                ResourceOfTest.class
            )
        );
    }

}
