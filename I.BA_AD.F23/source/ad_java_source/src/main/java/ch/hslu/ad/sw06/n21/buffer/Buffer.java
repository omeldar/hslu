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
package ch.hslu.ad.sw06.n21.buffer;

/**
 * First-In-First-Out Buffer mit einer begrenzten Kapazität. Der Puffer ist
 * thread sicher.
 *
 * @param <T> Element Typ.
 */
public interface Buffer<T> {

    /**
     * Ein Element T speichern. Falls der Buffer voll ist, warten bis ein Platz
     * frei wird.
     *
     * @param elem zu speicherndes Element.
     * @throws InterruptedException wenn das Warten unterbrochen wird.
     */
    void add(final T elem) throws InterruptedException;

    /**
     * Liest und entfernt ein Element. Falls der Buffer leer ist, warten bis ein
     * Platz belegt wird.
     *
     * @return gelesenes Element.
     * @throws InterruptedException falls das Warten unterbrochen wird.
     */
    T remove() throws InterruptedException;

    /**
     * Gibt, ob der Buffer leer ist.
     *
     * @return true wenn der Puffer leer ist, sonst false.
     */
    boolean empty();

    /**
     * Gibt, ob der Buffer voll ist.
     *
     * @return true wenn der Puffer voll ist, sonst false.
     */
    boolean full();

    /**
     * Gibt die Anzahl im Buffer gespeicherten Elemente zurück.
     *
     * @return Anzahl Elemente.
     */
    int size();
}
