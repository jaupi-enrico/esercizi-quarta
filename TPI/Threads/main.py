from threading import Semaphore, Thread
import time
import random

N = 10000
mutex = Semaphore(1)
posata_libera = [True] * N


def ci_sono_le_posate(j):
    return posata_libera[j] and posata_libera[(j + 1) % N]


def prende_posate(j):
    posata_libera[j] = False
    posata_libera[(j + 1) % N] = False


def deposita_posate(j):
    posata_libera[j] = True
    posata_libera[(j + 1) % N] = True


def filosofo(j):
    while True:
        print(f"Filosofo {j} sta pensando...")
        time.sleep(random.uniform(0.5, 1.5))

        mangiato = False
        while not mangiato:
            mutex.acquire()
            if ci_sono_le_posate(j):
                prende_posate(j)
                mutex.release()

                print(f"Filosofo {j} sta mangiando!")
                time.sleep(random.uniform(0.5, 1.5))

                mutex.acquire()
                deposita_posate(j)
                mutex.release()

                mangiato = True
            else:
                mutex.release()
                time.sleep(random.uniform(0.05, 0.15))


if __name__ == "__main__":
    threads = [Thread(target=filosofo, args=(i,), daemon=True) for i in range(N)]
    for t in threads:
        t.start()

    try:
        while True:
            time.sleep(0.1)
    except KeyboardInterrupt:
        print("\nStop.")