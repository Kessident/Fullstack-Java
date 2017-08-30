package com.CCGA.api.Controllers;

import com.CCGA.api.Models.*;
import com.CCGA.api.Repositorys.BookRepo;
import com.CCGA.api.Repositorys.ListingRepo;
import com.CCGA.api.Repositorys.UserRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.StringReader;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/listing")
public class ListingController {

    private ListingRepo listings;
    private BookRepo books;
    private UserRepo users;

    @Autowired
    public ListingController(ListingRepo listings, BookRepo books, UserRepo users) {
        this.listings = listings;
        this.books = books;
        this.users = users;
    }

    @GetMapping("/all")
    public ResponseEntity getAllBooksForSale(HttpSession session) {
        if (session.getAttribute("userID") != null) {
            try {
                Iterable<Listing> bookList = listings.findAll();
                return ResponseEntity.status(OK).body(new JSONResponse("Success", bookList));
            } catch (Exception e) {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(UNAUTHORIZED).body(new JSONResponse("You must be logged in to do that", null));
        }
    }

    @GetMapping("/{listingID}")
    public ResponseEntity getASpecificListing(@PathVariable int listingID, HttpSession session) {
        if (session.getAttribute("userID") != null) {
            Listing found = listings.findOne(listingID);
            if (found == null) {
                return ResponseEntity.status(NOT_FOUND).body(new JSONResponse("No listing with that ID found", null));
            } else {
                return ResponseEntity.status(OK).body(new JSONResponse("Success", found));
            }
        } else {
            return ResponseEntity.status(UNAUTHORIZED).body(new JSONResponse("You must be logged in to do that", null));
        }
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity createAListing(@RequestBody String ListingAsString, HttpSession session) {
        if (session.getAttribute("userID") != null) {
            JsonNode json;
            try {
                json = processJSON(ListingAsString);
                if (json.get("bookID") == null || json.get("amount") == null || json.get("condition") == null) {
                    return ResponseEntity.status(BAD_REQUEST).body(new JSONResponse("Please provided all required fields", null));
                }
            } catch (Exception e) {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new JSONResponse("Error processing request, please try again", null));
            }

            User loggedIn = users.findOne((int) session.getAttribute("userID"));
            Book toBeListed = books.findOne(json.get("bookID").asInt());
            long amount = json.get("amount").asLong();
            Condition condition = Condition.valueOf((json.get("condition").asText()).toUpperCase());
            String picture = json.get("picture").asText();

            Listing newListing = new Listing(toBeListed, condition, amount, picture);
            try {
                loggedIn.addListing(newListing);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(BAD_REQUEST).body(new JSONResponse("User does not own that book", null));
            }

            users.save(loggedIn);
            listings.save(newListing);

            return ResponseEntity.status(CREATED).body(new JSONResponse("Listing created", null));
        } else {
            return ResponseEntity.status(UNAUTHORIZED).body(new JSONResponse("You must be logged in to create a listing", null));
        }
    }

    @PostMapping(value = "/create", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public ResponseEntity createListingFormData(Integer bookID, Long amount, String condition, String picture, HttpSession session) {
        if (session.getAttribute("userID") != null) {
            User loggedIn = users.findOne((int) session.getAttribute("userID"));
            Book toBeListed = books.findOne(bookID);
            Condition conditionEnum = Condition.valueOf(condition.toUpperCase());

            Listing newListing = new Listing(toBeListed, conditionEnum, amount, picture);
            try {
                loggedIn.addListing(newListing);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(BAD_REQUEST).body(new JSONResponse("User does not own that book", null));
            }

            users.save(loggedIn);
            listings.save(newListing);

            return ResponseEntity.status(CREATED).body(new JSONResponse("Listing created", null));

        } else {
            return ResponseEntity.status(UNAUTHORIZED).body(new JSONResponse("You must be logged in to create a listing", null));
        }
    }

    @PostMapping("/create")
    public ResponseEntity createListingMediaNotSupported(HttpSession session) {
        if (session.getAttribute("userID") != null) {
            return ResponseEntity.status(UNSUPPORTED_MEDIA_TYPE).body(new JSONResponse("Content-Type not supported, please use \"application/json\" or \"application/x-www-form-urlencoded\"", null));
        } else {
            return ResponseEntity.status(UNAUTHORIZED).body(new JSONResponse("You must be logged in to do that", null));
        }
    }

    @DeleteMapping("/{listingID}")
    public ResponseEntity deleteAListing(@PathVariable int listingID, HttpSession session) {
        if (session.getAttribute("userID") != null) {
            Listing foundListing = listings.findOne(listingID);

            if (foundListing == null) {
                return ResponseEntity.status(NOT_FOUND).body(new JSONResponse("Listing with that ID not found", null));
            }

            User loggedIn = users.findOne((int) session.getAttribute("userID"));

            if (loggedIn.getBooksForSale().contains(foundListing)) {
                loggedIn.removeListing(foundListing);
                users.save(loggedIn);
                return ResponseEntity.status(NO_CONTENT).build();
            } else {
                return ResponseEntity.status(UNAUTHORIZED).body(new JSONResponse("Listing was not created by this user", null));
            }
        } else {
            return ResponseEntity.status(UNAUTHORIZED).body(new JSONResponse("You must be logged in to delete a listing", null));
        }
    }

    @PutMapping(value = "/{listingID}", consumes = "application/json")
    public ResponseEntity editAListing(@PathVariable int listingID, @RequestBody String editedListing, HttpSession session) {
        if (session.getAttribute("userID") != null) {
            Listing foundListing = listings.findOne(listingID);

            if (foundListing == null) {
                return ResponseEntity.status(NOT_FOUND).body(new JSONResponse("Listing with that ID not found", null));
            }

            User loggedIn = users.findOne((int) session.getAttribute("userID"));

            if (loggedIn.getBooksForSale().contains(foundListing)) {
                JsonNode json;

                try {
                    json = processJSON(editedListing);
                } catch (Exception e) {
                    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new JSONResponse("Error processing request, please try again", null));
                }

                if (json.get("amount") != null) {
                    foundListing.setAskingPrice(json.get("amount").asLong());
                }
                if (json.get("condition") != null) {
                    foundListing.setCondition(Condition.valueOf((json.get("condition").asText()).toUpperCase()));
                }

                listings.save(foundListing);
                return ResponseEntity.status(OK).body(new JSONResponse("Listing updated", foundListing));
            } else {
                return ResponseEntity.status(UNAUTHORIZED).body(new JSONResponse("Listing was not created by this user", null));
            }
        } else {
            return ResponseEntity.status(UNAUTHORIZED).body(new JSONResponse("You must be logged in to edit a listing", null));
        }
    }

    @PutMapping(value = "/{listingID}", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public ResponseEntity editAListingFormData(@PathVariable int listingID, Long amount, String condition, HttpSession session) {
        if (session.getAttribute("userID") != null) {
            Listing foundListing = listings.findOne(listingID);

            if (foundListing == null) {
                return ResponseEntity.status(NOT_FOUND).body(new JSONResponse("Listing with that ID not found", null));
            }
            User loggedIn = users.findOne((int) session.getAttribute("userID"));

            if (loggedIn.getBooksForSale().contains(foundListing)) {
                if (amount != null) {
                    foundListing.setAskingPrice(amount);
                }
                if (condition != null) {
                    foundListing.setCondition(Condition.valueOf(condition.toUpperCase()));
                }

                listings.save(foundListing);
                return ResponseEntity.status(OK).body(new JSONResponse("Listing updated", foundListing));
            } else {
                return ResponseEntity.status(UNAUTHORIZED).body(new JSONResponse("Listing was not created by this user", null));
            }
        } else {
            return ResponseEntity.status(UNAUTHORIZED).body(new JSONResponse("You must be logged in to edit a listing", null));
        }
    }

    @PutMapping("/{listingID")
    public ResponseEntity editListingMediaNotSupported(HttpSession session) {
        if (session.getAttribute("userID") != null) {
            return ResponseEntity.status(UNSUPPORTED_MEDIA_TYPE).body(new JSONResponse("Content-Type not supported, please use \"application/json\" or \"application/x-www-form-urlencoded\"", null));
        } else {
            return ResponseEntity.status(UNAUTHORIZED).body(new JSONResponse("You must be logged in to do that", null));
        }
    }

    private JsonNode processJSON(String toBeProcessed) throws Exception {
        JsonNode json = new ObjectMapper().readTree(new StringReader(toBeProcessed));
        if (json == null) {
            throw new Exception();
        }

        return json;
    }
}

