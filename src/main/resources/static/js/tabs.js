function selectTab(tab, className, tabsClass, tabId){
    const elements = document.getElementsByClassName(className)
    for (element of elements){
        element.style.visibility = element.id === tab ? 'visible' : 'hidden';
        element.style.opacity = element.id === tab ? '1' : '0';
    }
    makeActive(tabsClass,tabId);
}

function makeActive(tabsClass, tabId){
    const elements = document.getElementsByClassName(tabsClass);
    for(element of elements){
        if(element.id === tabId){
            element.classList.add('active');
        }else{
            element.classList.remove('active');
        }
    }
}