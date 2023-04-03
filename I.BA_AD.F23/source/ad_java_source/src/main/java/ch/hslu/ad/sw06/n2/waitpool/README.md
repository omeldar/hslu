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

Ausser typo's durch das Deutsch, wird in der Klasse `DemoWaitPool.java` nur ein Warning ausgegeben:

```bat
C-style array declaration of parameter 'args'
```

## 1.c

Wenn wir alles in einen `synchronized`-Block stellen, sieht der Code wie folgt aus:

```Java
public static void main(final String args[]) throws InterruptedException {
    synchronized (LOCK) {
        final MyTask waiter = new MyTask(LOCK);
        new Thread(waiter).start();
        Thread.sleep(1000);
        LOCK.notify();
    }
}
```
Wir kommen in ein ewiges Warten hinein:

```bat
[INFO ] - warten...
```

Wir blockieren so das `LOCK`-Objekt für die Dauer der gesamten `main`-Methode. Auch zu der Zeit, in der der `waiter` Thread im `run` auf das `lock` wartet.

## 1.4 Reflektion

### Was ist bei der Benachrichtigung mit Hilfe der `notify`/`notifyAll` Methoden zu beachten?

1. `notify()` weckt nur einen Thread auf: Die `notify()` Methode weckt nur einen der wartenden Threads auf, die auf demselben Monitor (demselben Objekt) wie der aufrufende Thread warten. Es ist nicht garantiert, welcher der wartenden Threads aufgeweckt wird, und es ist möglich, dass keiner aufgeweckt wird.

2. `notifyAll()` weckt alle wartenden Threads auf: Im Gegensatz dazu weckt die `notifyAll()` Methode alle wartenden Threads auf, die auf demselben Monitor warten. Dadurch wird sichergestellt, dass alle Threads, die auf das Signal warten, aufgeweckt werden.

3. Das notify-Signal geht nicht verloren: Wenn `notify()` aufgerufen wird, bevor ein Thread auf das Signal wartet, geht das Signal verloren.

4. Der notify-Aufruf sollte innerhalb eines synchronisierten Blocks erfolgen: Damit der `notify()` Aufruf ordnungsgemäß funktioniert, muss er innerhalb eines synchronisierten Blocks auf demselben Monitor wie die wartenden Threads aufgerufen werden.

5. Der wartende Thread muss die Sperre freigeben: Wenn der Thread die `wait()` Methode aufruft, gibt er automatisch die Sperre für den Monitor frei, auf dem er wartet. Beim Aufwachen muss er jedoch erneut versuchen, die Sperre zu erlangen, bevor er fortfahren kann. Daher muss der Code im synchronisierten Block ausgeführt werden, um zu verhindern, dass der Thread auf eine Sperre wartet, die ein anderer Thread hält.

### Warum wird für die Benachrichtigung `notifyAll` empfohlen, statt `notify`?

Es wird empfohlen, notifyAll anstelle von notify zu verwenden, um sicherzustellen, dass alle wartenden Threads benachrichtigt werden, wenn eine Bedingung erfüllt ist. Bei `notify` wird ein zufälliger Thread benachrichtigt, anstelle aller Threads. Dadurch kann es zu Deadlocks oder anderen Fehlern kommen. Die anderen Threads bleiben dann in der Warteschlange und warten weiter auf die Benachrichtigung.

### Was ist zu berücksichten, wenn man für die Benachrichtigung `notifyAll` verwendet?

1. Man soll es nur verwenden, wenn wirklich alle wartende Threads benachrichtigt werden sollen. Wenn nur ein Thread eine bestimmte Aufgabe ausführen kann, reicht es aus, nur diesen Thread zu benachrichtigen.

2. Man muss sicherstellen, dass alle wartenden Threads bereit sind, die nächste Aufgabe wahrzunehmen. Wenn ein wartender Thread durch `notifyAll` aufgeweckt wird, muss er bereit sein für die Aufgabe. Ansonsten könnte er wieder in den Wartezustand zurückkehren, ohne dass ein weiteres `notifyAll` kommt.

3. In Schleifen soll man `wait` verwenden. Wenn ein `notifyAll` gemacht wird, kann es vorkommem, dass ein Threads fälschlicherweise aus dem Wartezustand geweckt wird. Mit dem `wait` in der Schleife wird dies erneut überprüft. 

## `synchronized` und Monitore in Java

Der synchronized Block ist ein Mechanismus in Java, mit dem Sie den Zugriff auf kritische Abschnitte des Codes synchronisieren können, um sicherzustellen, dass nur ein Thread zur gleichen Zeit darauf zugreift. Wenn Sie eine Methode oder einen Codeblock mit dem synchronized Schlüsselwort kennzeichnen, wird nur ein Thread gleichzeitig Zugriff darauf haben. Alle anderen Threads, die gleichzeitig darauf zugreifen möchten, müssen warten, bis der erste Thread fertig ist und die Sperre freigibt.

Der synchronized Block funktioniert durch die Verwendung von Monitoren, die in Java in jedem Objekt enthalten sind. Wenn ein Thread versucht, auf einen synchronisierten Block zuzugreifen, muss er zuerst den Monitor sperren, bevor er den Code ausführen kann. Wenn ein anderer Thread gleichzeitig versucht, auf denselben synchronisierten Block zuzugreifen, muss er warten, bis der erste Thread den Monitor freigibt.

In der Praxis wird der synchronized Block oft verwendet, um die gemeinsame Nutzung von Ressourcen durch mehrere Threads zu synchronisieren und kritische Abschnitte des Codes zu schützen. Dies hilft, Probleme wie Race Conditions oder Inkonsistenzen bei der Verwendung gemeinsamer Datenstrukturen zu vermeiden.
