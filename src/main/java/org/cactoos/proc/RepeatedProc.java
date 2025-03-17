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
package org.cactoos.proc;

import org.cactoos.Func;
import org.cactoos.Proc;
import org.cactoos.func.FuncOf;
import org.cactoos.func.Repeated;

/**
 * Proc that runs repeatedly for a number of times.
 *
 * @param <X> Type of input
 * @since 0.49.2
 */
public final class RepeatedProc<X> implements Proc<X> {
    /**
     * The repeated func.
     */
    private final Func<? super X, Boolean> func;

    /**
     * Ctor.
     *
     * <p>If {@code count} is equal or less than zero {@link #exec(Object)}
     * will return an exception.</p>
     *
     * @param prc Proc to repeat.
     * @param count How many times.
     */
    public RepeatedProc(final Proc<? super X> prc, final int count) {
        this.func = new Repeated<>(
            new FuncOf<>(prc, true),
            count
        );
    }

    @Override
    public void exec(final X input) throws Exception {
        this.func.apply(input);
    }
}
