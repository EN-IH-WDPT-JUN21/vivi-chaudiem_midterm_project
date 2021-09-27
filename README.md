# Midterm project: Banking system

## Table of Contents

1. [Introduction](#Introduction)
2. [Functionality](#Functionality)
    1. [SalesReps](#1-salesreps)
    2. [Lead](#2-lead)
    3. [Opportunity](#3-opportunity)
    4. [Contact](#4-contact)
    5. [Account](#5-account)
    6. [Report](#6-report)
3. [User Tutorial](#User-Tutorial)
4. [Class Diagram](#Class-Diagram)
5. [Use Case Diagram](#Use-Case-Diagram)
### Introduction
This is a banking system project which allows you to use the general basic functionalities of an online banking system.

### Getting started
1. Clone this repo to a new project in IntelliJ
2. Run the following code in your MySQL Workbench:
   ```sh
   CREATE DATABASE IF NOT EXISTS midtermProject;
   USE midtermProject;

   CREATE USER IF NOT EXISTS 'ironhacker'@'localhost' IDENTIFIED BY '1r0nh4ck3r';
   GRANT ALL PRIVILEGES ON *.* TO 'ironhacker'@'localhost';
   FLUSH PRIVILEGES;
   ```
3. Run the MidTermProjectApplication.
4. Test the routes with Postman. All routes should start with **http://localhost:8080**

### Functionality
#### Create an account
There are four types of account that can be created.
To do so, run in Postman the following POST-route and include the input information in the body (for a template, see below):<br>
- **/create/savings** to create a Savings account
- **/create/creditcard** to create a Credit card account
- **/create/checking** to create a Checking account
- **/create/third-party** to create a third party account<br>
If the primary owner is younger than 24, a Student Checking account will automatically created, which means that 
the account does not have a monthly maintenance fee, nor a minimum balance.
<br><br>

##### Template to create a savings account (XXX are placeholders)
{ <br>
"balance": XXX, *(optional)*<br>
"penaltyFee":  XXX, *(optional)*<br>
"primaryOwner": { <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"name": XXX <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}, <br>
"secondaryOwner": XXX, *(optional)*<br>
"accountHolder": { <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"username": XXX,<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"password": XXX,<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"role": "ACCOUNT_HOLDER",<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"dateOfBirth": XXX,<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"primaryAddress": { <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;streetAddress: XXX <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;city: XXX <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;postalCode: XXX <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;},<br>
"mailingAddress": {<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;streetAddress: XXX *(optional)*<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;city: XXX *(optional)*<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;postalCode: XXX *(optional)*<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;} <br>
},<br>
"secretKey": XXX,<br>
"minimumBalance": XXX, *(optional)*<br>
"interestRate": XXX *(optional)*<br>
} <br>

##### Template to create a creditcard account (XXX are placeholders)
{ <br>
"balance": XXX, *(optional)*<br>
"penaltyFee":  XXX, *(optional)*<br>
"primaryOwner": { <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"name": XXX <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}, <br>
"secondaryOwner": XXX, *(optional)*<br>
"accountHolder": { <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"username": XXX,<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"password": XXX,<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"role": "ACCOUNT_HOLDER",<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"dateOfBirth": XXX,<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"primaryAddress": { <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;streetAddress: XXX <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;city: XXX <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;postalCode: XXX <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;},<br>
"mailingAddress": {<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;streetAddress: XXX *(optional)*<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;city: XXX *(optional)*<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;postalCode: XXX *(optional)*<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;} <br>
},<br>
"creditLimit": XXX *(optional)*, <br>
"interestRate": XXX *(optional)*<br>
}

##### Template to create a checking account (XXX are placeholders)
{ <br>
"balance": XXX, *(optional)*<br>
"penaltyFee":  XXX, *(optional)*<br>
"primaryOwner": { <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"name": XXX <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}, <br>
"secondaryOwner": XXX, *(optional)*<br>
"accountHolder": { <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"username": XXX,<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"password": XXX,<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"role": "ACCOUNT_HOLDER",<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"dateOfBirth": XXX,<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"primaryAddress": { <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;streetAddress: XXX <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;city: XXX <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;postalCode: XXX <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;},<br>
"mailingAddress": {<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;streetAddress: XXX *(optional)*<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;city: XXX *(optional)*<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;postalCode: XXX *(optional)*<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;} <br>
},<br>
"minimumBalance": XXX *(optional)*, <br>
"monthlyMaintenanceFee": XXX *(optional)* <br>
}

##### Template to create a third-party account (XXX are placeholders)
{ <br>
"name": XXX,<br>
"username": XXX,<br>
"password": XXX,<br>
"hashedKey": XXX,<br>
"role": {<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"name": "THIRD_PARTY"<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
}

#### Get a list of accounts and transactions
In order to get a list of all the accounts and transactions (only available for admins),
use the GET-route:<br>
- /savings
- /creditcard
- /checking
- /third_party
- /transactions

#### Get information on specific accounts and transactions
In order to get information on specific accounts and transactions,
use the GET-route:<br>
- /savings/{id}
- /creditcard/{id}
- /checking/{id}
- /third_party/{id}
- /transactions/{id}
#### Modify the balance of accounts
In order to modify the balance of specific accounts (only available for admins),
use the PATCH-route:<br>
- modify/savings/{id}
- modify/creditcard/{id}
- modify/checking/{id}
#### Transfer money for account holders
If an account holder wants to transfer money, (s)he must fill in the login data in the authorization fields
and use the PATCH-route:<br>
- /transfer/{accountType}/{value}/{owner}/{id}
#### Transfer money for third parties
If a third party account holder wants to transfer money, (s)he must fill in the login data in the authorization fields,
add the hashed key in the header and use the PATCH-route:<br>
- /transfer/third_party <br>
with the parameter:<br>
- values
- id
- secretKey *(of the account recipient, which is null when sending to another third party account)*
#### Interest rate
Interest rate will be added - if applicable - through the PATCH-route:<br>
- /savings/interest/{id}
- /creditcard/interest/{id}<br>
#### Other information
- If an account drops below the minimumBalance, the penaltyFee will be deducted from the balance automatically.
- The application recognizes patterns that indicate fraud and freezes the account status when potential fraud is detected.




