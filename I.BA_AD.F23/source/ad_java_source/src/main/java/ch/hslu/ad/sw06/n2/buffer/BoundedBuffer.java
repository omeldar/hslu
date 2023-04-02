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
package ch.hslu.ad.sw06.n2.buffer;

import java.util.ArrayDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Puffer nach dem First In First Out Prinzip mit einer begrenzten Kapazität.
 * Der Puffer ist thread sicher.
 *
 * @param <T> Elememente des Buffers
 */
public final class BoundedBuffer<T> implements Buffer<T> {

    private int capacity;
    private final ArrayDeque<T> queue;
    private final Semaphore putSema;
    private final Semaphore takeSema;

    /**
     * Erzeugt einen Puffer mit bestimmter Kapazität.
     *
     * @param n Kapazität des Puffers
     */
    public BoundedBuffer(final int n) {
        capacity = n;
        queue = new ArrayDeque<>(n);
        putSema = new Semaphore(n);
        takeSema = new Semaphore(0);
    }

    @Override
    public void add(final T elem) throws InterruptedException {
        putSema.acquire();
        synchronized (queue) {
            queue.addFirst(elem);
        }
        takeSema.release();
    }

    @Override
    public T remove() throws InterruptedException {
        takeSema.acquire();
        T elem;
        synchronized (queue) {
            elem = queue.removeLast();
        }
        putSema.release();
        return elem;
    }

    @Override
    public boolean add(T elem, long timeout) throws InterruptedException {
        if (elem == null) {
            throw new IllegalArgumentException("Element may not be null");
        }

        if (timeout <= 0) {
            throw new IllegalArgumentException("Timeout must be at least 1ms");
        }

        boolean canPutInTime = putSema.tryAcquire(timeout, TimeUnit.MILLISECONDS);

        if(!canPutInTime) {
            return false;
        }

        synchronized (queue) {
            queue.addFirst(elem);
        }

        takeSema.release();
        return true;
    }

    @Override
    public T remove(long timeout) throws InterruptedException {
        if (timeout <= 0) {
            throw new IllegalArgumentException("Timeout must be at least 1ms");
        }

        boolean canTakeInTime = takeSema.tryAcquire(timeout, TimeUnit.MILLISECONDS);

        if(!canTakeInTime) {
            return null;
        }

        T element;
        synchronized (queue) {
            element = queue.removeLast();
        }

        putSema.release();
        return element;
    }

    @Override
    public boolean empty() {
        return queue.isEmpty();
    }

    @Override
    public boolean full() {
        return queue.size() == capacity;
    }

    @Override
    public int size() {
        return queue.size();
    }
}
