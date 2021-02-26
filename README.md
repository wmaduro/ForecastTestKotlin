# ForecastTestKotlin <img src="https://i.imgur.com/bdtycwr.png">
Opensource weather app for Android. It gathers data from [OpenWeatherMap](https://openweathermap.org/), via their public API.


## Architectural Decisions
### MVVM
The app was implemented using the following best practices' architecture:

* __Data Binding__
* __LiveData__
* __ViewModel__
* __Lifecycle__

### Other Applied Patterns

* __NetworkConnectionInterceptor__

    The interceptor is responsible for check the internet connection state before sending any request to API.
  
* __Eventbus__

    The Eventbus is responsible to notify the VM that internet connection is not availabe. It was implemented to avoid a tight coupling between API's service and View layers. 

* __ActivityLifecycleCallbacks__

    The life cycle callbacks allow the application to get the current opened activity for a general purpose. 


### General Application's Structure

* __OVERVIEW__

[<img src="https://i.imgur.com/sfLPjTc.png">](https://i.imgur.com/sfLPjTc.png)


* __Package "view"__

    In this package you will find classes responsible to for the view layer.
    
    - search - Folder containing fragments and adapters to show the search data.
    
    - forecast - Folder containing fragments and adapters to show the weather information received from the API.
    
    - main - Folder containing the main activity.
    
* __Package "viewmodel"__

    In this package you will find the MainViewModel class.

* __Package "respository"__

    In this package you will find classes responsible to provide the data access and the repository interface.

    - openweathermap - Folder containing the service class to provide access to OpenWeatherMap API. 
    
    - ForecastTestJavaRepository - Main repository class.
    
    - ApiDataParser - Helper class to parse Api's data to POJO classes. 

* __Package "model"__

    In this package you will find classes responsible for the data modeling.
    
    - api -  Package containing classes to map the API data sources.
    
    - pojo - Package containing POJO classes to map the information that will be used in the Views. 

* __Package "base"__
    
    In this package you will find classes responsible for the application infra-structure, such as: Application, ActiveBase, Interceptors and EventBus messages.

* __Package "utils"__

    In this package you will find general purpose classes.

## Implemented Features
* GPS location on startup
* Detailed 5-day forecast
* Works with any city in the world

## Next Features
* Offline city search.
* City search helper.
* Show more weather information - Humidity, Precipitation's probability and more.
* Graph to show weather temperature evolution.
* Degree scale parametrization.
* Default cities.
* Weather alerts.


## Screenshots

<!-- [<img src="https://i.imgur.com/0EZUsaW.png" width=250>](https://i.imgur.com/0EZUsaW.png)
[<img src="https://i.imgur.com/YYZ6qrF.png" width=250>](https://i.imgur.com/YYZ6qrF.png)
[<img src="https://i.imgur.com/I3e3rDl.png" width=250>](https://i.imgur.com/I3e3rDl.png)
[<img src="https://i.imgur.com/fpCLfh0.jpg" width=250>](https://i.imgur.com/fpCLfh0.jpg)
[<img src="https://i.imgur.com/mD7pSU5.jpg" width=250>](https://i.imgur.com/mD7pSU5.jpg) -->



