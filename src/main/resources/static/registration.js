let schoolSelector = document.getElementById("schoolSelector");
fetch("/api/school/all").then(response => {
    response.json().then(data => {

console.log(data);
        let realData = data.data;

        realData.forEach(element => {
            let newOption = document.createElement("option");
            newOption.value = "" + element.schoolID;
            newOption.innerText = element.name;
            schoolSelector.appendChild(newOption);
        });
    });
});

let majorSelector = document.getElementById("majorSelector");
fetch("/api/major/all").then(response => {
    response.json().then(data => {
console.log(data);
        let realData = data.data;

        realData.forEach(element => {
            console.log(element);
            let newOption = document.createElement("option");
            newOption.value = element.majorID;
            newOption.innerText = element.name;
            majorSelector.appendChild(newOption);
        });
    });
});
