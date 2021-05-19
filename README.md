Choco (test app)
=================

The #1 ordering tool for restaurants & suppliers

Getting Started
---------------
This project uses the Gradle build system. To build this project, use the
`gradlew build` command or use "Import Project" in Android Studio.

There are two Gradle tasks for testing the project:
* `connectedAndroidTest` - for running Espresso on a connected device
* `test` - for running unit tests

### Running on a virtual device

1.  Clone repository https://github.com/choco-hire/mobile-exercise-ErzhenaD.git
2.  Open the AVD Manager (**Tools** -> **Android** -> **AVD Manager**), https://i.imgur.com/881HJHx.png
3.  Create a new Virtual Device
4.  Finish and click play!

### Running on a hardware device

1.  Clone repository https://github.com/choco-hire/mobile-exercise-ErzhenaD.git
2.  [Configure Your System to Detect Your Android Device][96]
3.  Finish and click play!

Screenshots
-----------

![Start](screenshots/login.png "Login page")
![List of products](screenshots/productsList.png "List of products")
![Choosing products](screenshots/productsChoose.png "Choosing products")
![Create an order](screenshots/productsChoose.png "Create an order")
![Name the order](screenshots/nameTheOrder.png "Name the order")
![List of orders](screenshots/ordersList.png "List of orders")
![Order details](screenshots/orderDetails.png "Detailed information about the order")
![List of products_dark](screenshots/productsListDark.png "List of products with dark mode")
![Order details_dark](screenshots/orderDetailsDark.png "Detailed information about the order with dark mode")

Libraries Used
--------------
* [Foundation][0] - Components for core system capabilities, Kotlin extensions and support for
  multidex and automated testing.
  * [AppCompat][1] - Degrade gracefully on older versions of Android.
  * [Android KTX][2] - Write more concise, idiomatic Kotlin code.
  * [Test][4] - An Android testing framework for unit and runtime UI tests.
* [Architecture][10] - A collection of libraries that help you design robust, testable, and
  maintainable apps. Start with classes for managing your UI component lifecycle and handling data
  persistence.
  * [Data Binding][11] - Declaratively bind observable data to UI elements.
  * [Lifecycles][12] - Create a UI that automatically responds to lifecycle events.
  * [LiveData][13] - Build data objects that notify views when the underlying database changes.
  * [ROOM][16] - The Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite
  * [ViewModel][17] - Store UI-related data that isn't destroyed on app rotations. Easily schedule
     asynchronous tasks for optimal execution.
* [UI][30] - Details on why and how to use UI Components in your apps - together or separate
  * [Animations & Transitions][31] - Move widgets and transition between screens.
  * [Fragment][34] - A basic unit of composable UI.
  * [Layout][35] - Lay out widgets using different algorithms.
* Third party and miscellaneous libraries
  * [Dagger][92]: for [dependency injection][93]
  * [RxJava][91] for managing background threads 
  * [OkHttp][94] for sending and receive HTTP-based network requests
  * [Retrofit][95] for retrieving and uploading JSON (or other structured data) via a REST based webservice

[0]: https://developer.android.com/jetpack/components
[1]: https://developer.android.com/topic/libraries/support-library/packages#v7-appcompat
[2]: https://developer.android.com/kotlin/ktx
[4]: https://developer.android.com/training/testing/
[10]: https://developer.android.com/jetpack/arch/
[11]: https://developer.android.com/topic/libraries/data-binding/
[12]: https://developer.android.com/topic/libraries/architecture/lifecycle
[13]: https://developer.android.com/topic/libraries/architecture/livedata
[14]: https://developer.android.com/topic/libraries/architecture/navigation/
[16]: https://developer.android.com/topic/libraries/architecture/room
[17]: https://developer.android.com/topic/libraries/architecture/viewmodel
[18]: https://developer.android.com/topic/libraries/architecture/workmanager
[30]: https://developer.android.com/guide/topics/ui
[31]: https://developer.android.com/training/animation/
[34]: https://developer.android.com/guide/components/fragments
[35]: https://developer.android.com/guide/topics/ui/declaring-layout
[91]: https://github.com/ReactiveX/RxJava
[92]: https://dagger.dev/
[93]: https://developer.android.com/training/dependency-injection
[94]: https://square.github.io/okhttp/
[95]: https://square.github.io/retrofit/
[96]: https://developer.android.com/studio/run/device

Support
-------

Patches are encouraged, and may be submitted by forking this project and submitting a pull request
through GitHub.
