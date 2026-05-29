const iscrizioni = [];

const form = document.getElementById('registrationForm');
const lista = document.getElementById('listaIscrizioni');
const sezione = document.getElementById('sezioneIscrizioni');
const separatore = document.getElementById('separatore');
const errore = document.getElementById('errore');

form.addEventListener('submit', function(e) {
  e.preventDefault();

  const nome = document.getElementById('nome').value.trim();
  const cognome = document.getElementById('cognome').value.trim();
  const email = document.getElementById('email').value.trim();
  const corso = document.getElementById('corso').value;
  const modalita = document.querySelector('input[name="modalita"]:checked');
  const newsletter = document.getElementById('newsletter').checked;

  if (!nome || !cognome || !email || !corso || !modalita) {
    errore.textContent = 'Compila tutti i campi obbligatori.';
    errore.style.display = 'block';
    return;
  }
  errore.style.display = 'none';

  const iscrizione = {
    id: Date.now(),
    nome,
    cognome,
    email,
    corso,
    modalita: modalita.value,
    newsletter
  };

  iscrizioni.push(iscrizione);

  const li = document.createElement('li');
  li.className = 'list-group-item d-flex justify-content-between align-items-start';
  li.setAttribute('data-id', iscrizione.id);

  li.innerHTML = `
    <div>
      <strong>${iscrizione.nome} ${iscrizione.cognome}</strong><br>
      Email: ${iscrizione.email}<br>
      Corso: ${iscrizione.corso}<br>
      Modalità: ${iscrizione.modalita}<br>
      Newsletter: ${iscrizione.newsletter ? 'Sì' : 'No'}
    </div>
    <button class="btn btn-sm btn-outline-danger ms-3" onclick="elimina(${iscrizione.id})">Elimina</button>
  `;

  lista.appendChild(li);
  sezione.style.display = 'block';
  separatore.style.display = 'block';

  form.reset();
});

function elimina(id) {
  const idx = iscrizioni.findIndex(i => i.id === id);
  if (idx !== -1) iscrizioni.splice(idx, 1);

  const li = lista.querySelector(`[data-id="${id}"]`);
  if (li) li.remove();

  if (iscrizioni.length === 0) {
    sezione.style.display = 'none';
    separatore.style.display = 'none';
  }
}

document.getElementById('btnEliminaTutti').addEventListener('click', function() {
  iscrizioni.length = 0;
  lista.innerHTML = '';
  sezione.style.display = 'none';
  separatore.style.display = 'none';
});