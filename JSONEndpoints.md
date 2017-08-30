# Types

## All types have

Key | Value Type | Description | Example
---|---|---|---
ID  | ```Integer``` | Unique ID of the entity returned | 1232231568
createdAt | ```String``` | Timestamp entity was created at | 2017-02-06T05:03:15
updatedAt | ```String``` | Timestamp entity was last updated at | 2017-02-06T05:03:15

## School

Key | Value Type | Description | Example
---|---|---|---
name | ```String``` | Name of School | MorehouseCollege 
location | ```Location``` | Representation of the physical location of the school | AtlantaGA 
majorsOffered | ```Array of Majors``` | List of all majors listed at School | {biology,math,etc…}

## Location

Key|Value Type|Description|Example
---|---|---|---
streetNum|```Integer```|Number of street|115
street|```String```|Name of street|M.L.K Jr Dr.
city|```String```|Name of city|Atlanta
state|```String```|Name of State|Georgia
zipCode|```Integer```|Postal Zip Code|30303
zipExp|```Integer```|Postal Zip Code Exp|8949
latitude|```Float```|Physical global location|33.75 (N is positive, S is negative; out of 60, converts to 33N 45)
longitude|```Float```|Physical global location|-84.38 (E is positive, W is negative; Out of 60, converts to 84W 23  

## Major

Key|Value Type|Description|Example
---|---|---|---
Name|```String```|Field of Study|Biology, Math

## User

Key|Value Type|Description|Example
---|---|---|---
name|```String```|Name of student|John Smith
email|```String```|Email used to log in/communicate|johnsmith@gmail.com
password| |Password used to log in (Never returned)| |
major|```Major```|String representation of major|Biology, Political Science
school|```School```|Unique identifier assigned to a school|3445566
isDeleted| |Flag for soft deletion if user decides to delete account (Never returned)| |
booksOwned|```Array of Books```|List of books user has|
booksForSale|```Array of Books```|List of Books user has and is willing to sell|

## Book

Key|Value Type|Description|Example
---|---|---|---
name|```String```|Name of Book|All Things Biology
author|```String```|Author of Book|Tremont Biggs
ISBN|```String```|Unique book identifier|978-3-16-148410-0
description|```String```|More detailed information about the book|The publisher is McGraw-Hill, used for course BIO101.
picture|```String```|base64 encoded string|
major | ```Major``` | Major book is used for

## Listing

Key|Value Type|Description|Example
---|---|---|---
offered|```Book```|Book listed for sale|
condition|```String```|Physical condition of the book|New, Light Use, Moderate Use, Heavy Use, Damaged
askingPrice|```Number```|Initial base asking price for the book(in USD)|80
picture | ```String``` | optional base64 encoded picture of the actual book for sale | 

## Transaction

Key|Value Type|Description|Example
---|---|---|---
seller|```User```|ID of user who sold the book|4412
buyer|```User```|ID of user who bought the book|4411
bookSold|```Book```|Book that was sold
amountSoldFor|```Long```|Number of dollars book was sold for|90

## Message

Key|Value Type|Description|Example
---|---|---|---
sentFrom|```Integer```|UserId of user message was sent from|23
sentTo|```Integer```|UserId of user message was sent from|33
message|```String```|Message sent|Let's meet at the student center

## Request

Key|Value Type|Description|Example
---|---|---|---
userRequesting|```User```|User requesting book|4
bookRequested|```Book```|Book requested|

## Rating
Key|Value Type|Description|Example
---|---|---|---
about | ```User``` | User the review is about |
issuer | ```User``` | User that issued the review | 
rating | ```Number``` | rating of `issuer`'s interaction with `about` |




# Routes
## All routes that return data have

Key | Value Type | Description | Example
---|---|---|---
message  | ```String``` | message | "success", "failure", "You must be logged in to do that"
data | ```Object``` | any data that is returned  |

## School Routes

Action|Method|Path
---|---|---
Get a list of all schools|GET|/api/school/all
Get a specific School|GET|/api/school/{ID}
Create a school | POST | /api/school/create

### Get a list of all Schools
#### Request
	GET /api/school/all
#### Response
    HTTP/1.1 200 OK
    Content-Type: application/json
	{ “data”:  [ School ]  }

### Get a School
#### Request
	GET /api/school/{ID}
#### Response
    HTTP/1.1 200 OK
    Content-Type: application/json
	{ “data”:  School  }
	
	HTTP/1.1 404 Not Found

### Create a school
#### Request
    POST /api/school/create
    { School }
#### Response
    HTTP/1.1 201 Created
    { "data": School }
    
## Major Routes

Action|Method|Path
---|---|---
Get a list of all majors |GET|/api/major/all
Get a specific major |GET|/api/major/{majorID}
Create a major | POST | /api/major/create

### Get a list of all majors
#### Request
    GET  /api/major/all
#### Response
    HTTP/1.1 200 OK
    Content-Type: application/json
	{ “data”: [ Major ] }

### Get a specific major
#### Request
	GET /api/major/{majorID}
#### Response
	HTTP/1.1 200 OK
    Content-Type: application/json
	{ “data”: Major }
	
### Create a major
#### Request
    POST /api/major/create
    { Major }
#### Response
    HTTP/1.1 201 Created

## User Routes

Action|Method|Path
---|---|---
Register a new user|POST|/api/user/register
Login a user|POST|/api/user/login
Update information for a user|PUT|/api/user/update
Delete a user|DELETE|/api/user/delete
Logout a user | GET | /api/user/logout

### Register a new user
#### Request
    POST  /api/user/register
    { “name”: String, "email": String, "password": String, "schoolID": Integer, "majorID": Integer  }
#### Response
    HTTP/1.1 201 Created
    
    HTTP/1.1 400 Bad Request

### Login a user
#### Request
	POST /api/user/login
	{ "email": String, "password": String}
#### Response
	HTTP/1.1 200 OK
	
	HTTP/1.1 401 UNAUTHORIZED

### Update information for a user
#### Request
    All parameters are optional, only ones wanting to be updated should be sent
    PUT  /api/user/update
    { “name”:  String, "schoolID": Integer, "majorID": Integer }
#### Response
    HTTP/1.1 200 OK
    
    HTTP/1.1 400 Bad Request

    HTTP/1.1 401 Unauthorized
    
### Delete a user
#### Request
	DELETE /api/user/delete
### Response
    The server will respond with 204 No Content, and no body upon success.
	HTTP/1.1 204 No Content
    
    HTTP/1.1 401 Unauthorized
    
### Logout
#### Request
    GET /api/user/logout
#### Response
    HTTP/1.1 200 OK


## Book Routes

Action|Method|Path
---|---|---
Get a list of all books owned by signed in user | GET | /api/book/all
Get a particular book from a user's collection | GET | /api/book/owned/{bookID}
Add a book to collection | POST | /api/book/owned/add
Delete an owned book | DELETE | /api/book/owned/{bookID}
Lookup Book by either ISBN or name|POST|/api/book/search

### Get a list of all books owned by signed in user | GET | /api/book/all
#### Request
    GET | /api/user/book/all
#### Response
    HTTP/1.1 200 OK
    Content-Type: application/json
    { “data” : [ Book ] }

    HTTP/1.1 404 No Content
    Sent if user has no books
    
    HTTP/1.1 401 Unauthorized

### Get a particular book from a user's collection
#### Request
    GET | /api/user/book/owned/{BookID}
#### Response
    HTTP/1.1 200 OK
    { "data": Book }
    
    HTTP/1.1 404 Not Found
    
    HTTP/1.1 401 Unauthorized

### Add a book to collection
#### Request
    POST | /api/user/book/add
    { "isbn" : String } || { "bookID": Integer }
#### Response
    HTTP/1.1 201 Created
    { "data": Book }
    
    HTTP/1.1 400 Bad Request
    
    HTTP/1.1 401 Unauthorized

### Delete an owned book
#### Request
    DELETE | /api/user/book/{BookID}/delete
#### Response
    HTTP/1.1 204 No Content
    
    HTTP/1.1 401 Unauthorized

### Lookup book by other criteria
#### Request
	POST /api/book/search
	{ “name”: String, “author”: String, "isbn": String, "bookID": Integer } parameters optional, isbn or bookID preferred
#### Response
	HTTP/1.1 200 OK
    Content-Type: application/json
	{ “data”: [ Book ] }
	
	HTTP/1.1 401 Unauthorized

## Listing Routes

Action|Method|Path
---|---|---
Get a list of all listings | GET | /api/listing/all
Get a specific listing | GET | /api/listing/{listingID}
List a book for sale | POST | /api/listing/create
Delete a listing | DELETE | /api/listing/{listingID}
Edit a listing | PUT | /api/listing/{listingID}

### Get a list of all listings
#### Request
    GET /api/listing/all
#### Response
    HTTP/1.1 200 OK
    { "data": [ Listing] }
    
### Get a specific listing
#### Request
    GET /api/listing/{listingID}
#### Response
    HTTP/1.1 200 OK
    { "data": Listing }
    
    HTTP/1.1 404 Not Found
    { "No listing with that ID found" }

### List a book for sale
#### Request
    POST  /api/listing
    { “bookID”: Integer, “amount”: Number, “condition”: String, "picture":String(base64 encoded) }
#### Response
    HTTP/1.1 201 Created
    Content-Type: application/json
    { “data”: Listing}

    HTTP/1.1 400 Bad Request
    Sent if user does not have book in collection

    HTTP/1.1 500 Internal Server Error

### Delete a Listing
#### Request
    DELETE  /api/listing/{listingID}
#### Response
    HTTP/1.1 204 No Content
    Content-Type: application/json

    HTTP/1.1 400 Bad Request
    Sent if listing with that ID does not exist or if listing with that ID is not associated with logged in user

    HTTP/1.1 500 Internal Server Error

### Edit a Listing
#### Request
    PUT  /api/listing/{listingID}
    { “amount”: Number, “condition”: String}
#### Response
    HTTP/1.1 200 OK
    Content-Type: application/json
    { “data”: Listing}

    HTTP/1.1 401 Unauthorized
    Sent if listing with that ID is not associated with logged in user or usr not logged in
    
    HTTP/1.1 404 Bad Request
    Sent if listing with that ID does not exist

    HTTP/1.1 500 Internal Server Error

## Transaction Routes

Action|Method|Path
---|---|---
Get a list of all transactions|GET|/api/transaction/all
Get a specific transactions|GET|/api/transaction/{ID}
Create a new transaction|POST|/api/transaction/create

### Get a list of all transactions
#### Request
    GET  /api/transaction/all
#### Response
    HTTP/1.1 200 OK
    Content-Type: application/json
	{ “data” : [ Transaction ] }

### Get a specific transaction
#### Request
    GET /api/transaction/{ID}
#### Response
	HTTP/1.1 200 OK
    Content-Type: application/json
	{ “data”: Transaction }

	HTTP/1.1 404 Not Found

### Create a new transaction
#### Request
    POST  /api/transaction/create
    { “sellerID” : userID, “buyerID”: userID, “bookSoldID”: BookID, “amountSoldFor”: Long (Dollar amount)}
#### Response
    HTTP/1.1 201 CREATED
    Content-Type: application/json
    { “data” : Transaction}

    HTTP/1.1 400 Bad Request

    HTTP/1.1 500 Internal Server Error


## Message Routes

Action|Method|Path
---|---|---
Get a list of all messages sent to/from logged in user and {userID}|GET|/api/message/{userID}/all
Create a new message from signed in user to {userID}|POST|/api/message/{userID}/create
Get a list of users signed in user has messaged | GET | /api/message/contacts

### Get a list of all messages sent to/from logged in user and {userID}
#### Request
    GET  /api/message/{userID}/all
#### Response
    HTTP/1.1 200 OK
    Content-Type: application/json
	{ “data”: [ Message] }

	HTTP/1.1 404 No Content
	Sent when either no Messages or no user with {userID}

### Create a new message from signed in user to {userID}
#### Request
	POST/api/message/{userID}/create
    { “message”: String }
#### Response
	HTTP/1.1 201 Created
    Content-Type: application/json
	{ “data”: Message }

    HTTP/1.1 400 Bad Request

    HTTP/1.1 500 Internal Server Error

### Get a list of users signed in user has messaged
#### Request
    GET /api/message/contacts
#### Response
    HTTP/1.1 200 OK
    Content-Type: application/json
    { “data”: [ User] }


## Request Routes

Action|Method|Path
---|---|---
Get a list of all requests|GET|/api/request/all/
Get a specific request|GET|/api/request/{requestID}
Get a list of all requests a logged in user made|GET|/api/request/mine/all
Get a specific request a logged in user made|GET|/api/request/mine/{requestID}
Create a new request for a book from logged in user|POST|/api/request/create
Delete a request a logged in user made|DELETE|/api/request/delete/{requestID}

### Get a list of all requests
#### Request
	GET /api/request/all/
#### Response
	HTTP/1.1 200 OK
    Content-Type: application/json
	{ “data”: [ Request ] }

	HTTP/1.1 404 Not Found

### Get a specific request
#### Request
    GET /api/request/all/{requestID}
#### Response
    HTTP/1.1 200 OK
    Content-Type: application/json
	{ “data” : Request }

    HTTP/1.1 404 Not Found

### Get a list of all requests a logged in user made
#### Request
    GET /api/request/mine
#### Response
    HTTP/1.1 201 CREATED
    Content-Type: application/json
	{ “data”: [Request] }

    HTTP/1.1 400 Bad Request

    HTTP/1.1 500 Internal Server Error

### Get a specific request a logged in user made
#### Request
	GET /api/request/mine/{requestID}
#### Response
	HTTP/1.1 200 OK
    Content-Type: application/json
	{ “data”: Request }

	HTTP/1.1 404 Not Found

### Create a new request for a book from logged in user
#### Request
    POST /api/request/create
    { "bookID": Integer } Makes a request for a book that already exists
    { "name": String, "author": String, "isbn": String, "majorID": Integer } Makes a request for a book that doesn't exist, and creates that book
#### Response
    HTTP/1.1 200 OK
    Content-Type: application/json
	{ “data” : [ Request] }

	HTTP/1.1 400 Bad Request

	HTTP/1.1 500 Internal Server Error

### Delete a request a logged in user made
#### Request
	DELETE /api/request/delete/{requestID}
#### Response
	HTTP/1.1 204 No Content
	
	HTTP/1.1 400 Bad Request
	
	HTTP/1.1 500 Internal Server Error