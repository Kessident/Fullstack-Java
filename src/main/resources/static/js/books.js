let bookList = document.getElementById("listOfBooks");

let url = "/api/book/search";
let request = new Request(url, {
    method: "GET",
    headers: new Headers({
        'Content-Type': 'application/x-www-form-urlencoded'
    }),
    credentials: "same-origin"
});

fetch(request).then(res => {
    res.json().then(data => {
        let bookData = data.data;
        bookData.forEach(book => {

            let newP = document.createElement("p");
            newP.innerText += "Book name: " + book.name;
            newP.innerText += "\nBook author: " + book.author;
            newP.innerText += "\nBook ISBN: " + book.isbn;
            newP.innerText += "\nBook description: " + book.description;
            newP.innerText += "\nBook Major: " + book.major.name;
            newP.innerText += "\nBook Picture: " + book.picture;
            bookList.appendChild(newP);
        });
    });
});