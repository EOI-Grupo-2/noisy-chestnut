function selectTab(tab){
    const elements = document.getElementsByClassName()
    for (element of elements){
        element.style.visibility = element.id === tag ? 'visible' : 'hidden';
        element.style.opacity = element.id === tag ? '1' : '0';
    }

}