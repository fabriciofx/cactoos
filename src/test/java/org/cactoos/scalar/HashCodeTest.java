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

package org.cactoos.scalar;

import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Tests for {@link HashCode}.
 * @since 1.0
 */
final class HashCodeTest {
    /**
     * {@link HashCode} must compute the exact same hashCode as produced
     * by Joshua Block's "Item 9: Always override hashCode when you override
     * equals" in Effective Java, 2nd edition.
     */
    @Test
    void computeHashCode() {
        final int initial = 5;
        final int multiplier = 31;
        final Object[] attributes = {5, 31, "abc", 5, 50f, "xyz"};
        new Assertion<>(
            "Value must be equal to Josh Block's implementation of hashCode()",
            new HashCode(initial, multiplier, attributes),
            new HasValue<>(
                joshBloch(initial, multiplier, attributes)
            )
        ).affirm();
    }

    /**
     * {@link HashCode} must assume an {@code initial} values of 17 and a
     * {@code multiplier} value of 31 when these are not provided.
     */
    @Test
    void computeHashCodeWithDefaultValues() {
        final int initial = 17;
        final int multiplier = 31;
        final Object[] attributes = {494, 43, "test", 190, 298f, "joshua"};
        new Assertion<>(
            "Value must be equal to Josh Block's implementation of hashCode() with initial=17 and multiplier=31",
            new HashCode(attributes),
            new HasValue<>(
                joshBloch(initial, multiplier, attributes)
            )
        ).affirm();
    }

    /**
     * Joshua Bloch's implementation of hashCode() as per Effective Java,
     * 2nd Edition, Item 9: "Always override hashCode when you override equals".
     * @param initial Initial value
     * @param multiplier Step multiplier
     * @param attributes The object's attributes
     * @return Hashcode value
     */
    private static int joshBloch(
        final int initial, final int multiplier, final Object... attributes
    ) {
        int hash = initial;
        for (final Object attr : attributes) {
            hash = hash * multiplier + Objects.hashCode(attr);
        }
        return hash;
    }
}
