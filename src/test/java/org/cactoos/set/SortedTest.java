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
package org.cactoos.set;

import java.util.Comparator;
import org.cactoos.Text;
import org.cactoos.list.ListOf;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;
import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Sorted}.
 * @since 1.0.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals",
    "PMD.TooManyMethods",
    "PMD.JUnitTestsShouldIncludeAssert"})
final class SortedTest {

    @Test
    @SuppressWarnings("unchecked")
    void mustSortIntegerArrayAsSetInAscendingOrder() {
        new Assertion<>(
            "Must keep unique integer numbers sorted",
            new Sorted<Integer>(
                Integer::compareTo,
                2, 1, 3, 2, 1
            ),
            new IsIterableContainingInOrder<Integer>(
                new ListOf<>(
                    new IsEqual<>(1),
                    new IsEqual<>(2),
                    new IsEqual<>(3)
                )
            )
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void mustSortIntegerIterableAsSetInDescendingOrder() {
        new Assertion<>(
            "Must keep unique integer numbers sorted in descending order",
            new Sorted<Integer>(
                Comparator.reverseOrder(),
                2, 1, 3, 2, 1
            ),
            new IsIterableContainingInOrder<Integer>(
                new ListOf<>(
                    new IsEqual<>(3),
                    new IsEqual<>(2),
                    new IsEqual<>(1)
                )
            )
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void mustSortTextIterableAsSetUsingCustomCOmparator() {
        new Assertion<>(
            "Must keep unique integer numbers sorted in descending order",
            new Sorted<Text>(
                (first, second) -> {
                    final String left = new UncheckedText(first).asString();
                    final String right = new UncheckedText(second).asString();
                    return left.compareTo(right);
                },
                new TextOf("cd"),
                new TextOf("ab"),
                new TextOf("gh"),
                new TextOf("ef")
            ),
            new IsIterableContainingInOrder<Text>(
                new ListOf<>(
                    new IsText("ab"),
                    new IsText("cd"),
                    new IsText("ef"),
                    new IsText("gh")
                )
            )
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void mustNotBeEqualToSortedSet() {
        new Assertion<>(
            "Sorted set must not be equal to the tested collection",
            new Sorted<Integer>(
                Integer::compareTo,
                2, 1, 3, 2, 1
            ),
            new IsNot<>(
                new IsIterableContainingInOrder<Integer>(
                    new ListOf<>(
                        new IsEqual<>(1),
                        new IsEqual<>(3),
                        new IsEqual<>(2)
                    )
                )
            )
        ).affirm();
    }

    @Test
    void returnsCorrectComparator() {
        final Comparator<Integer> comparator = Integer::compareTo;
        new Assertion<>(
            "Comparator must be the same",
            new Sorted<>(comparator, 1, 2, 3).comparator(),
            new IsEqual<>(comparator)
        ).affirm();
    }

    @Test
    void returnsSubset() {
        new Assertion<>(
            "Must return sorted subset",
            new Sorted<>(Integer::compareTo, 3, 6, 1, 9, 3).subSet(3, 9),
            new IsIterableContainingInOrder<>(
                new ListOf<Matcher<? super Integer>>(
                    new IsEqual<>(3),
                    new IsEqual<>(6)
                )
            )
        ).affirm();
    }

    @Test
    void returnsHeadset() {
        new Assertion<>(
            "Must return sorted headset",
            new Sorted<>(Integer::compareTo, 3, 6, 1, 9, 3).headSet(9),
            new IsIterableContainingInOrder<>(
                new ListOf<Matcher<? super Integer>>(
                    new IsEqual<>(1),
                    new IsEqual<>(3),
                    new IsEqual<>(6)
                )
            )
        ).affirm();
    }

    @Test
    void returnsTailset() {
        new Assertion<>(
            "Must return sorted tailset",
            new Sorted<>(Integer::compareTo, 3, 6, 1, 9, 3).tailSet(6),
            new IsIterableContainingInOrder<>(
                new ListOf<Matcher<? super Integer>>(
                    new IsEqual<>(6),
                    new IsEqual<>(9)
                )
            )
        ).affirm();
    }

    @Test
    void returnsFirst() {
        new Assertion<>(
            "Must return first element",
            new Sorted<>(Integer::compareTo, 3, 6, 1, 9, 3).first(),
            new IsEqual<>(1)
        ).affirm();
    }

    @Test
    void returnsLast() {
        new Assertion<>(
            "Must return last element",
            new Sorted<>(Integer::compareTo, 3, 6, 1, 9, 3).last(),
            new IsEqual<>(9)
        ).affirm();
    }

    @Test
    void mustSortIntegersByNumberComparator() {
        new Assertion<>(
            "Must keep unique integer numbers sorted",
            new Sorted<Number>(
                Comparator.comparing(Number::intValue),
                2, 1, 3, 2, 1
            ),
            new IsIterableContainingInOrder<>(
                new ListOf<Matcher<? super Number>>(
                    new IsEqual<>(1),
                    new IsEqual<>(2),
                    new IsEqual<>(3)
                )
            )
        ).affirm();
    }

}
