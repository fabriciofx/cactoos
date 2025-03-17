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
package org.cactoos.text;

import org.cactoos.Text;
import org.cactoos.scalar.ScalarOf;
import org.cactoos.scalar.Ternary;

/**
 * Returns a text that is after given boundary.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0
 */
public final class SuffixOf extends TextEnvelope {

    /**
     * Ctor.
     * @param text Text representing the text value
     * @param boundary String after which text will be split
     */
    public SuffixOf(final CharSequence text, final CharSequence boundary) {
        this(new TextOf(text), boundary);
    }

    /**
     * Ctor.
     * @param text Text representing the text value
     * @param boundary String after which text will be split
     */
    public SuffixOf(final Text text, final CharSequence boundary) {
        super(
            new Flattened(
                new Ternary<>(
                    new ScalarOf<>(() -> new Sticky(text)),
                    (Text t) -> t.asString().contains(boundary.toString()),
                    t -> new Sub(
                        t,
                        s -> s.indexOf(boundary.toString())
                            + boundary.length()
                    ),
                    t -> new TextOf("")
                )
            )
        );
    }
}
