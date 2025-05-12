window.addEventListener('resize', () => {
    const menu = document.getElementById('menuModal');
    if(window.innerWidth > 992 && menu.style.visibility === 'hidden' && menu.style.opacity === '0'){
        openModal('menuModal');
    } else if(window.innerWidth <= 992 && menu.style.visibility === 'visible' && menu.style.opacity === '1'){
       openModal('menuModal')
    }
})