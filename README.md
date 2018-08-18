# Shopping Cart API

## User Story:
As a shopper, I want to manage products from my shopping cart and see what I already have in it.

### Project Requirements

| Requirement | Description
| ------ | ------ 
| [REQ-001](#Can-add-products-to-the-basket) | Can add products to the basket |
| [REQ-002](#Can-remove-products-to-the-basket) | Can remove products to the basket |
| [REQ-003](#Can-see-which-products-are-in-the-basket) | Can see which products are in the basket |
| [REQ-004](#Can-see-the-costs-for-the-items-in-the-basket) | Can see the costs for the items in the basket |
| [REQ-005](#A-subtotal-displays-and-gets-updated-whenever-I-make-a-changes-to-the-cart) | A subtotal displays and gets updated whenever I make a changes to the cart |

## Technical Acceptance Criteria

### The solution must be publicly available, using Heroku or any other PaaS or IaaS.
This API has been built using MVN, SpringBoot and spark framework

### The solution manages one shopping cart per user session.
The application can handle one shopping cart using the session.

## endpoints

> GET /products

Returns information about the products.

Return type: array[Product]

> GET /shoppingcart

Returns the current shopping cart for the session. 

Return type: ShoppingCart

> POST /shoppingcart/items

Adds an item to the shopping cart.

Return type: CommerceItem

> DELETE /shoppingcart/items/:id

Removes a commerce item from the shopping cart, by commerce item id.

## Running Locally

Make sure you have [MVN](https://maven.apache.org/) and the [Java 8](https://www.java.com/pt_BR/download/) installed.

```sh
$ git clone https://github.com/guilhermemalfatti/shoppingCartAPI
$ cd shoppingCartAPI
$ ./mvnw spring-boot:run
```

Your app should now be running on [localhost:8081](http://localhost:8081/).

## Deploying to Heroku

```
$ cd shoppingCartAPI
$ mvn heroku:deploy
```

or

The easiest way to deploy this is to push the button:

[![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)
