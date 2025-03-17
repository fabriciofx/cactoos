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
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.cactoos.Scalar;
import org.cactoos.scalar.Unchecked;

/**
 * Iterator that never ends.
 *
 * <p>If you need to repeat certain amount of time,
 * use {@link Repeated}.</p>
 *
 * @param <T> Element type
 * @since 0.4
 */
public final class Endless<T> implements Iterator<T> {

    /**
     * The element to repeat.
     */
    private final Unchecked<? extends T> origin;

    /**
     * Ctor.
     * @param element Element to repeat
     */
    public Endless(final T element) {
        this(() -> element);
    }

    /**
     * Ctor.
     * @param scalar Scalar to repeat
     */
    public Endless(final Scalar<? extends T> scalar) {
        this(new Unchecked<>(scalar));
    }

    /**
     * Ctor.
     * @param scalar Scalar to repeat
     */
    private Endless(final Unchecked<? extends T> scalar) {
        this.origin = scalar;
    }

    @Override
    public boolean hasNext() {
        return this.origin.value() != null;
    }

    @Override
    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException(
                "The iterator doesn't have item"
            );
        }
        return this.origin.value();
    }
}
