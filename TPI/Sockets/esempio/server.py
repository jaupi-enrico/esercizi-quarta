import socket
import time
import threading

ADDRESS_FAMILY = socket.AF_INET
SOCKET_TYPE = socket.SOCK_STREAM
SERVER_HOST = '0.0.0.0'
SERVER_PORT = 5000


def invia(sock, messaggio):
    sock.send((messaggio + "\n").encode('utf-8'))
    time.sleep(0.05)


def ricevi(sock):
    dati = b""
    while not dati.endswith(b"\n"):
        chunk = sock.recv(1024)
        if not chunk:
            return None
        dati += chunk
    return dati.decode('utf-8').strip()


def calcola(espressione):
    try:
        return str(eval(espressione, {"__builtins__": None}, {}))
    except Exception:
        return "ERRORE: espressione non valida"


def gestisci_client(client_socket, client_address):
    print(f"[+] Thread avviato per {client_address}")

    try:
        invia(client_socket, "Benvenuto alla Calcolatrice TCP!")
        invia(client_socket, "Scrivi un'operazione (es: 5+3, 10/2, 7*8). Scrivi 'exit' per uscire.")

        while True:
            invia(client_socket, "Inserisci operazione:")
            operazione = ricevi(client_socket)

            if operazione is None:
                print(f"[-] {client_address} disconnesso")
                break

            operazione = operazione.lower().strip()
            print(f"[{client_address}] Operazione: {operazione}")

            if operazione == "exit":
                invia(client_socket, "Chiusura calcolatrice. Arrivederci!")
                break

            risultato = calcola(operazione)
            invia(client_socket, f"Risultato: {risultato}")

    except Exception as e:
        print(f"[ERRORE] {client_address}: {e}")

    finally:
        client_socket.close()
        print(f"[x] Connessione chiusa {client_address}")


print("\n=== SERVER TCP CALCOLATRICE MULTI-CLIENT ===")

server = socket.socket(ADDRESS_FAMILY, SOCKET_TYPE)
server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
server.bind((SERVER_HOST, SERVER_PORT))
server.listen(50)

print(f"[OK] In ascolto su {SERVER_HOST}:{SERVER_PORT}")

try:
    while True:
        client_socket, client_address = server.accept()
        print(f"[NEW] Connessione da {client_address}")

        thread = threading.Thread(
            target=gestisci_client,
            args=(client_socket, client_address),
            daemon=True
        )
        thread.start()

except KeyboardInterrupt:
    print("\n[!] Server interrotto")

finally:
    server.close()