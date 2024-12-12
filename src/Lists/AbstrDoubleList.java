package Lists;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class AbstrDoubleList<T> implements IAbstrDoubleList<T> {

    private class Element implements Serializable {

        Element prev;
        Element next;
        T data;

        public Element(Element prev, Element next, T data) {
            this.prev = prev;
            this.next = next;
            this.data = data;
        }

        public Element(T data) {
            this.data = data;
        }
    }

    private Element actualEl = null;
    private Element firstEl = null;
    private Element lastEl = null;
    private int rozmer = 0;

    private class IteratorImplement implements Iterator<T> {

        private Element el;
        private Element lastReturned;
        private boolean canRemove;
        private boolean StopIterace = true;

        public IteratorImplement() {
            el = firstEl;
        }

        @Override
        public boolean hasNext() {
            return el != null && (StopIterace || el != firstEl);
        }

        @Override
        public T next() {
            if (hasNext()) {
                lastReturned = el;
                el = el.next;
                canRemove = true;
                StopIterace = false;
                return lastReturned.data;
            }
            throw new NoSuchElementException("Seznam prazdny");
        }

        @Override
        public void remove() {
            if (!canRemove) {
                throw new IllegalStateException("neni mozne remove Elements");
            }

            if (lastReturned == firstEl) {
                firstEl = lastReturned.next;
                if (firstEl != null) {
                    firstEl.prev = null;
                }
            } else if (lastReturned == lastEl) {
                lastEl = lastReturned.prev;
                if (lastEl != null) {
                    lastEl.next = null;
                }
            } else {
                lastReturned.prev.next = lastReturned.next;
                lastReturned.next.prev = lastReturned.prev;
            }

            lastReturned = null;
            canRemove = false;
        }
    }

    @Override
    public void zrus() {
        firstEl = null;
        actualEl = null;
        lastEl = null;
        rozmer = 0;
    }

    @Override
    public boolean jePrazdny() {
        return rozmer == 0;
    }

    @Override
    public void vlozPrvni(T data) {
        Element el = new Element(data);
        if (jePrazdny()) {
            actualEl = el;
            firstEl = el;
            lastEl = el;
            firstEl.prev = lastEl;
            lastEl.next = firstEl;
            firstEl.next = lastEl;
            lastEl.prev = firstEl;
        } else {
            el.next = firstEl;
            el.prev = lastEl;
            firstEl.prev = el;
            lastEl.next = el;
            firstEl = el;
        }
        rozmer++;
    }

    @Override
    public void vlozPosledni(T data) {
        Element el = new Element(data);
        if (jePrazdny()) {
            actualEl = el;
            firstEl = el;
            lastEl = el;
            firstEl.prev = lastEl;
            lastEl.next = firstEl;
            firstEl.next = lastEl;
            lastEl.prev = firstEl;
        } else {
            el.next = firstEl;
            el.prev = lastEl;
            firstEl.prev = el;
            lastEl.next = el;
            lastEl = el;
        }
        rozmer++;
    }

    @Override
    public void vlozNaslednika(T data) {
        if (actualEl == lastEl) {
            vlozPosledni(data);
        } else {
            Element el = new Element(data);
            el.next = actualEl.next;
            actualEl.next.prev = el;
            actualEl.next = el;
            el.prev = actualEl;
            rozmer++;
        }
    }

    @Override
    public void vlozPredchudce(T data) {
        if (actualEl == firstEl) {
            vlozPrvni(data);
        } else {
            Element el = new Element(data);
            el.prev = actualEl.prev;
            actualEl.prev.next = el;
            actualEl.prev = el;
            el.next = actualEl;
            rozmer++;
        }
    }

    @Override
    public T zpristuniAktualni() {
        if (actualEl == null) {
            throw new NoSuchElementException("Aktuální prvek neexistuje.");
        }
        return actualEl.data;
    }

    @Override
    public T zpristupniPrvni() {
        if (firstEl == null) {
            throw new NoSuchElementException("Aktuální prvek neexistuje.");
        }
        actualEl = firstEl;
        return firstEl.data;
    }

    @Override
    public T zpristuniPosledni() {
        if (lastEl == null) {
            throw new NoSuchElementException("Aktuální prvek neexistuje.");
        }
        actualEl = lastEl;
        return lastEl.data;
    }

    @Override
    public T zpristupniNaslednika() {
        if (actualEl == null) {
            throw new NoSuchElementException("Aktuální prvek neexistuje.");
        }
        actualEl = actualEl.next;
        return actualEl.data;
    }

    @Override
    public T zpristuniPredchudce() {
        if (actualEl == null) {
            throw new NoSuchElementException("Aktuální prvek neexistuje.");
        }
        actualEl = actualEl.prev;
        return actualEl.data;
    }

    @Override
    public T odeberAktualni() {
        if (actualEl == null) {
            throw new NoSuchElementException("Aktuální prvek neexistuje.");
        }
        Element actualData = actualEl;
        if (actualEl == firstEl && actualEl == lastEl) {
            zrus();
            return actualData.data;

        } else if (actualEl == firstEl) {
            odeberPrvni();
        } else if (actualEl == lastEl) {
            odeberPosledni();
        } else {
            actualEl.prev.next = actualEl.next;
            actualEl.next.prev = actualEl.prev;
            rozmer--;
        }
        return actualData.data;
    }

    @Override
    public T odeberPrvni() {

        if (firstEl != null) {
            Element firstData = firstEl;
            if (firstData == actualEl) {
                actualEl = firstData.next;
            }
            if (firstEl == lastEl) {
                zrus();
            } else {
                firstEl = firstEl.next;
                firstEl.prev = lastEl;
                lastEl.next = firstEl;

            }
            rozmer--;
            return firstData.data;
        }
        throw new NoSuchElementException("Aktuální prvek neexistuje.");

    }

    @Override
    public T odeberPosledni() {
        if (lastEl != null) {
            Element lastData = lastEl;
            if (lastData == actualEl) {
                actualEl = lastData.prev;
            }
            if (firstEl == lastEl) {
                zrus();
            } else {
                lastEl = lastEl.prev;
                lastEl.next = firstEl;
                firstEl.prev = lastEl;
            }
            if (lastData == actualEl) {
                actualEl = lastEl;
            }
            rozmer--;
            return lastData.data;
        }
        throw new NoSuchElementException("Aktuální prvek neexistuje.");

    }

    @Override
    public T odeberNaslednika() {
        if (actualEl == null) {
            throw new NoSuchElementException("Aktuální prvek neexistuje.");
        }
        if (actualEl.next == firstEl) {
            return odeberPrvni();
        }
        Element childData = actualEl.next;
        actualEl.next = childData.next;
        childData.next.prev = actualEl;
        rozmer--;
        return childData.data;
    }

    @Override
    public T odeberPredchudce() {
       if (actualEl == null) {
            throw new NoSuchElementException("Aktuální prvek neexistuje.");
        }
        if (actualEl.prev == null) {
            throw new NoSuchElementException("Aktuální prvek neexistuje.");
        }
        Element oldData = actualEl.prev;
        actualEl.prev = oldData.prev;
        oldData.prev.next = actualEl;
        rozmer--;
        return oldData.data;
       
       
    }

    @Override
    public Iterator<T> iterator() {
        return new IteratorImplement();
    }

    public int rozmer() {
        return rozmer;
    }
}
