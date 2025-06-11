setTimeout(() =>{
    const element = document.getElementById("errorContainer")
    element.style.visibility = "hidden";
    element.style.opacity = "0";
    setTimeout(()=>{
        element.style.display = "none"
    },500)
},4000)