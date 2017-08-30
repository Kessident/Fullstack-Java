let errMsgsDiv = document.getElementById("errMsgs");
let loginForm = document.getElementById("loginForm");

loginForm.addEventListener("submit", e => {
    e.preventDefault();
    let loginData = {
        email: loginForm.elements.email.value,
        password: loginForm.elements.password.value
    };

    let url = "/api/user/login";
    const loginUser = new Request(url, {
        method: 'POST',
        body: JSON.stringify(loginData),
        headers: new Headers({
            'Content-Type': 'application/json'
        })
    });

    fetch(loginUser).then(res => {
        errMsgsDiv.innerHTML = "";

        if (res.status === 200) {
            window.location.replace("/");
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