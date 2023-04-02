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
package ch.hslu.ad.sw06.n21.buffer.guardedblocks;

import ch.hslu.ad.sw06.n21.buffer.Buffer;

/**
 * Buffer (First In First Out) mit einer begrenzten Kapazität. Der Puffer ist
 * thread sicher.
 *
 * @param <T> Element Typ.
 */
public final class BoundedBuffer<T> implements Buffer<T>{

    private final T[] data;
    private int head;
    private int tail;
    private int count;

    /**
     * Erzeugt einen Buffer mit bestimmter Kapazität.
     *
     * @param cap Kapazität des Buffers.
     */
    @SuppressWarnings("unchecked")
    public BoundedBuffer(int cap) {
        this.data = (T[]) new Object[cap];
        this.head = 0;
        this.tail = 0;
        this.count = 0;
    }

    /**
     * Ein Element T speichern. Falls der Buffer voll ist, warten bis ein Platz
     * frei wird.
     *
     * @param item zu speicherndes Element.
     * @throws InterruptedException wenn das Warten unterbrochen wird.
     */
    @Override
    public synchronized void add(final T item) throws InterruptedException {
        while (count == data.length) {
            this.wait();
        }
        count++;
        data[tail] = item;
        tail = (tail + 1) % data.length;
        if (count == 1) {
            this.notifyAll();
        }
    }

    /**
     * Ein Element T auslesen. Falls der Buffer leer ist, warten bis ein Platz
     * belegt ist.
     *
     * @return ausgelesenes Element.
     * @throws InterruptedException wenn das Warten unterbrochen wird.
     */
    @Override
    public synchronized T remove() throws InterruptedException {
        while (count == 0) {
            this.wait();
        }
        count--;
        T item = data[head];
        data[head] = null;
        head = (head + 1) % data.length;
        if (count == data.length - 1) {
            this.notifyAll();
        }
        return item;
    }

    /**
     * Ein Element T speichern oder nach einem Timeout abbrechen. Falls der
     * Buffer voll ist, warten bis ein Platz frei wird.
     *
     * @param item zu speicherndes Element.
     * @param millis Timeout bis zum Abbruch.
     * @return true, wenn Element gespeichert wurde, false, wenn Timeout
     * eingetreten ist.
     * @throws InterruptedException wenn das Warten unterbrochen wird.
     */
    public synchronized boolean offer(final T item, final long millis) throws InterruptedException {
        while (count == data.length) {
            this.wait(millis);
            if (count == data.length) {
                return false;
            }
        }
        count++;
        data[tail] = item;
        tail = (tail + 1) % data.length;
        if (count == 1) {
            this.notifyAll();
        }
        return true;
    }

    /**
     * Gibt, ob der Buffer leer ist.
     *
     * @return true wenn der Puffer leer ist, sonst false.
     */
    @Override
    public boolean empty() {
        return count==0;
    }

    /**
     * Gibt, ob der Buffer voll ist.
     *
     * @return true wenn der Puffer voll ist, sonst false.
     */
    @Override
    public boolean full() {
        return count==data.length;
    }

    /**
     * Gibt die Anzahl im Buffer gespeicherten Elemente zurück.
     *
     * @return Anzahl Elemente.
     */
    @Override
    public int size() {
        return count;
    }
}
