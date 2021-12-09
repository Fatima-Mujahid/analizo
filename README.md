# Analizo
“ANALIZO” is an Esperanto word meaning “Analyze”. Analizo is a machine learning based solution that analyzes your movie reviews and categorize them as positive or negative and then displays the summary of the calculations. The users can see the summary to know about the overall review of people about that movie. It will also help the movie developer to know about the review of the people without going through each and every review.

### Architecture 
We followed Object Oriented Programming approach for the project. Major classes were Movie and Reviews. There were other classes based on activities as well e.g. MainActivity, AppIntro, SliderAdapter, ProductList, ProductDetail, ProductReviews and others. Moreover, we made use of methods wherever required to avoid code duplication and enhance program maintenance and reusability. In our project the concepts of Inheritance, Abstraction, Encapsulation, Polymorphism were used. We used data-centric architecture approach using Firebase real time database to store user reviews on movies. For sentiment classification ML model, we used a dataset containing 50,000 IMDb movie reviews (containing both positive and negative reviews) and trained and tested our sequential model on this data for 40 epochs. Our sequential model contains different layers including Embedding layer, Global Average Pooling 1D layer and Dense layers. The tools we used for the project are TensorFlow, TensorFlow Lite, Android Studio, Firebase (Realtime Database). The programming languages used are Java, XML and Python.

### Model and Accuracy

![model](https://github.com/Fatima-Mujahid/analizo/blob/main/Resources/model.jpg)

![accuracy](https://github.com/Fatima-Mujahid/analizo/blob/main/Resources/accuracy.jpg)

### Screenshots

Analizo Splash Screen

<img src="https://github.com/Fatima-Mujahid/analizo/blob/main/Resources/a1.png" alt="analizo splash screen" width="300" height="600">

App Intro Slider

![app intro slider](https://github.com/Fatima-Mujahid/analizo/blob/main/Resources/a2.png)

Movies List

<img src="https://github.com/Fatima-Mujahid/analizo/blob/main/Resources/a3.png" alt="movies list" height="600">

Movie Detail

![movie detail](https://github.com/Fatima-Mujahid/analizo/blob/main/Resources/a4.png)

Reviews and No Internet Activity

<img src="https://github.com/Fatima-Mujahid/analizo/blob/main/Resources/a5.png" alt="review and no internet" height="600">
