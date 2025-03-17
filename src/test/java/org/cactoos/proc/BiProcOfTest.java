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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Satisfies;

/**
 * Test case for {@link BiProcOf}.
 *
 * @since 0.50
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class BiProcOfTest {
    @Test
    void worksWithFunc() {
        final AtomicReference<Object> done = new AtomicReference<>();
        new Assertion<>(
            "Must execute BiProc with Func",
            new BiProcOf<>(
                input -> {
                    done.set(input);
                    return "discarded";
                }
            ),
            new Satisfies<>(
                proc -> {
                    final Object first = new Object();
                    final Object second = new Object();
                    proc.exec(first, second);
                    return done.get().equals(first);
                }
            )
        ).affirm();
    }

    @Test
    void worksWithBiFunc() {
        final AtomicReference<Object> done = new AtomicReference<>();
        new Assertion<>(
            "Must execute BiProc with BiFunc",
            new BiProcOf<>(
                (first, second) -> {
                    done.set(first);
                    return "discarded";
                }
            ),
            new Satisfies<>(
                proc -> {
                    final Object first = new Object();
                    final Object second = new Object();
                    proc.exec(first, second);
                    return done.get().equals(first);
                }
            )
        ).affirm();
    }

    @Test
    void worksWithProc() {
        final AtomicReference<Object> done = new AtomicReference<>();
        new Assertion<>(
            "Must execute BiProc with Proc",
            new BiProcOf<>(
                new ProcOf<>(
                    done::set
                )
            ),
            new Satisfies<>(
                proc -> {
                    final Object first = new Object();
                    final Object second = new Object();
                    proc.exec(first, second);
                    return done.get().equals(first);
                }
            )
        ).affirm();
    }

    @Test
    void worksWithBiProc() {
        final AtomicReference<Object> done = new AtomicReference<>();
        new Assertion<>(
            "Must execute BiProc with BiProc",
            new BiProcOf<>(
                (first, second) -> {
                    done.set(first);
                }
            ),
            new Satisfies<>(
                proc -> {
                    final Object first = new Object();
                    final Object second = new Object();
                    proc.exec(first, second);
                    return done.get().equals(first);
                }
            )
        ).affirm();
    }

    @Test
    void worksWithTwoProcs() {
        final AtomicInteger fst = new AtomicInteger();
        final AtomicInteger snd = new AtomicInteger();
        new Assertion<>(
            "Must execute BiProc with two Procs",
            new BiProcOf<>(
                AtomicInteger::incrementAndGet,
                AtomicInteger::incrementAndGet
            ),
            new Satisfies<>(
                proc -> {
                    proc.exec(fst, snd);
                    return fst.get() == 1 && snd.get() == 1;
                }
            )
        ).affirm();
    }
}
