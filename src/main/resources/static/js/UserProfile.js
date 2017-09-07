let userProfile = document.getElementById("userprofile");

let url = "/api/user/profile";
let request = new Request(url, {
    method: "GET",
    headers: new Headers({
        'Content-Type': 'application/x-www-form-urlencoded'
    }),
    credentials: "same-origin"
});

fetch(request).then(res => {
    res.json().then(data => {
        let userData = data.data;
        userData.forEach(user => {

            let newP = document.createElement("p");
            newP.innerText += "Name: " + user.name;
            newP.innerText += "\nEmail: " + user.email;
            newP.innerText += "\nSchool: " + user.school;
            userProfile.appendChild(newP);
        });
    });
});