# Triangle - Java Messaging App

## Overview

- Triangle is a desktop java application. The app is a communication system that allows people in the company to the exchange of text messages between them.
Triangle supports the dynamicity of the context : it detects and takes into account sudden (dis)connections of users in runtime.
- Depending on the situation, the system architecture changes. It provides for different and adapted communication behaviours and communication procedures. In the enterprise, when all users are on the users are on the proprietary network, the system lacks a centralised architecture.
- From the user's point of view, the system is used as a service and facilitates the user's life. Indeed, an administrator of the organisation that wishes to use the system carries out the installation of the agents on the workstations of the people who will be interacting and, after a minimal configuration of the workstation, the system is operational.
- The system is also used by the experts and technicians responsible for administering and and deploy it on the workstations of the people who will be using it in order to carry out their tasks more their mission more effectively.

## How to use?

- Deployment description
...

## Project Description

### a. Constraints

- Decentralized system : no central server to handle the communications. Every host is in charge the management of its communications.
- There local databases to store the conversations and the logins.
- No 2 users can connect using the same Pseudo.
- 1 account is allowed per computer (as the account is linked to the MAC address of the computer)

### b. Approach and Project Structure

1. MVC : an iterative approach was required
    - Connection Phase
    - Adapting Connected Users Phase
    - Messaging exchanges Phase
2. Technologies
    - Front : JavaFX
    - Back : Java
    - Database : SQLite
    - Dependency Management : Maven
3. Protocols
    - Client-Server Model : Every user is a Client and Server at the same time.
        - TCP is used for sending and receiving messages.
        - UDP is used during the connection phase, the update of the connected users (disconnection, pseudo validation..)
    - BD : local DB
    - Multi-threaded System : Synchronous and Asynchronous behavior
    - Observer pattern : update on runtime → receiving new message and seeing a live contact book of the connected users.

### c. Major functionalities and behavior

1. Authentication window (capture)
    1. Signing up
        - Create an account by entering a pseudo and hitting the button “Sign in”
        - an entry is added to DB with a unique ID according to MAC address of the computer (implementation ONLY for Linux app)
        - If an account exists with that precise MAC, an error message is shown “Existing Account.”
    2. Logging in
        - Enter Pseudo in the box and hit the button “Log in“.
        - If the pseudo is not linked to any account (wrong, or account not created already), an error message is displayed “Account not found.”
        - If the pseudo is used currently by a current user, an error message is displayed “Unable to connect.”
        - If pseudo is valid to use, the user is connected : switch to Chat window.
2. Chat Window (capture) 
    1. View Connected users + update user
        - Once logged in, view on runtime : users disconnecting, connecting and an update in the Pseudo
    2. Initiating Conversation 
        - Click on one f the connected user.
        - If an ancient conversion has occurred before, the history is loaded on the left screen
        - If not, an empty history is printed
    3. Send Message
        - Enter message in the box and hit the sending button
        - If receiver is connected, the text is sent, added to DB  and is add added to view.
        - If not, an error message is displayed for the user “User disconnected”.
    4. Receive Message (notif ou pas notif)
        - If the sender is selected : the message is added to the loaded history
        - If not, the user is notified : the sender is highlighted in the list of the connected users
    5. Change Pseudo
        - Enter the New Pseudo in the box and hit the button Change
        - This change is broadcasted to the connected users.
        - If the Pseudo is unique (at the time of change), it is accepted and the change appears on runtime on the list of the connected users (the table of users is updated)
        - Otherwise, an error message is displayed “Pseudo in use.”
    6. Disconnect
        - Hit the disconnect button to get back to the Authentication Window and notify the connected user of the disconnection
    7. Close app 
        - using the closing button, the app stops turning and disconnection is broadcasted automatically
