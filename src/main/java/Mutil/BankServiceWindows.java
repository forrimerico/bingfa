package Mutil;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class BankServiceWindows {
    private final Sync sync;
    public BankServiceWindows(int count) {
        sync = new Sync(count);
    }
    private static class Sync extends AbstractQueuedSynchronizer {
        Sync(int count) {
            setState(count);
        }
        public int tryAcquireShared(int interval) {
            for (;;) {
                int current = getState();
                int newCount = current - 1;
                if (newCount < 0 || compareAndSetState(current, newCount)) {
                    return newCount;
                }
            }
        }
        public boolean tryReleaseShared(int interval) {
            for (;;) {
                int current = getState();
                int newCount = current + 1;
                if (compareAndSetState(current, newCount)) {
                    return true;
                }
            }
        }
    }


    public void handle() {
        sync.acquireShared(1);
    }


    public void unhandle() {
        sync.releaseShared(1);
    }


}