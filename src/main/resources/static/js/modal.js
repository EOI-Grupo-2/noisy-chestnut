function openModal(elementId) {
    const chatModal = document.getElementById(elementId);
    let isClose = chatModal.style.visibility === 'hidden' || chatModal.style.opacity === '0';
    chatModal.style.visibility = isClose ? 'visible' : 'hidden';
    chatModal.style.opacity = isClose ? '1' : '0';
}

