import socket
import threading

HOST = '192.168.54.31'
PORT = 12345


client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)


def ricevi_messaggi():
    global client_socket

    buffer = ''

    try:
        while True:
            dati = client_socket.recv(1024)

            if not dati:
                break

            buffer += dati.decode()

            while '\n' in buffer:
                riga, buffer = buffer.split('\n', 1)
                riga = riga.strip()

                if riga:
                    print(riga)

    except:
        pass

    print('[SERVER] Connessione chiusa')


nome = input('Inserisci il tuo nome: ')

try:
    client_socket.connect((HOST, PORT))
    client_socket.sendall(f'NOME:{nome}\n'.encode())

    print(f'Connesso al server {HOST}:{PORT}')
    print("Scrivi un messaggio e premi Invio. Scrivi 'exit' per uscire.")

    thread_ricezione = threading.Thread(target=ricevi_messaggi, daemon=True)
    thread_ricezione.start()

    while True:
        messaggio = input()

        if messaggio.lower() == 'exit':
            client_socket.sendall('EXIT\n'.encode())
            break

        client_socket.sendall(f'MSG:{messaggio}\n'.encode())

except Exception as errore:
    print('Errore:', errore)

finally:
    client_socket.close()
    print('Connessione chiusa')
