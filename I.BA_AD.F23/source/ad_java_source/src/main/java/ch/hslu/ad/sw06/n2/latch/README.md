# Pferderennen

Ein Latch ist ein Mechanismus, welcher uns erlaubt eine Art "Ampel" zu nutzen. Dabei warten Threads darauf, bis ein anderer Thread die Ampel auf Grün setzt, um mit ihren nächstne Aufgaben fortzufahren.

Während diese auf das Grüne Licht warten, kann jedoch auch die Aktion im anderen Thread abgebrochen werden. Folgende Implementation hat ein Latch zum Beispiel:

## 2.a und 2.b

```Java
private boolean open = false;
private boolean aborted = false;

@Override
public synchronized void acquire() throws InterruptedException {
    while (!open) {
        if (aborted) {
            throw new InterruptedException();
        }
        this.wait();
    }
}

@Override
public synchronized void release() {
    this.open = true;
    this.notifyAll();
}

@Override
public synchronized void abort() {
    this.aborted = true;
    this.notifyAll();
}
```

## 2.c

Es wird fälschlicherweile gelogged, dass das Pferd im Ziel ist, da das ausserhalb des `try-catch`-Blocks ist. Aber es werden auch die Exception logs ausgeführt. Somit funktioniert es.

```Java
public static void main(final String[] args) {
    final Synch starterBox = new Latch();
    Thread thread;
    for (int i = 1; i < 6; i++) {
        thread = new Thread(new RaceHorse(starterBox), "Horse " + i);
        thread.start();
    }
    LOG.info("Start...");
    starterBox.abort(); // Hier abort anstelle release
}
```

## 2.4 Reflektion

### Ist das Rennen wirklich gerecht? Begründen Sie Ihre Antwort.

Nein. Es wird nicht garantiert, dass jeder Thread die selbe Startzeit nach dem `release` erhält. 

Wenn ein Latch released wird, werden alle Threads benachrichtigt und sind dazu berechtigt nächste Schritte auszuführen. Jedoch ist es die Entscheidung des Scheduler, welcher Thread zuerst dran kommt. Der Scheduler kann anhand folgenden Faktoren entscheiden, welche Threads priorisiert werden:

1. Thread priority
2. Fairness Policies
3. Andere System spezifische Faktoren

Es ist also nicht wirklich 100% fair. Jedoch ist der Latch-Mechanismus trotzdem eine gute Variante für einen ungefähr gleichzeitigen Start von Thread-Aktionen.

### Falls Ihre Antwort Nein ist – wie könnte man es gerechter machen?

Man könnte `CycleBarrier` nutzen anstelle eines `Latch`. Eine `CycleBarrier` wartet darauf, bis alle Threads eine Startlinie (barrier) erreicht haben und released dann alle gleichzeitig. Dies stellt sicher, dass alle Thread auch gleichzeitig gestartet werden.

### Was folgern Sie aus dem obigen Überlegungen?

Für ein Rennen, würde ich eher eine CycleBarrier verwenden anstelle des Latch-Mechanismus.
