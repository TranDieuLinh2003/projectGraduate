const modal = document.querySelector('.modal');
const overlay = document.querySelector('.overlay');
const btnCloseModal = document.querySelector('.close-modal');
const btnOpenModal = document.querySelector('.show-modal');
console.log(btnOpenModal);

function openModal() {
  modal.classList.remove('hidden');
  overlay.classList.remove('hidden');
}

function closeModal() {
  modal.classList.add('hidden');
  overlay.classList.add('hidden');
}

// Clicking the 'Show modal' to open the modal
btnOpenModal.addEventListener('click', openModal);
// Clicking the 'X' button to close the modal
btnCloseModal.addEventListener('click', closeModal);
// Clicking the overlay to close the modal as well
overlay.addEventListener('click', closeModal);

// Pressing 'Esc' key to close the modal
document.addEventListener('keydown', function (e) {
  console.log(e.key);

  if (e.key === 'Escape' && !modal.classList.contains('hidden')) {
    closeModal();
  }
});

