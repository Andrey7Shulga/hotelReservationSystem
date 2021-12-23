# Hotel Reservation Application - the first practical project of the program 
program link - https://www.udacity.com/course/java-programming-nanodegree--nd079

- the second practical project: https://github.com/Andrey7Shulga/webcrawler
- the third practical project: https://github.com/Andrey7Shulga/udacity-service-parent

In this project, you will be designing and implementing a Java hotel reservation application. The hotel reservation application will allow customers to find and book a hotel room based on room availability. This project will demonstrate your abilities to design classes using OOP, organize and process data with collections, and use common Java types. 

## The major components of the Hotel Reservation Application will consist of the following:
- CLI for the User Interface. We'll use the Command Line Interface (or CLI for the user interface. For this, we'll need to have Java monitor the CLI for user input, so the user can enter commands to search for available rooms, book rooms, and so on.
- Java code. The second main component is the Java code itself—this is where we add our business logic for the app.
- Java collections. Finally, we'll use Java collections for in-memory storage of the data we need for the app, such as the users' names, room availability, and so on.

## Application Architecture
The app will be separated into the following layers:
- User interface (UI), including a main menu for the users who want to book a room, and an admin menu for administrative functions.
- Resources will act as our Application Programming Interface (API) to our UI.
- Services will communicate with our resources, and each other, to build the business logic necessary to provide feedback to our UI.
- Data models will be used to represent the domain that we're using within the system (e.g., rooms, reservations, and customers).

## The application provides four user scenarios:
- Creating a customer account. The user needs to first create a customer account before they can create a reservation.
- Searching for rooms. The app should allow the user to search for available rooms based on provided checkin and checkout dates. If the application has available rooms for the specified date range, a list of the corresponding rooms will be displayed to the user for choosing.
- Booking a room. Once the user has chosen a room, the app will allow them to book the room and create a reservation.
- Viewing reservations. After booking a room, the app allows customers to view a list of all their reservations.

