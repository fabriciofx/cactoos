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
package org.cactoos.iterable;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Iterator;
import org.cactoos.Fallback;
import org.cactoos.Scalar;
import org.cactoos.iterator.IteratorOf;
import org.cactoos.scalar.And;
import org.cactoos.scalar.Folded;
import org.cactoos.scalar.Or;
import org.cactoos.scalar.ScalarWithFallback;
import org.cactoos.scalar.Unchecked;
import org.cactoos.text.Joined;
import org.cactoos.text.UncheckedText;

/**
 * Array as iterable.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of item
 * @since 0.12
 */
public final class IterableOf<X> implements Iterable<X> {

    /**
     * The encapsulated iterator.
     */
    private final Scalar<? extends Iterator<? extends X>> itr;

    /**
     * Ctor.
     * @param items The array
     */
    @SafeVarargs
    public IterableOf(final X... items) {
        this(() -> new IteratorOf<>(items));
    }

    /**
     * Ctor.
     * @param list The list
     * @since 0.21
     */
    public IterableOf(final Iterator<? extends X> list) {
        this(() -> list);
    }

    /**
     * Ctor.
     * @param sclr The encapsulated iterator of x
     */
    public IterableOf(final Scalar<? extends Iterator<? extends X>> sclr) {
        this.itr = sclr;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<X> iterator() {
        return (Iterator<X>) new Unchecked<>(this.itr).value();
    }

    @Override
    @SuppressFBWarnings("EQ_UNUSUAL")
    @SuppressWarnings("unchecked")
    public boolean equals(final Object other) {
        return new Unchecked<>(
            new Or(
                () -> other == this,
                new And(
                    () -> other != null,
                    () -> Iterable.class.isAssignableFrom(other.getClass()),
                    () -> {
                        final Iterable<X> compared = (Iterable<X>) other;
                        return new ScalarWithFallback<>(
                            new And(
                                (X value) -> true,
                                new Matched<>(
                                    this,
                                    compared
                                )
                            ),
                            new IterableOf<>(
                                new Fallback.From<>(
                                    IllegalStateException.class,
                                    ex -> false
                                )
                            )
                        ).value();
                    }
                )
            )
        ).value();
    }

    @Override
    public int hashCode() {
        return new Unchecked<>(
            new Folded<>(
                42,
                (hash, entry) -> 37 * hash + entry.hashCode(),
                this
            )
        ).value();
    }

    @Override
    public String toString() {
        return new UncheckedText(
            new Joined(
                ", ",
                new Mapped<>(
                    Object::toString,
                    this
                )
            )
        ).asString();
    }
}
