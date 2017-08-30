// let bookList = document.getElementById("listOfBooks");

// let url = "/api/book/search";
// let request = new Request(url, {
//     method:"POST"
// });
//
// fetch(request).then(res => {
//     console.log(res);
//
//     res.json().then(data => {
//         console.log(data);
//
//         let bookData = data.data;
//         bookData.forEach(book => {
//             console.log(book);
//
//             let newP = document.createElement("p");
//             newP.innerText += "Book name: " + book.name;
//             newP.innerText += "\nBook author: " + book.author;
//             newP.innerText += "\nBook ISBN: " + book.isbn;
//             newP.innerText += "\nBook description: " + book.description;
//             bookList.appendChild(newP);
//         });
//     });
// });