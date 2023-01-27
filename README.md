# 1. Overview

Triangle is a desktop java application. The app is a communication system that allows people in the company to exchange text messages. Triangle supports the dynamicity of the context: it detects and takes into account sudden connections or disconnections of users in runtime.
Depending on the situation, the system architecture changes. It provides different and adapted communication behavior. In the enterprise, when all users are on the proprietary (local) network, the system lacks a centralized architecture.  From the user’s perspective, we are in charge of developing a service that eases their life. Experts and technicians responsible for administration and deployment on workstations can use the app too.

# 2. Learn about Triangle

## 2.1. Requirements

### Functional Requirements**

- The user can create an account.
- The user can log in to the account.
- The user can log out from the account.
- The user can view users connected to the local network as one-self.
- The user can start a conversation with any of the connected users.
- The user can receive a message from any other connected user.
- The user can communicate with as many users.
- The user is notified when a message is received from another connected user.
- The user can change his/her pseudo when logged in. The other users are notified of the change.
- An admin can deploy, configure and set up the app.

### **Non-functional Requirements**

- Decentralized system: no central server to handle the communications. Every host has to manage his/her communications.
- Local databases: login and message history are stored locally.
- Unique pseudo: No two users can connect simultaneously using the same Pseudo.
- Unique ID: MAC address is used to identify the user.

## 2.2. Technologies

- Backend: **Java 11 →** general-purpose, object-oriented language intended to let programmers write once, and run anywhere (WORA).
- Frontend: **JavaFX** → Java client-app platform desktop offering many features.
- Graphic Front building tool: **Scene Builder** → graphically handles JavaFX components.
- Database: **SQLite** → local and fast database.
- Dependency Management: **Maven** → handles build automation and dependencies.
- Collaboration and version control: **Git** → Repo: https://github.com/tasnimk11/MessagingApp.git.
- Issue, tracking, and product management: **Jira** → supports the Agile-working method.

## 2.3. Protocols and Technical choices

- **Client-Server Model**: Every user is a Client and a Server at the same time.
- **TCP** is used for sending and receiving messages.
- **UDP** is used during the connection phase, the update of the connected users (disconnection, pseudo validation...).
- **Multithreaded System**: Synchronous and Asynchronous behavior.
- **Observer pattern**: update on runtime → receiving a new message and seeing a live contact book of the connected users.

## 2.4. Backstory and Graphics

