# ATM Machine (Phase 2)
=======================

## Setup
========
Building this project requires Maven.
If this does not compile, close the project and clone again, and make sure to import Maven, there are maven
dependencies so make sure these are imported (select auto import from maven if this is not done automatically)

1. Import this project from the directory: phase2
2. Select the option to search for the pom.xml recursively.
3. Once found import the project and the code will be in the src folder
4. The code will be in the main sub-folder and the tests will be in the tests sub-folder.
5. The GUI.java class will be in the GUI package, run the main method in this class to run our program.

## How to get started
=====================

-To initiate our ATM, compile the GUI.java class in the GUI package and run the main method.

-When ATM is first initiated, bank manager can log in using default username: admin and password: admin
 Then the bank manager can create new user accounts, restock Atm etc.

-Our ATM implements a database and thus will have pre-made user accounts available, see below for their login information

-When downloaded on a new machine this is simulating a new ATM machine and thus the cash.txt will be empty and the ATM
will have zero cash so you will not be able to withdraw. Either deposit cash as a customer or restock as a bank manager
and you will be able to withdraw. It is also possible to directly edit the cash.txt file, instructions for this file are below.

## Files
======================
### alerts.txt - a text file containing alerts from the ATM when it is low on bills, can be read by bank manager.
Format [message],[time stamp]

### cash.txt - a text file containing the current levels of bills in the ATM such that cash will still be in it after
the program is restarted ie it reads from this file what bills should be in the ATM when the main method is ran.
Format [number of fives],[number of tens],[number of twenty's],[number of fifty's]

### outgoing.txt - a text file that lists all outgoing payed bills from accounts
Format Payed bill: [amount] to [receiver].

## Phase two features
======================
### Users are able to request a US bank account from the bank manager. A bank teller can view currency conversion
in real time. Our program utilizes a currency API and thus exchange rates are updated in real time
(NOTE this API is free and is limited to 100 requests in an hour). When users transfer between US accounts
and their regular Canadian accounts, this currency exchange is automatically called and the correct amounts will
be deposited/withdrawn from the repetitive classes. Users are also able to select what currency they are able to
pay bills in, for example when a user pays a 1 dollar USD bill from their regular canadian account, approximately $1.33
(depending on current exchange rates) will be deducted from their canadian accounts (and vice versa if paying a canadian
bill from a US account.

### Bank managers are able to create customer accounts as per phase 1, however now they have the option to make them
students accounts (as well as toggle its designation as a student account after its creation by going to the edit
user accounts. Student accounts are not charged monthly fees. To test this one can login as bank manager, set the date
to the first of a month, and all non-student accounts will have five dollars deducted from their primary chequing accounts.
(NOTE this same method of changing the time to the first of a month can be used to see interest on savings accounts.

### Joint accounts can be requested by users on their request accounts screen by entering the username of the other user
in the given text box and if a bank manager approves this request (their view account requests screen) the account
will show up in both of their view accounts screens.

### Bank tellers are customer users with more functionality (and are this subclasses of customer users) for example they
can view current exchange rates, reset users passwords given their username and security questions. They can access
other users accounts and do things such as withdraw, deposit, etc.

### GUI implements the Model-View-Controlled design pattern in that users see and interact with the GUI, our view, which
they can use to access the controller, which in turn affects model, our database, which then updates the GUI as it gets
the information to show from said database (the model).

### A database hosted on AWS that allows for our ATM system to work cross system, ie any computer with this program
is able to access the same the accounts from anywhere with the current information.

