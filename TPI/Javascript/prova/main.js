const form = document.getElementById("formIscrizione");
const lista = document.getElementById("listaIscrizioni");
const btnEliminaTutti = document.getElementById("eliminaTutti");

let iscrizioni = [];

form.addEventListener("submit", function(event) {
    event.preventDefault();

    const nome = document.getElementById("nome").value.trim();
    const cognome = document.getElementById("cognome").value.trim();
    const email = document.getElementById("email").value.trim();
    const corso = document.getElementById("corso").value;

    const modalita = document.querySelector(
        'input[name="modalita"]:checked'
    );

    const newsletter = document.getElementById("newsletter").checked;

    if (
        nome === "" ||
        cognome === "" ||
        email === "" ||
        corso === "" ||
        !modalita
    ) {
        alert("Compila tutti i campi obbligatori!");
        return;
    }

    // 4. Creare oggetto iscrizione
    const iscrizione = {
        nome: nome,
        cognome: cognome,
        email: email,
        corso: corso,
        modalita: modalita.value,
        newsletter: newsletter
    };

    // 5. Salvare nell'array
    iscrizioni.push(iscrizione);

    // 6. Mostrare elenco
    mostraIscrizioni();

    // Reset form
    form.reset();
});

function mostraIscrizioni() {

    lista.innerHTML = "";

    iscrizioni.forEach((iscrizione, indice) => {

        const li = document.createElement("li");

        // Interpolazione con backtick
        li.innerHTML = `
            <strong>${iscrizione.nome} ${iscrizione.cognome}</strong><br>
            Email: ${iscrizione.email}<br>
            Corso: ${iscrizione.corso}<br>
            Modalità: ${iscrizione.modalita}<br>
            Newsletter: ${iscrizione.newsletter ? "Sì" : "No"}
        `;

        // Pulsante elimina
        const btnElimina = document.createElement("button");
        btnElimina.textContent = "Elimina";

        btnElimina.addEventListener("click", function() {
            iscrizioni.splice(indice, 1);
            mostraIscrizioni();
        });

        li.appendChild(btnElimina);

        lista.appendChild(li);
    });
}

// Elimina tutti
btnEliminaTutti.addEventListener("click", function() {

    iscrizioni = [];
    mostraIscrizioni();
});