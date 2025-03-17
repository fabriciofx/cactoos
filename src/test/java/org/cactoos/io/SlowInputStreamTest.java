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

import org.cactoos.bytes.BytesOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test for {@link SlowInputStream}.
 *
 * @since 0.47
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class SlowInputStreamTest {

    @Test
    void readsSigned() throws Exception {
        new Assertion<>(
            "must correctly convert signed bytes to int",
            new SlowInputStream(
                new InputStreamOf(new BytesOf((byte) -100))
            ).read(),
            new IsEqual<>(156)
        ).affirm();
    }

    @Test
    void readsUnsigned() throws Exception {
        new Assertion<>(
            "must correctly convert unsigned bytes to int",
            new SlowInputStream(
                new InputStreamOf(new BytesOf((byte) 65))
            ).read(),
            new IsEqual<>(65)
        ).affirm();
    }
}
