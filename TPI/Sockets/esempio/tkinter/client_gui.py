import socket
import threading
import tkinter as tk
from tkinter import scrolledtext, simpledialog

HOST = '127.0.0.1'
PORT = 12345

client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

root = tk.Tk()
root.title("Chat TCP")

nome = simpledialog.askstring("Nome", "Inserisci il tuo nome:", parent=root)
if not nome:
    exit()

area_chat = scrolledtext.ScrolledText(root, state='disabled', width=60, height=20)
area_chat.pack(padx=10, pady=10)

frame_input = tk.Frame(root)
frame_input.pack(padx=10, pady=5)

entry_messaggio = tk.Entry(frame_input, width=45)
entry_messaggio.pack(side=tk.LEFT, padx=5)

def stampa_chat(testo):
    area_chat.config(state='normal')
    area_chat.insert(tk.END, testo + "\n")
    area_chat.config(state='disabled')
    area_chat.yview(tk.END)


def ricevi_messaggi():
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
                    root.after(0, stampa_chat, riga)

    except:
        pass

    root.after(0, stampa_chat, "[SERVER] Connessione chiusa")


def invia_messaggio(event=None):
    messaggio = entry_messaggio.get().strip()
    entry_messaggio.delete(0, tk.END)

    if not messaggio:
        return

    if messaggio.lower() == 'exit':
        client_socket.sendall('EXIT\n'.encode())
        root.destroy()
        return

    client_socket.sendall(f'MSG:{messaggio}\n'.encode())

btn_invia = tk.Button(frame_input, text="Invia", command=invia_messaggio)
btn_invia.pack(side=tk.LEFT)

entry_messaggio.bind("<Return>", invia_messaggio)


try:
    client_socket.connect((HOST, PORT))
    client_socket.sendall(f'NOME:{nome}\n'.encode())
    stampa_chat(f"Connesso al server {HOST}:{PORT} come {nome}")

    thread_ricezione = threading.Thread(target=ricevi_messaggi, daemon=True)
    thread_ricezione.start()

except Exception as errore:
    stampa_chat(f"Errore: {errore}")

root.mainloop()
client_socket.close()