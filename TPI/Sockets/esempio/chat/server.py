import socket
import threading

HOST = '127.0.0.1'
PORT = 12345

clients = []
lock = threading.Lock()


def invia_a_tutti(messaggio, mittente=None):
    with lock:
        copia_client = clients.copy()

    for client in copia_client:
        if client != mittente:
            try:
                client.sendall((messaggio + '\n').encode())
            except:
                pass


def gestisci_client(connessione, indirizzo):
    nome = 'Utente'
    buffer = ''

    try:
        primo_messaggio = connessione.recv(1024).decode().strip()

        if primo_messaggio.startswith('NOME:'):
            nome = primo_messaggio.split(':', 1)[1]
        else:
            connessione.sendall('Devi inviare prima il nome.\n'.encode())
            connessione.close()
            return

        print(f'{nome} connesso da {indirizzo}')
        invia_a_tutti(f'[SERVER] {nome} e\' entrato nella chat')

        while True:
            dati = connessione.recv(1024)

            if not dati:
                break

            buffer += dati.decode()

            while '\n' in buffer:
                riga, buffer = buffer.split('\n', 1)
                riga = riga.strip()

                if riga == '':
                    continue

                if riga.startswith('MSG:'):
                    testo = riga.split(':', 1)[1]
                    messaggio = f'{nome}: {testo}'
                    print(messaggio)
                    invia_a_tutti(messaggio, connessione)
                elif riga == 'EXIT':
                    return
                else:
                    connessione.sendall('Comando non valido. Usa MSG:testo\n'.encode())

    except Exception as errore:
        print(f'Errore con {indirizzo}: {errore}')

    finally:
        with lock:
            if connessione in clients:
                clients.remove(connessione)

        connessione.close()
        print(f'Connessione chiusa con {nome}')
        invia_a_tutti(f'[SERVER] {nome} ha lasciato la chat')


server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind((HOST, PORT))
server_socket.listen(5)

print(f'Server chat in ascolto su {HOST}:{PORT}')

while True:
    connessione, indirizzo = server_socket.accept()

    with lock:
        clients.append(connessione)

    thread_client = threading.Thread(
        target=gestisci_client,
        args=(connessione, indirizzo)
    )

    thread_client.start()
