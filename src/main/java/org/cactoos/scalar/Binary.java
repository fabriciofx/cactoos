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

import org.cactoos.Scalar;

/**
 * Binary operation.
 *
 * <p>There is no thread-safety guarantee.
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use the {@link Unchecked} decorator. Or you may use
 * {@link IoChecked} to wrap it in an IOException.</p>
 *
 * <pre>{@code
 * final AtomicInteger counter = new AtomicInteger();
 * new Binary(
 *     () -> true,
 *     () -> counter.incrementAndGet
 * ).value() // will be equal true
 * }</pre>
 *
 * @since 1.0
 */
public final class Binary implements Scalar<Boolean> {

    /**
     * Scalar for condition.
     */
    private final Scalar<Boolean> condition;

    /**
     * Proc executed when condition is true.
     */
    private final Runnable consequent;

    /**
     * Ctor.
     *
     * @param condition Boolean function
     * @param consequent Proc executed when condition is true
     */
    public Binary(
        final Scalar<Boolean> condition,
        final Runnable consequent
    ) {
        this.condition = condition;
        this.consequent = consequent;
    }

    @Override
    public Boolean value() throws Exception {
        final Boolean result = this.condition.value();
        if (result) {
            this.consequent.run();
        }
        return result;
    }
}
