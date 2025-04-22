function selectTab(tab, className){
    const elements = document.getElementsByClassName(className)
    for (element of elements){
        element.style.visibility = element.id === tab ? 'visible' : 'hidden';
        element.style.opacity = element.id === tab ? '1' : '0';
    }
}