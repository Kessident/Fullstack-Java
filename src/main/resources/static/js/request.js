let majorSelector = document.getElementById("majorSelector");
fetch("/api/major/all").then(response => {
    response.json().then(data => {
        let realData = data.data;

        realData.forEach(element => {
            let newOption = document.createElement("option");
            newOption.value = element.majorID;
            newOption.innerText = element.name;
            majorSelector.appendChild(newOption);
        });
    });
});