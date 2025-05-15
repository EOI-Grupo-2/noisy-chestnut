function openModal(elementId) {
    const element = document.getElementById(elementId);
    let isClose = element.style.visibility === 'hidden' || element.style.opacity === '0' || element.style.opacity === '' || element.style.visibility === '';
    element.style.visibility = isClose ? 'visible' : 'hidden';
    element.style.opacity = isClose ? '1' : '0';
}

