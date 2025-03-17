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
package org.cactoos.number;

import java.math.BigDecimal;
import org.cactoos.Text;

/**
 * Text as {@link Number}.
 *
 * <pre>
 * long value = new NumberOf("186789235425346").longValue();
 * int value = new NumberOf("1867892354").intValue();
 * double value = new NumberOf("185.65156465123").doubleValue();
 * </pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public final class NumberOf extends NumberEnvelope {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = 1572355842425667751L;

    /**
     * Ctor.
     * @param txt Number-string
     */
    public NumberOf(final String txt) {
        this(new BigDecimal(txt));
    }

    /**
     * Ctor.
     * @param text Number-text
     */
    public NumberOf(final Text text) {
        this(new NumberOfScalars(() -> new BigDecimal(text.asString())));
    }

    /**
     * Ctor.
     * @param nbr Number
     */
    private NumberOf(final Number nbr) {
        super(nbr);
    }
}
