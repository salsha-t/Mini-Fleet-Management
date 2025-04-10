# Mini Fleet Management
Mini Fleet Management is an Android application built using Kotlin that monitors various parameters of a fleet, such as vehicle speed, door status, and engine status. The application displays live updates and issues alerts via dialog boxes and notifications if certain thresholds are exceeded (e.g., high speed, open door, or engine off).


## Screenshoots
![Image Alt](https://github.com/salsha-t/Mini-Fleet-Management/blob/0059fd09b8794d8bbf4ff94ea784803cb103ed87/app_screenshot.jpg)

## Features
### 1. Simulated Route:
The application simulates a journey from Monas (Monumen Nasional) to TMII (Taman Mini Indonesia Indah). The route is represented by a series of hardcoded latLng points. New positions are generated every 5 seconds, resulting in a total of 15 waypoints.
### 2. Periodic API Requests:
Sensor data such as speed, door status, and engine status are fetched via a Node-RED API. The app requests sensor data every 15 seconds, making a total of 5 requests per simulation cycle.
### 3. Real-time Alerts and Notifications:
Alerts are shown (via dialogs and notifications) when dangerous conditions occur—for instance, when the speed exceeds 80 km/h, the door is left open, or the engine is off.
### 4. MVVM Architecture:
Uses ViewModel and LiveData to handle asynchronous API calls, ensuring a clean separation between the UI and the business logic.

## Installation
### Running Node-RED
(If Node-RED is already installed, proceed to Step 4)
#### 1. Install Node.js and npm:
Make sure you have Node.js installed, which includes npm.
#### 2. Verify Installation:
Open your cmd terminal and run:
```shell
node --version && npm --version
If installed correctly, the versions of Node.js and npm will be displayed.




