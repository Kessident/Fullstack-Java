let errMsgsDiv = document.getElementById("errorMsgs");
let schoolSelector = document.getElementById("schoolSelector");
fetch("/api/school/all").then(response => {
    response.json().then(data => {

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
        let realData = data.data;

        realData.forEach(element => {
            let newOption = document.createElement("option");
            newOption.value = element.majorID;
            newOption.innerText = element.name;
            majorSelector.appendChild(newOption);
        });
    });
});

let form = document.getElementById("registrationForm");

form.addEventListener("submit", function (e) {
    e.preventDefault();
    let dataToSend = {
        name: form.elements.name.value,
        email: form.elements.email.value,
        password: form.elements.password.value,
        passwordConfirm: form.elements.passwordConfirm.value,
        majorID: form.elements.majorID.value,
        schoolID: form.elements.schoolID.value
    };

    let url = "/api/user/register";
    const registerNewUser = new Request(url, {
        method: 'POST',
        body: JSON.stringify(dataToSend),
        headers: new Headers({
            'Content-Type': 'application/json'
        })
    });

    fetch(registerNewUser).then(res => {
        errMsgsDiv.innerHTML = "";

        if (res.status === 200) {
            window.location.replace("/api/user/profile");
        } else {
            res.json().then(data => {
                let newErrP = document.createElement("p");
                newErrP.innerText = "Error: " + data.message;
                errMsgsDiv.appendChild(newErrP);

                if (data.data) {
                    data.data.forEach(item => {
                        let newErrP = document.createElement("p");
                        newErrP.innerText = "Error: " + item;
                        errMsgsDiv.appendChild(newErrP);
                    });
                }
            });
        }
    });
});