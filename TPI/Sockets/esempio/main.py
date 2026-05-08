import socket


ADDRESS_FAMILY = socket.AF_INET
SOCKET_TYPE = socket.SOCK_STREAM
SERVER_HOST = '10.4.54.21'
SERVER_PORT = 5000

def invia(sock, messaggio):
    sock.sendall((messaggio + "\n").encode('utf-8'))

def ricevi(sock):
    dati = b""
    while not dati.endswith(b"\n"):
        chunk = sock.recv(1024)
        if not chunk:
            break
        dati += chunk
    return dati.decode('utf-8').strip()

print("\n=== CLIENT TCP QUIZ - Avvio ===")

client = socket.socket(ADDRESS_FAMILY, SOCKET_TYPE)
print(f"[1] Socket creato")

client.connect((SERVER_HOST, SERVER_PORT))
print(f"[2] Connesso a {SERVER_HOST}:{SERVER_PORT}\n")

benvenuto = ricevi(client)
print(f"[3] Read: '{benvenuto}'\n")

while True:
    messaggio = ricevi(client)
    if not messaggio:
        break

    print(f"[4a] Read: '{messaggio}'")

    if messaggio.startswith("DOMANDA"):
        risposta = input(">>> La tua risposta: ").strip()
        invia(client, risposta)
        print(f"[4b] Write: '{risposta}'")

    elif messaggio.startswith("QUIZ TERMINATO"):
        print()
        break

client.close()
print(f"[5] Connessione Client chiusa\n")
