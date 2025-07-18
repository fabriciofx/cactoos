/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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
package org.cactoos.iterator;

import java.util.NoSuchElementException;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test Case for {@link StackTraceIterator}.
 *
 * @since 0.56
 */
final class IteratorOfStackTraceTest {
    @Test
    void iteratorOfStackTraceTest() {
        final Throwable inner = new Throwable();
        final IteratorOfStackTrace iter =  new IteratorOfStackTrace(new Throwable(inner));
        new Assertion<>(
            "First call 'hasNext' should return true.",
            iter.hasNext(),
            new IsTrue()
        ).affirm();
        new Assertion<>(
            "First call 'next' should return inner exception.",
            iter.next(),
            new IsEqual<>(inner)
        ).affirm();
        new Assertion<>(
            "Second call 'hasNext' should return false.",
            iter.hasNext(),
            new IsEqual<>(false)
        ).affirm();
        new Assertion<>(
            "Third call 'next' should throw NSEE.",
            () -> iter.next(),
            new Throws<Throwable>(NoSuchElementException.class)
        ).affirm();
    }
}
