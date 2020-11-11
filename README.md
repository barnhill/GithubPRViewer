# GithubPRViewer ![Android CI](https://github.com/barnhill/GithubPRViewer/workflows/Android%20CI/badge.svg) [![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
Displays Github pull requests with diff viewer

This project was created to give an example of how to list pull request from a user on Github, view the pull request.

## To Run the application
This project uses the Gradle build system. To build this project, use the gradlew build command or use "Import Project" in Android Studio.

There is a Gradle task for installing the project:

`installDebug` - for installing the project on a connected device

## Libraries Used
  * [Android Jetpack][0] - Components for core system capabilities
  * [LiveData][13] - Build data objects that notify views when the underlying database changes.
  * [ViewModel][17] - Store UI-related data that isn't destroyed on app rotations.
  * [Glide][90] - for asynchronous image loading
  * [Retrofit][91] - for making requests and recieveing data from a RESTful API
  * [OKHttp][92] - for use with Retrofit as the HTTP client

[0]: https://developer.android.com/jetpack/foundation/
[13]: https://developer.android.com/topic/libraries/architecture/livedata
[17]: https://developer.android.com/topic/libraries/architecture/viewmodel
[90]: https://bumptech.github.io/glide/
[91]: https://square.github.io/retrofit/
[92]: https://square.github.io/okhttp/
