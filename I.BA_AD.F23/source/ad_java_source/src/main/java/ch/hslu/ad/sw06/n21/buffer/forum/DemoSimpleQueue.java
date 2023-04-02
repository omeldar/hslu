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
package ch.hslu.ad.sw06.n21.buffer.forum;

/**
 * Demonstration der SimpleQueue mit einem Producer und n Consumer.
 */
public final class DemoSimpleQueue {

    /**
     * Privater Konstruktor.
     */
    private DemoSimpleQueue() {
    }

    /**
     * Main-Demo.
     *
     * @param args not used.
     */
    public static void main(final String args[]) {
        final int n = 1000;
        final int nCons = 1;
        final boolean[] check = new boolean[n];
        final SimpleQueue queue = new SimpleQueue();
        new Thread(new Producer(queue, n), "Prod  ").start();
        for (int i = 0; i < nCons; i++) {
            new Thread(new Consumer(queue, check), "Cons " + (char) (i + 65)).start();
        }
    }
}
