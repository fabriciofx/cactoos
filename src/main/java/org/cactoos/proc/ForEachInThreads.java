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

import org.cactoos.Proc;
import org.cactoos.func.FuncOf;
import org.cactoos.scalar.AndInThreads;

/**
 * Executes a {@link Proc} in a new Thread for each element of an
 * {@link Iterable}
 *
 * <p>
 * This class can be effectively used to iterate through a collection, just like
 * {@link java.util.stream.Stream#forEach(java.util.function.Consumer)} works,
 * but with no guarantee on the output sorting:
 * </p>
 *
 * {@code
 * new ForEachInThreads(
 *    new ProcOf<>(input -> System.out.printf("\'%s\' ", input)),
 * ).execute(
 *    new IterableOf<>("Mary", "John", "William", "Napkin")
 * ); // Will print 'Mary' 'John' 'William' 'Napkin' to standard output.
 *    // Order of printing can be random.
 * }
 * <p>
 * There is no thread-safety guarantee.
 *
 * @param <X> The type to iterate over
 * @since 1.0
 */
public final class ForEachInThreads<X> implements Proc<Iterable<X>> {

    /**
     * The proc.
     */
    private final Proc<X> proc;

    /**
     * Ctor.
     *
     * @param proc The proc to execute
     */
    public ForEachInThreads(final Proc<X> proc) {
        this.proc = proc;
    }

    @Override
    public void exec(final Iterable<X> input) throws Exception {
        new AndInThreads(
            new FuncOf<>(this.proc, true), input
        ).value();
    }

}
