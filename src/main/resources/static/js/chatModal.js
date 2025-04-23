let isOpen = false

function openChatModal() {
    isOpen = !isOpen;
    const chatModal = document.getElementById('chatModal');
    chatModal.style.visibility = isOpen ? 'visible' : 'hidden';
    chatModal.style.opacity = isOpen ? '1' : '0';
}

