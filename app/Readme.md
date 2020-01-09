# Recipe Finder

The application allows the user to search for recipes, either by specific or type or recipe or/and
using a list of predefined common ingredients.

The search will be done using the API http://www.recipepuppy.com/about/api/. 

The app has 3 screens:
 * MainActivity: Search for recipes.
 * ListRecipesActivity: API results, displayed as a list of elements containing the title of the 
 recipe, the list of ingredients, a button to go to the recipe URL and a clickable image for 
 adding the recipe to the user's Favourites list.
 * ListFavouriteActivity: List of saved recipes. For storing the data, Room is used.
 
Most of the code is adapter after the [Android Developer Fundamentals Course](https://codelabs.developers.google.com/android-training/).