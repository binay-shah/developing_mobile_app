#  *Movie Manager*

**Movie Manager** shows the latest movies currently playing in theaters and the upcoming movies. The app displays images and basic information about these movies to the user.

## Topics

* Recyclerview, Adapter & ViewHolder
* Fragments
* Material design elements
    - Floating Action Button
    - Snackbar
    - App bar y Toolbar
    - Coordinator Layout
* Navigation
    - Drawer


## User Stories

The following **required** functionality is completed:

* [x] User can **scroll through current movies and upcoming movies**
* [x] Layout is optimized with the [ViewHolder](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView#improving-performance-with-the-viewholder-pattern) pattern.
* [x] For each movie displayed, user can see the following details:
  * [x] Title, Poster Image, Overview (Portrait mode)
* [x] User interface improved through styling and coloring and using material design elements such as [CardView](https://developer.android.com/reference/android/support/v7/widget/CardView.html) and [Navigation Drawer](https://developer.android.com/training/implementing-navigation/nav-drawer.html)


The following **bonus** features are implemented:

* [x] Allow user to view details of the movie including ratings and popularity within a separate activity.
* [x] All the values for dimensions are specified in dimens.xml
* [x] All the string values are specified in strings.xml

####Functionality
* [x] The code runs without errors.
* [x] Each percentage button updates the TextViews by adding the correct new values.
* [x] The reset button resets the scores on both of the score TextViews.

####Code Readability
* [x] Any classes are named after the object they represent.All variables are named by their intended contents. All methods are named by their intended effect or in the style required by a callback interface.
* [x] "There are no unnecessary blank lines. One variable is declared per declaration line. The code within a method is indented with respect to the method declaration line."

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://github.com/binay-shah/developing_mobile_app/blob/master/week3_4/examples/MovieManager/movie_manager_demo.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Open-source libraries used

- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
