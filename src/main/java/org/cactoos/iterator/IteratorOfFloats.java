/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link Iterator} that returns the {@code float}s as {@link Float}s.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @since 0.32
 */
public final class IteratorOfFloats implements Iterator<Float> {
    /**
     * The list of items to iterate.
     */
    private final float[] items;

    /**
     * Current position.
     */
    private final AtomicInteger position;

    /**
     * Ctor.
     * @param items Items to iterate
     */
    @SuppressWarnings("PMD.ArrayIsStoredDirectly")
    public IteratorOfFloats(final float... items) {
        this.items = items;
        this.position = new AtomicInteger(0);
    }

    @Override
    public boolean hasNext() {
        return this.position.intValue() < this.items.length;
    }

    @Override
    public Float next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException(
                "The iterator doesn't have any more items"
            );
        }
        return this.items[this.position.getAndIncrement()];
    }
}
