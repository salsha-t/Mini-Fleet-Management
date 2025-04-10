# Mini Fleet Management
Mini Fleet Management is an Android application built using Kotlin that monitors various parameters of a fleet, such as vehicle speed, door status, and engine status. The application displays live updates and issues alerts via dialog boxes and notifications if certain thresholds are exceeded (e.g., high speed, open door, or engine off).

## Screenshoots
![Image Alt](https://github.com/salsha-t/Mini-Fleet-Management/blob/0059fd09b8794d8bbf4ff94ea784803cb103ed87/app_screenshot.jpg)

## Features
### 1. Simulated Route
The application simulates a journey from Monas (Monumen Nasional) to TMII (Taman Mini Indonesia Indah). The route is represented by a series of hardcoded latLng points. New positions are generated every 5 seconds, resulting in a total of 15 waypoints.
### 2. Periodic API Requests
Sensor data such as speed, door status, and engine status are fetched via a Node-RED API. The app requests sensor data every 15 seconds, making a total of 5 requests per simulation cycle.
### 3. Real-time Alerts and Notifications
Alerts are shown (via dialogs and notifications) when dangerous conditions occur—for instance, when the speed exceeds 80 km/h, the door is left open, or the engine is off.
### 4. MVVM Architecture
Uses ViewModel and LiveData to handle asynchronous API calls, ensuring a clean separation between the UI and the business logic.

## Installation
### 1. Running Node-RED
(If Node-RED is already installed, proceed to Step 4)
1. Make sure you have Node.js installed, which includes npm.
2. Verify Node.js and npm installation
```shell
node --version && npm --version
```
3. Install Node-RED
```shell
npm install -g --unsafe-perm node-red
```
4. Run Node-RED
```shell
node-red
```

### 2. Importing Node-RED Flow
1. Download the node-red.json file from this repository.
2. Open the Node-RED interface (usually at http://localhost:1880), then use the Import option to import the node-red.json file.
3. Click the Deploy button to apply the imported flow.

### 3. Clone the Repository and Import into Android Studio
1. Clone this repository
```shell
git clone https://github.com/salsha-t/Mini-Fleet-Management.git
```
2. Open the project in Android Studio

### 4. Additional Setup for Google Maps API
1. Generate SHA1 from Android Studio terminal
```shell
./gradlew signingReport
```
2. Open the following URL in your web browser, replacing YOUR_SHA1 with your SHA1 value and ensuring that the package name (com.portfolio.minifleetmanagement) matches your project
```shell
[./gradlew signingReport](https://console.developers.google.com/flows/enableapi?apiid=maps-android-backend.googleapis.com&keyType=CLIENT_SIDE_ANDROID&r=YOUR_SHA1%3Bcom.portfolio.minifleetmanagement
)
```
3. Copy the API key provided and replace the placeholder in your AndroidManifest.xml (simply update YOUR_API_KEY with the actual key.)
```shell
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="YOUR_API_KEY" />
```

### 5. Running the Application on an Emulator
The application accesses Node-RED through the localhost IP address (10.0.2.2).
Make sure to run the application using the Android Studio emulator only.





