Types

All types have

Key | Value Type    | Description                      | Example
---|---|---|---
ID  | ```Integer``` | Unique ID of the entity returned | 1232231568
createdAt | ```String``` | Timestamp entity was created at | 2017-02-06T05:03:15
updatedAt | ```String``` | Timestamp entity was last updated at | 2017-02-06T05:03:15

##School

Key | Value Type | Description | Example
---|---|---|---
name | ```String``` | Name of School | MorehouseCollege 
location | ```Location``` | Representation of the physical location of the school | AtlantaGA 
listOfMajors | ```Array of Strings``` | List of all majors listed at School | {biology,math,etc…}

##Location

Key|Value Type|Description|Example
---|---|---|---
streetNum|```Integer```|Number of street|115
street|```String```|Name of street|M.L.K Jr Dr.
city|```String```|Name of city|Atlanta
state|```String```|Name of State|Georgia
zipCode|```Integer```|Postal Zip Code|30303
zipExp|```Integer```|Postal Zip Code Exp|8949
latitude|```Float```|Physical global location|33.75 (N is positive, S is negative; out of 60, so converts to 33N 45)
longitude|```Float```|Physical global location|-84.38 (E is positive, W is negative; Out of 60, so converts to 84W 23  

##Major

Key|Value Type|Description|Example
---|---|---|---
Name|```String```|Field of Study|Biology, Math
listOfBooks|```Array of Strings```|Books listed by major|{All Things Biology, Biochemistry, etc,..}

##User

Key|Value Type|Description|Example
---|---|---|---
name|```String```|Name of student|John Smith
email|```String```|Email used to log in/communicate|johnsmith@gmail.com
password| |Password used to log in (Never returned)| |
passSalt| |Pass salt used for hashing (never returned)| |
major|```String```|String representation of major|Biology, Political Science
schoolID|Integer|Unique identifier assigned to a school|3445566
isDeleted| |Flag for soft deletion if user decides to delete account (Never returned)| |
booksOwned|```Array of Books```|List of books user has|
booksForSale|```Array of Books```|List of Books user has and is willing to sell|
lastLogIn| |Time used to determine if a user is ‘inactive’ (Never returned)| | 



##Book

Key|Value Type|Description|Example
---|---|---|---
name|```String```|Name of Book|All Things Biology
author|```String```|Author of Book|Tremont Biggs
ISBN|```String```|Unique book identifier|978-3-16-148410-0
description|```String```|More detailed information about the book|The publisher is McGraw-Hill, used for course BIO101.
picture|```String```|Image of book|24234.png

##Listing

Key|Value Type|Description|Example
---|---|---|---
sellerID|```Integer```|ID of person selling|12
bookID|```Integer```|ID of book being sold|332
condition|```String```|Physical condition of the book|New, Light Use, Moderate Use, Heavy Use, Damaged

##Transaction

Key|Value Type|Description|Example
---|---|---|---
boughtFrom|```Integer```|ID of user who sold the book|4412
soldTo|```Integer```|ID of user who bought the book|4411
bookSold|```Book```|Book that was sold
amount|```Float```|Number of dollars book was sold for|90
dateSoldAt|```String```|String representation of the time book was sold at

##Message

Key|Value Type|Description|Example
---|---|---|---
sentFrom|```Integer```|UserId of user message was sent from|23
sentTo|```Integer```|UserId of user message was sent from|33
message|```String```|Message sent|Let's meet at the student center

##Request

Key|Value Type|Description|Example
---|---|---|---
userRequesting|```Integer|```User ID of user requesting|4
bookRequested|```Book```|Book requested|








#School Routes

Action|Method|Path
---|---|---
Get a list of all schools|GET|/api/school/all
Get a specific School|GET|/api/school/{ID}

###Get a list of all Schools
####Request
	GET /api/school/all
####Response
    HTTP/1.1 200 OK
    Content-Type: application/json
	{ “schools”:  [ School ]  }

###Get a School
####Request
	GET /api/school/{ID}
####Response
    HTTP/1.1 200 OK
    Content-Type: application/json
	{ “school”:  School  }


#Major Routes

Action|Method|Path
---|---|---
Get a list of all majors at a specific school|GET|/api/school/{ID}/major/all
Get a specific major at a specific school|GET|/api/school/{ID}/major/{majorID}

###Get a list of all majors at a specific school
####Request
    GET  /api/school/{ID}/major/all
####Response
    HTTP/1.1 200 OK
    Content-Type: application/json
	{ “majors”: [ Major ] }

###Get a specific major at a specific school
####Request
	GET /api/school/{ID}/major/{majorID}
####Response
	HTTP/1.1 200 OK
    Content-Type: application/json
	{ “major”: Major }


#User Routes

Action|Method|Path
---|---|---
Register a new user|POST|/api/user/register
Login a user|POST|/api/user/login
Update information for a user|PUT|/api/user/update
Delete a user|DELETE|/api/user/delete

###Register a new user
####Request
    POST  /api/user/register
    { “newUser”: User }
####Response
    HTTP/1.1 201 CREATED

###Login a user
####Request
	POST /api/user/login
####Response
	HTTP/1.1 200 OK

###Update information for a user
####Request
    PUT  /api/user/update
    { “updatedUser”:  User }
####Response
    HTTP/1.1 200 OK

###Delete a user
####Request
	DELETE /api/user/delete
###Response
    The server will respond with 204 No Content, and no body upon success.
	HTTP/1.1 204 No Content


#Book Routes

Action|Method|Path
---|---|---
Lookup Book by ISBN|GET|/api/book/search/{ISBN}
Lookup Book by other criteria|GET|/api/book/search

###Lookup book by ISBN
####Request
    GET  /api/book/search/{ISBN}
####Response
    HTTP/1.1 200 OK
    Content-Type: application/json
	{ “book” : [ Book ] }

    HTTP/1.1 204 No Content
	Sent if valid ISBN, but no book found

	HTTP/1.1 400 Bad Request
	Sent if invalid ISBN

###Lookup book by other criteria
###Request
	GET /api/book/search
	{ “name”: String, “Author”: String }
####Response
	HTTP/1.1 200 OK
    Content-Type: application/json
	{ “books”: [ Book ] }

	HTTP/1.1 203 No Content
	Sent when request was valid, but no book was found

	HTTP/1.1 400 Bad Request


#Listing Routes

List a book for sale|POST|/api/listing
---|---|---
Delete a listing|DELETE|/api/listing/{ID}
Edit a listing|PUT|/api/listing/{ID}

###List a book for sale
####Request
    POST  /api/listing
    { “bookID”: Integer, “amount”: Number, “condition”: String }
####Response
    HTTP/1.1 201 Created
    Content-Type: application/json
    { “listing”: Listing}

    HTTP/1.1 400 Bad Request
    Sent if user does not have book in collection

    HTTP/1.1 500 Internal Server Error

###Delete a Listing
####Request
    DELETE  /api/listing/{ID}
####Response
    HTTP/1.1 204 No Content
    Content-Type: application/json

    HTTP/1.1 400 Bad Request
    Sent if listing with that ID does not exist or if listing with that ID is not associated with logged in user

    HTTP/1.1 500 Internal Server Error

###Edit a Listing
####Request
    PUT  /api/listing/{ID}
    { “amount”: Number, “condition”: String}
####Response
    HTTP/1.1 200 OK
    Content-Type: application/json
    { “listing”: Listing}

    HTTP/1.1 400 Bad Request
    Sent if listing with that ID does not exist, if listing with that ID is not associated with logged in user, or if  missing information

    HTTP/1.1 500 Internal Server Error

#Transaction Routes

Action|Method|Path
---|---|---
Get a list of all transactions|GET|/api/transaction/all
Get a specific transactions|GET|/api/transaction/{ID}
Create a new transaction|POST|/api/transaction/create

###Get a list of all transactions
####Request
    GET  /api/transaction/all
####Response
    HTTP/1.1 200 OK
    Content-Type: application/json
	{ “transactions” : [ Transaction ] }

###Get a specific transaction
####Request
    GET /api/transaction/{ID}
####Response
	HTTP/1.1 200 OK
    Content-Type: application/json
	{ “transaction”: Transaction }

	HTTP/1.1 203 No Content

###Create a new transaction
####Request
    POST  /api/transaction/create
    { “seller” : userID, “buyer”: userID, “bookSold”: Book, “amountSoldFor”: Float (Dollar amount)}
####Response
    HTTP/1.1 201 CREATED
    Content-Type: application/json
    { “transaction”: Transaction}

    HTTP/1.1 400 Bad Request

    HTTP/1.1 500 Internal Server Error


#Message Routes

Action|Method|Path
---|---|---
Get a list of all messages sent to/from logged in user and {userID}|GET|/api/message/{userID}/all
Create a new message from signed in user to {userID}|POST|/api/message/{userID}/create

###Get a list of all messages sent to/from logged in user and {userID}
####Request
    GET  /api/message/{userID}/all
####Response
    HTTP/1.1 200 OK
    Content-Type: application/json
	{ “messages”: [ Message] }

	HTTP/1.1 204 No Content
	Sent when either no Messages or no user with {userID}

###Create a new message from signed in user to {userID}
####Request
	POST/api/school/{ID}/major/{majorID}
    { “message”: String }
####Response
	HTTP/1.1 201 Created
    Content-Type: application/json
	{ “message”: Message }

    HTTP/1.1 400 Bad Request

    HTTP/1.1 500 Internal Server Error


#Request Routes

Action|Method|Path
---|---|---
Get a list of all requests|GET|/api/request/all/
Get a specific request|GET|/api/request/{ID}
Get a list of all requests a logged in user made|GET|/api/request/all
Get a specific request a logged in user made|GET|/api/request/{ID}
Create a new request for a book from logged in user|POST|/api/request/create
Edit a request a logged in user made|PUT|/api/request/edit/{ID}
Delete a request a logged in user made|DELETE|/api/request/delete/{ID}

###Get a list of all requests
####Request
	GET /api/request/all/all
	{ "getFromAll": True}
####Response
	HTTP/1.1 200 OK
    Content-Type: application/json
	{ “requests”: [Request] }

	HTTP/1.1 203 No Content

###Get a specific request
####Request
    GET /api/request/all/{ID}
    { "getFromAll": True}
####Response
    HTTP/1.1 200 OK
    Content-Type: application/json
	{ “request” : Request }

    HTTP/1.1 204 No Content
    
	HTTP/1.1 400 Bad Request

###Get a list of all requests a logged in user made
####Request
    GET /api/request/all
	{ "getFromAll": False}
####Response
    HTTP/1.1 201 CREATED
    Content-Type: application/json
	{ “requests”: [Request] }

    HTTP/1.1 400 Bad Request

    HTTP/1.1 500 Internal Server Error

###Get a specific request a logged in user made
####Request
	GET /api/request/delete/{ID}
    { "getFromAll": False}
####Response
	HTTP/1.1 200 OK
    Content-Type: application/json
	{ “request”: Request }

	HTTP/1.1 204 No Content

###Create a new request for a book from logged in user
####Request
    POST /api/request/create
    { Book }
####Response
    HTTP/1.1 200 OK
    Content-Type: application/json
	{ “request” : [ Request] }

	HTTP/1.1 400 Bad Request

	HTTP/1.1 500 Internal Server Error

###Edit a request a logged in user made
####Request
    PUT /api/request/edit/{ID}
    { Book }
####Response
    HTTP/1.1 201 CREATED
    Content-Type: application/json
    { “request”: Request}

    HTTP/1.1 400 Bad Request

    HTTP/1.1 500 Internal Server Error

###Delete a request a logged in user made
####Request
	DELETE /api/request/delete/{ID}
####Response
	HTTP/1.1 204 No Content
	
	HTTP/1.1 400 Bad Request
	
	HTTP/1.1 500 Internal Server Error