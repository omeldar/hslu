# Waitpool exercise

## 1.a

Code Ausgangslage:

```java
public static void main(final String args[]) throws InterruptedException {
    final MyTask waiter = new MyTask(LOCK);
    new Thread(waiter).start();
    Thread.sleep(1000);
    LOCK.notify();
}
```

```java
@Override
public void run() {
    LOG.info("warten...");
    synchronized (lock) {
        try {
            wait();
        } catch (InterruptedException ex) {
            /* Exception handling... */
            return;
        }
    }
    LOG.info("...aufgewacht");
}
```

Was passiert bei der Ausführung?

Bei der Ausführung dieses Codes, passiert folgendes:

```bat
Exception in thread "Thread-0" java.lang.IllegalMonitorStateException: current thread is not owner
	at java.base/java.lang.Object.wait(Native Method)
	at java.base/java.lang.Object.wait(Object.java:338)
	at ch.hslu.ad.exercise.n2.waitpool.MyTask.run(MyTask.java:42)

[INFO ] - warten...

Exception in thread "main" java.lang.IllegalMonitorStateException: current thread is not owner
	at java.base/java.lang.Object.notify(Native Method)
	at ch.hslu.ad.exercise.n2.waitpool.DemoWaitPool.main(DemoWaitPool.java:40)
```

### Was passiert bei der Ausführung und Verhalten der Klassen

In der unangepassten Version wird die `wait()` Methode in `MyTask` aufgerufen, ohne die Synchronisation durch das `lock`-Objekt.
Dies führt dazu, dass der Thread in `MyTask` nicht korrekt aufgeweckt wird und die Ausführung nicht wie erwartet fortgesetzt wird.

Zudem könnte im `main()` das `LOCK.notify()` aufgerufen werden, bevor in `MyTask` die `wait()`-Methode aufgerufen wird.
So kann es dazu kommen, dass `MyTask` ewig blockiert wird, da kein `notify()` mehr kommt.

### Anpassungen

Es sind 2 Anpassungen nötig, eine in der `main()`-Methode in `DemoWaitPool`. Dazu muss man das `LOCK.notify()` in ein `synchronized` packen:

```Java
synchronized (LOCK) {
    LOCK.notify();
}
```

Die andere Änderung muss in `MyTask` in der `run()`-Methode gemacht werden. Und zwar soll auf das LOCK gewartet werden, bis das freigegeben wird:

```Java
lock.wait();
```

### Andere Lösungsvarianten

Man könnte auch anstelle des `Thread.Sleep(1000)` mit einem `CountDownLatch` arbeiten. Dies würde wie folgt aussehen:

```Java
final CountDownLatch latch = new CountDownLatch(1);
final MyTask waiter = new MyTask(LOCK, latch);
new Thread(waiter).start();
latch.await(); // blockiert
synchronized (LOCK) {
    LOCK.notify();
}
```

```Java
public MyTask(final Object lock, final CountDownLatch latch) {
    this.lock = lock;
    this.latch = latch;
}

@Override
public void run() {
    LOG.info("warten...");
    synchronized (lock) {
        try {
            lock.wait();
            latch.countDown();
        } catch (InterruptedException ex) {
            /* Exception handling... */
            return;
        }
    }
    LOG.info("...aufgewacht");
}
```

Dabei blockiert das `latch.await();` den Hauptthread bis in `MyTask` das `latch.countDown();` aufgerufen wird.

## 1.b

