# README.md

## Fragrances Catalog

## This is an Android app created for the offline database management of a mini fragrance store.

**The app allows the user to perform basic CRUD operations which include:**

*	Creating  fragrances with the properties:
	name, wholesale price, retail price, quantity in stock and gender
*	Viewing a list of all the fragrances saved. The list shows the name, price, gender,
	quantity sold and quantity remaining in stock
*	Updating fragrance properties such as name, wholesale price, 
	retail price, quantity in stock and gender
*	Delete fragrances.

**The app also allows the user to **

*	Search for fragrances by name, track the quantity sold for each 
	fragrance and also 
*	Share the fragrances and the properties of the fragrance through 
	text-based implicit intents for cases where the user might want to 
	share the content of the inventory with a prospective customer. 

###	The app uses Android Room SQLite database to store data locally.

**Bugs and Impending Improvements**

*	Bug: Two fragrances with exactly the same names can be saved.
	This should be prevented.

*	Improvement: Settings should be added for sorting fragrances by name, 
	price, quantity sold and other properties.
*	Improvement: The context menu could be attached to list items and not 
	the Action Bar
