/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;
import org.cactoos.Text;

/**
 * Split a Text.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.3
 */
public final class SplitedText implements Text, Iterable<Text> {

    /**
     * The text.
     */
    private final Text origin;

    /**
     * The pattern separator.
     */
    private final String pattern;

    /**
     * The limit of pieces.
     */
    private final int limit;

    /**
     * Ctor.
     * @param text The text
     */
    public SplitedText(final Text text) {
        this(text, " ");
    }

    /**
     * Ctor.
     * @param text The text
     * @param pattern The pattern used to split
     */
    public SplitedText(final Text text, final String pattern) {
        this(text, pattern, Integer.MAX_VALUE);
    }

    /**
     * Ctor.
     * @param text The text
     * @param pattern The pattern used to split
     * @param limit The max limit of pieces
     */
    public SplitedText(final Text text, final String pattern, final int limit) {
        this.origin = text;
        this.pattern = pattern;
        this.limit = limit;
    }

    @Override
    public String asString() throws IOException {
        return this.origin.asString();
    }

    /**
     * Split the Text.
     * @return The pieces of Text
     */
    @Override
    public Iterator<Text> iterator() {
        try {
            return Arrays.stream(
                this.origin.asString()
                .split(this.pattern, this.limit)
            )
            .map(s -> new StringAsText(s))
            .collect(Collectors.<Text>toList())
            .iterator();
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

}