![traingle](https://user-images.githubusercontent.com/99467850/214590794-4f260c2d-c105-47cd-9667-f0678a3d9bd8.png)

We chose “**Triangle”** as the name of our app for various reasons:
- It is simple, easy to remember, and easy to recognize.
- This is our first collaborative project as a team. We value project management and teamwork. The shape of a triangle represents the three main stages of a project: planning, executing, and testing.
- It is a reference to the three points of a triangle symbolizing the three main features of the application: authenticating, sending, and receiving.

![logo](https://user-images.githubusercontent.com/99467850/214590719-f5da0488-06ec-40af-9ccb-93d58de133c2.png)

We chose a logo for the project that is circle-shaped:

- It symbolizes unity, continuity, and cohesiveness of individuals or communities using the application or developing it.
- It is also simple and easily recognizable.

We chose a dark-mode arcade theme (purple, pink, black).

# 3. How to use?

## 3.1. Deployment

There are many ways to deploy the app. Here are two ways we suggest:

### **First Method: Executable JAR**

1. Download the jar corresponding to your OS: [Linux](https://drive.google.com/file/d/1CDLqvfQEhxpkMyi9jZjWH9VMaSAWm9yV/view?usp=sharing) or [Windows](https://drive.google.com/file/d/1G-EAPIkDAuuzMSXChI6kCbY9hVLkf6u6/view?usp=sharing). (mac version coming soon)

2. Open the archive with any tool (Don’t unzip it).
    - *Locate the “**BroadcastAddress**” in the root file and open it with any text editor.*
        -  *Open “**BroadcastAddress**” with any text editor.*
        - *change the broadcast address to that of your network*
            - *if you are on a Linux OS, you can use the* `ifconfig` *command in the terminal.*
            - *if you are on a Windows OS, you can use the* `ipconfig` *command in the terminal.*
    - *Make sure you use the correct address, in the correct format and preserve the format of the file.*
3. Run the App:
    - *If you are on Linux  run `java –jar triangle-linux.jar` on the terminal.*
    - *If you are on Windows  just double-click on the jar.*



### **Second Method: Git**

1. Clone the git repo using the following command:
    - *if you are on a Linux OS :*  `git clone -b triangle-linux https://github.com/tasnimk11/MessagingApp.git`
    - *if you are on a Windows OS :*  `git clone -b triangle-windows https://github.com/tasnimk11/MessagingApp.git`
2. Move to the root directory of the project: that contains the pom.xml file
    - *Open “**BroadcastAddress**” with any text editor.*
    - *change the broadcast address to that of your network*
        - *if you are on a Linux OS, you can use the* `ifconfig` *command in the terminal.*
        - *if you are on a Windows OS, you can use the* `ipconfig *`command in the terminal.*
    - *Make sure you use the correct address, in the correct format and preserve the format of the file.*
3. Open the terminal and run the following maven command : `mvn clean javafx:run`*.*
    - *Make sure you have Java and Maven installed, and the environment variables of Java and Maven configured correctly.*


## 3.2. Behavior and functioning

1. Authentication window
![FinalView1](https://user-images.githubusercontent.com/99467850/215125672-528a6fdf-b51a-4240-80a4-86bab60bc189.png)
    - Signing up
        - Create an account by entering a pseudo and hitting the button “**Sign up**”.
        - An entry is added to the database with a unique ID according to the MAC address of the computer (implementation ONLY for Linux app).
        - If an account already exists with that precise MAC, an error message is shown “**Existing Account**”.
    - Logging in
        - Enter Pseudo in the box and hit the button “**Log in**“.
            - If the pseudo is not linked to any account (wrong, or account not created already), an error message is displayed: “**Account not found**”.
            - If another connected user is using the pseudo currently, an error message is displayed: “**Unable to connect**”.
            - If pseudo is valid to use, the user is connected: switch to Chat window.

2. Chat Window
![FinalView2](https://user-images.githubusercontent.com/99467850/215125647-5537c2ab-fdc8-40ae-b416-6a9f4365f453.png)
    - View Connected users + update user
        - Once logged in, you can view users disconnecting, connecting, and updating their pseudo on runtime.
    - Initiating Conversation
        - Click on one of the connected users.
        - If an ancient conversion has occurred before, the history is loaded on the right side of the screen.
        - If not, an empty history is printed.
    - Send Message
        - Enter the message in the box on the right side and hit the sending button.
        - If the receiver is connected, the text is sent, added to the database, and added to the messages view.
        - If not, an error message is displayed for the user “**User disconnected**”.
    - Receive Message
        - If the sender is selected: the message is added to the view of the loaded history.
        - If not, the user is notified: the sender is highlighted in the list of the connected users.
    - Change Pseudo
        - Enter the New Pseudo in the box and hit the “Pen” button.
        - This change is broadcasted to the connected users.
        - If the Pseudo is unique (at the time of change), it is accepted and the change appears on runtime on the list of the connected users (the table of users is updated).
        - Otherwise, an error message is displayed “Pseudo in use.
    - Disconnect
        - Hit the “**Log out**” button to get back to the Authentication Window and notify the connected users of the disconnection.
    - Close app
        - Using the closing button, the app stops turning and disconnection is broadcasted automatically.


# 4. Reach out!

Have you encountered any problems? Do need more information about our app? Do you need any help with the deployment? Do you want to report some bugs? Feel free to contact the contributors:

- Sofiene Ben Yahia (he/him) : benyahiasofiene@yahoo.com
- Tasnim Kamoun (she/ her): tasnimk1108@gmail.com
