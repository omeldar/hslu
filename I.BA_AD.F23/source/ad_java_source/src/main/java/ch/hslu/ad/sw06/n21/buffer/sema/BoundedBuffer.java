/*
 * Copyright 2023 Hochschule Luzern - Informatik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.hslu.ad.sw06.n21.buffer.sema;

import ch.hslu.ad.sw06.n21.buffer.Buffer;
import java.util.ArrayDeque;
import java.util.concurrent.Semaphore;

/**
 * Puffer nach dem First In First Out Prinzip mit einer begrenzten Kapazität.
 * Der Puffer ist thread sicher.
 *
 * @param <T> Elememente des Buffers
 */
public final class BoundedBuffer<T> implements Buffer<T> {

    private final ArrayDeque<T> queue;
    private final Semaphore putSema;
    private final Semaphore takeSema;

    /**
     * Erzeugt einen Puffer mit bestimmter Kapazität.
     *
     * @param n Kapazität des Puffers
     */
    public BoundedBuffer(final int n) {
        queue = new ArrayDeque<>(n);
        putSema = new Semaphore(n);
        takeSema = new Semaphore(0);
    }

    /**
     * Fügt ein Element in den Puffer ein, wenn dies möglich ist, wenn nicht
     * muss der Schreiber warten.
     *
     * @param item Element zum Einfügen.
     * @throws InterruptedException falls das Warten unterbrochen wird.
     */
    @Override
    public void add(final T item) throws InterruptedException {
        putSema.acquire();
        synchronized (queue) {
            queue.addFirst(item);
        }
        takeSema.release();
    }

    /**
     * Liest und entfernt ein Element aus dem Puffer, wenn dies möglich ist,
     * wenn nicht muss der Leser warten.
     *
     * @return gelesenes Element.
     * @throws InterruptedException falls das Warten unterbrochen wird.
     */
    @Override
    public T remove() throws InterruptedException {
        takeSema.acquire();
        T item;
        synchronized (queue) {
            item = queue.removeLast();
        }
        putSema.release();
        return item;
    }

    @Override
    public boolean empty() {
        return queue.isEmpty();
    }

    @Override
    public boolean full() {
        return putSema.availablePermits() == 0;
    }

    @Override
    public int size() {
        return queue.size();
    }
}
