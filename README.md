# Weekly Magazine and Supplements Service

## Project Description

This project implements a set of classes to manage an online weekly personalized magazine service. The service allows customers to receive a main magazine along with optional supplements each week. The application supports different types of customers, including paying customers and associate customers whose subscriptions are paid for by a specified paying customer. The project is designed to handle customer subscriptions, payment methods, and weekly/monthly email notifications about magazine availability and charges.

## Features

- **Customer Management**: Add, remove, and manage customers and their subscription details.
- **Payment Methods**: Handle different payment methods, including credit card and direct debit.
- **Subscriptions**: Manage weekly supplements and calculate costs for each customer.
- **Email Notifications**: Send weekly emails to customers and monthly billing emails to paying customers.
- **Test Program**: A client program that demonstrates the functionalities of the service by simulating customer interactions and displaying email notifications.

## Class Structure

### Customer
- **Attributes**: `name`, `email`, `supplements`
- **Methods**: Constructor, getters, setters, email generation.

### PayingCustomer (extends Customer)
- **Attributes**: `paymentMethod`, `associateCustomers`
- **Methods**: Constructor, getters, setters, monthly billing email generation.

### AssociateCustomer (extends Customer)
- **Attributes**: `payingCustomer`
- **Methods**: Constructor, getters, setters.

### PaymentMethod
- **Attributes**: Abstract class for different payment methods.
- **Methods**: Abstract methods for handling payments.

### CreditCard (extends PaymentMethod)
- **Attributes**: `cardNumber`, `expiryDate`
- **Methods**: Constructor, getters, setters.

### DirectDebit (extends PaymentMethod)
- **Attributes**: `bankAccount`, `bankName`
- **Methods**: Constructor, getters, setters.

### Supplement
- **Attributes**: `name`, `weeklyCost`
- **Methods**: Constructor, getters, setters.

### Magazine
- **Attributes**: `weeklyCost`, `supplements`
- **Methods**: Constructor, getters, setters.

## Usage

### Test Program
The client program demonstrates the functionality of the magazine service. It includes:

1. Constructing a magazine with an array of 3-4 supplements.
2. Constructing an array of 5-6 different customers with made-up details.
3. Printing out weekly email notifications for all customers for four weeks.
4. Printing out end-of-month billing emails for paying customers.
5. Adding a new customer to the magazine service.
6. Removing an existing customer from the magazine service.

### Example Outputs
The program outputs various information during its operation, such as:

- Weekly emails to customers detailing their magazine and supplements.
- Monthly billing emails to paying customers with itemized costs.
- Information about adding and removing customers.
