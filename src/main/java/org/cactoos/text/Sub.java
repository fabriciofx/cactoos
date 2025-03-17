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

import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.func.FuncOf;
import org.cactoos.scalar.Constant;

/**
 * Extract a substring from a Text.
 *
 * <p>There is no thread-safety guarantee.
 * @since 0.11
 */
public final class Sub extends TextEnvelope {

    /**
     * Ctor.
     * @param text The String
     * @param strt Start position in the text
     */
    public Sub(final CharSequence text, final int strt) {
        this(new TextOf(text), strt);
    }

    /**
     * Ctor.
     * @param text The String
     * @param strt Start position in the text
     * @param finish End position in the text
     */
    public Sub(final CharSequence text, final int strt, final int finish) {
        this(new TextOf(text), strt, finish);
    }

    /**
     * Ctor.
     * @param text The Text
     * @param strt Start position in the text
     */
    public Sub(final Text text, final int strt) {
        this(text, new Constant<>(strt));
    }

    /**
     * Ctor.
     * @param text The Text
     * @param strt Start position in the text
     */
    public Sub(final Text text, final Scalar<Integer> strt) {
        this(text, new FuncOf<>(strt));
    }

    /**
     * Ctor.
     * @param text The Text
     * @param strt Start position in the text
     */
    public Sub(final Text text, final Func<? super String, Integer> strt) {
        this(text, strt, String::length);
    }

    /**
     * Ctor.
     * @param text The Text
     * @param strt Start position in the text
     * @param finish End position in the text
     */
    public Sub(final Text text, final int strt, final int finish) {
        this(text, new Constant<>(strt), new Constant<>(finish));
    }

    /**
     * Ctor.
     * @param text The Text
     * @param strt Start position in the text
     * @param finish End position in the text
     */
    public Sub(final Text text, final int strt, final Scalar<Integer> finish) {
        this(text, new Constant<>(strt), finish);
    }

    /**
     * Ctor.
     * @param text The Text
     * @param strt Start position in the text
     * @param finish End position in the text
     */
    public Sub(final Text text, final int strt,
        final Func<? super String, Integer> finish) {
        this(text, new Constant<>(strt), finish);
    }

    /**
     * Ctor.
     * @param text The Text
     * @param strt Start position in the text
     * @param finish End position in the text
     */
    public Sub(final Text text, final Scalar<Integer> strt,
        final Scalar<Integer> finish) {
        this(text, new FuncOf<>(strt), new FuncOf<>(finish));
    }

    /**
     * Ctor.
     * @param text The Text
     * @param strt Start position in the text
     * @param finish End position in the text
     */
    public Sub(final Text text, final Scalar<Integer> strt,
        final Func<? super String, Integer> finish) {
        this(text, new FuncOf<>(strt), finish);
    }

    /**
     * Ctor.
     * @param text The Text
     * @param start Start position in the text
     * @param end End position in the text
     */
    public Sub(final Text text, final Func<? super String, Integer> start,
        final Func<? super String, Integer> end) {
        super(
            new Mapped(
                origin -> {
                    int begin = start.apply(origin);
                    if (begin < 0) {
                        begin = 0;
                    }
                    int finish = end.apply(origin);
                    if (origin.length() < finish) {
                        finish = origin.length();
                    }
                    return origin.substring(begin, finish);
                },
                text
            )
        );
    }
}
