function searchInput(listId, inputId) {
    const input = document.getElementById(inputId);
    const search = input.value.toLowerCase();
    const ul = document.getElementById(listId);
    const li = ul.getElementsByTagName("li");
    for (let i = 0; i < li.length; i++) {
        let item = li[i];
        if (item.id.toLowerCase().includes(search)) {
            item.style.display = "";
        } else {
            item.style.display = "none";
        }
    }
}