function myFunction() {
    var input = document.getElementById("userSearch");
    var filter = input.value.toUpperCase();
    var ul = document.getElementById("list");
    var li = ul.getElementsByTagName("li");
    for (var i = 0; i < li.length; i++) {
        var txtValue = li[i].textContent || li[i].innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";
        }
    }
}