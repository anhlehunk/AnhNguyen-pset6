# AnhNguyen-pset6
This app is built around the API of the Rijksmuseum. 
This app requires firebase to login with google or register with email.
You are required to log in to use this app.
Onced logged in, you get to see the main screen, with some navigation buttons and a brief explanation.
The search activity of this app is very simple to maximize the affordance of the app. There is only an editText and a searchbutton, 
where the user can (and must) fill in something. 
After pressing the search button, the input will be given to another activity with an intent.
In this new activity, the SearchFoundActivity, the input will be used in the AsyncTask. This will result in a list with results,
with the title and the picture (if available).
If you click on one of the results, the unique id of the certain result will be given to another activity.
This activity shows the information of a certain piece of art. On this activity it is also possible to add the item to your favorites.
In this list, you can view the item by clicking on the item, and delete the item by longclicking the item.
On the bottom of all activities except the main one, there is a navigation bar to navigate through the whole app with ease.
You can also logout anytime you want.
