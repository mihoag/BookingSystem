# Movie Ticket Booking System

## Overview

The Movie Ticket Booking System is a graphical user interface (GUI) application designed to facilitate the booking of tickets for events occurring on specified future dates. This system consists of two main components: a server application and a client application, allowing for efficient management and purchase of tickets.

## Key Features

### Event Configuration
- **Future Events**: Configure events to take place on a specific date in the future.
- **Multiple Showtimes**: Set up various showtimes within a single day (e.g., 7:00 AM - 9:00 AM, 9:30 AM - 11:30 AM).

### Venue Setup
- **Seating Arrangement**: Configure the layout of the venue, including the number of zones or stages, rows, and seats per row.
- **Dynamic Pricing**: Set different ticket prices based on the designated zones.

### Data Storage
- **Internal Data Management**: Operate without a database by utilizing internal data storage for managing event and booking information.

## Server Application (GUI)
- **Admin Controls**: Allows administrators to configure events, seating arrangements, and ticket prices.
- **Real-Time Monitoring**: Provides a view of seat availability (occupied or vacant).
- **Multi-Client Support**: Facilitates simultaneous connections from multiple client applications for ticket booking.

## Client Application (GUI)
- **User Connectivity**: Connects to the server to retrieve and display available events and their showtimes.
- **Seating Layout Display**: Shows the seating arrangement and indicates the status of each seat (occupied or available).
- **Ticket Booking**: Enables users to book tickets for available seats, storing essential customer information (name, phone number, seat selection).

## Prerequisites
- **Java Development Kit (JDK)**
- **Eclipse IDE** (or another compatible Java IDE)

## Instructions for Creating Executable JAR Files

### Step 1: Open the Project in Eclipse
1. Open Eclipse IDE.
2. Go to **File** -> **Open Projects from File System...**.
3. Click on **Directory...**, navigate to the `BookingSystem` project directory, and select it.
4. Click on **Select Folder** to import the project.

### Step 2: Run Server and Client Main Files
1. In the `run` directory, right-click on `StartServerMain.java`.
2. Choose **Run As** -> **Java Application** to start the server, then close the application.
3. Repeat the same steps for `StartClientMain.java` to ensure both files are configured to run.

### Step 3: Create a Release Directory
1. Create a new folder named `release`. You can place it anywhere on your system.

### Step 4: Export Executable JAR Files
1. Right-click on the `BookingSystem` project in Eclipse.
2. Go to **Export...** -> **Java** -> **Runnable JAR file** and click **Next**.
3. In the **Launch configuration** dropdown, select `StartServerMain - BookingSystem`.
4. For **Export destination**, click **Browse...**, navigate to the `release` folder, and specify a name for the JAR file.
5. Click **Finish** to create the JAR file.
6. Repeat the same steps for `StartClientMain - BookingSystem`.

### Step 5: Copy Data Files
1. Copy the `BookingInfoData.dat` and `UserData.dat` files into the `release` folder.

### Step 6: Set Up Assets Directory
1. In the `release` folder, create the following directory structure: `src/main/java/assets`.
2. Copy all images from the `assets` directory in the project and paste them into the newly created `assets` folder in the `release` directory.

### Step 7: Run the JAR Files
1. Navigate to the `release` folder.
2. Double-click on the `StartServerMain.jar` to start the server.
3. Similarly, double-click on the `StartClientMain.jar` to start the client.

## Video demo: https://www.youtube.com/watch?v=Hv6hROnKYb8
