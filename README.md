# Interima
<!---
ip = interima.ddns.net
port = 3306
-->
## Authors
[Océane ONGARO](https://oceaneongaro.github.io/)

[Lorenzo PUCCIO](https://github.com/StOil-L)

[Arthur SAPIN](https://github.com/a-sapin)

## Context

This project was carried out during my studies at the University of Montpellier as a first year Software Engineering Masters Degree student.

It constitutes as an assignment from the Mobile Programming subject (subject code: HAI811I).

Interima is an Android app built for the browsing, application and management of temping offers. Its backend is a MySQL image deployed on a Raspberry Pi 4 with Docker.

## Features

| **Features**                                                       | **No user role** | **Job seeker** | **Employer** | **Temping agency** |
|--------------------------------------------------------------------|------------------|----------------|--------------|--------------------|
| Browse relevant temping offers based on device's location          |         Y        |        Y       |       Y      |          Y         |
| Sign up and log in as specific user role                           |         Y        |        Y       |       Y      |          Y         |
| Check full offer details                                           |         Y        |        Y       |       Y      |          Y         |
| Bookmark offer                                                     |         N        |        Y       |       N      |          N         |
| Apply from scratch or reusing a previous application's information |         N        |        Y       |       Y      |          Y         |
| Check application details and status                               |         N        |        Y       |       Y      |          Y         |
| Manage application status                                          |         N        |        N       |       Y      |          Y         |
| Check search history                                               |         N        |        Y       |       Y      |          Y         |
| Check submitted resumes and cover letters                          |         N        |        Y       |       N      |          N         |
| Publish offer from in-app form                                     |         N        |        N       |       Y      |          Y         |
| Publish offer from JSON file                                       |         N        |        N       |       N      |          Y         |
| Check published offers                                             |         N        |        N       |       Y      |          Y         |

## Getting Started

As the aforementioned MySQL image has been deleted off the Raspberry Pi 4, the app has become practically unusable.

However, if you still wish to run what remains of the app's frontend, you can do so by importing this project in Android Studio and running it either on actual hardware or with an Android Virtual Device - in which case we recommend a Google Pixel 5 with an Android API of version 28+ for best effect.
