window.addEventListener('resize', () => {
    const menu = document.getElementById('menuModal');
    if(window.innerWidth > 992){
        menu.style.transform = 'translateX(0rem)'
    } else if(window.innerWidth <= 992){
        menu.style.transform = 'translateX(-15rem)'
    }
})