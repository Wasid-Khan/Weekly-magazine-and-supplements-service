import java.util.ArrayList;
import java.util.Scanner;

/**
 * Object that manages input and displays output.
 * @author wakhan
 */
public class Client {
  private char userInput;
  private Boolean runSystem;
  private Scanner scanner;
  private Customers customers;
  private Magazine mag;

  /**
   * Constructor
   */
  public Client() {
    scanner   = new Scanner(System.in);
    mag       = new Magazine();
    customers = new Customers();
    runSystem = true;

    createSupplements();
    createCustomers();
    displayStudentDetails();
    promptMainOptions();
  }

  /**
   * Create initial data for program.
   * Satisfies requirement (a).
   */
  private void createCustomers() {
    AssociateCustomer c1 = new AssociateCustomer("wakhan66", "wakhan66@gmail.com");
    AssociateCustomer c2 = new AssociateCustomer("Ben Ong", "ben@gmail.com");
    AssociateCustomer c3 = new AssociateCustomer("Malinda Gate", "malinda@me.com");
    PayingCustomer    c4 = new PayingCustomer("Elon Jani", "elon@yahoo.com");
    PayingCustomer    c5 = new PayingCustomer("Imran Khan", "imran@gmail.com");

    c1.addSupplement("SUPP-1");
    c1.addSupplement("SUPP-1");
    c2.addSupplement("SUPP-1");
    c2.addSupplement("SUPP-2");
    c3.addSupplement("SUPP-0");
    c3.addSupplement("SUPP-2");
    c3.addSupplement("SUPP-3");
    c5.addSupplement("SUPP-0");
    c5.addSupplement("SUPP-1");
    c5.addSupplement("SUPP-2");

    c4.setPaymentMethod("Debit");
    c5.setPaymentMethod("Credit");
    c4.setPaymentNumber("111-11111-111");
    c5.setPaymentNumber("5555-5555-5555-5555");

    c4.addAssociate(c1.getID());
    c4.addAssociate(c2.getID());
    c4.addAssociate(c3.getID());

    customers.add(c1);
    customers.add(c2);
    customers.add(c3);
    customers.add(c4);
    customers.add(c5);
  }

  /**
   * Create initial data for program.
   * Satisfies requirement (b).
   */
  private void createSupplements() {
    mag.addSupplement("Python", 1.5);
    mag.addSupplement("C++", 1);
    mag.addSupplement("Java", 1.25);
    mag.addSupplement("JavaScript", 2);
  }

  /**
   * Display weekly email for all customers.
   * Satisfies requirement (c).
   * Performed by option [1].
   */
  private void showWeeklyEmails() {
    ArrayList<Customer> all = customers.getAll();

    for (int i = 0; i < all.size(); i++) {
      Customer customer = all.get(i);
      String supplementMessage = "No supplement(s).";

      ArrayList<String> supplementIDs = customer.getSupplements();

      if (supplementIDs.size() > 0) {
        for (int j = 0; j < supplementIDs.size(); j++) {
          if (j == 0) {
            supplementMessage = mag.getSupplementName(supplementIDs.get(j));
          } else {
            supplementMessage += ", ";
            supplementMessage += mag.getSupplementName(supplementIDs.get(j));
          }
        }
      }

      System.out.println("--------------------------------------------------------------------------------");
      System.out.printf("| Recipient: %-65s |\n", customer.getEmail());
      System.out.printf("| Hello %-70s |\n", customer.getName() + ",");
      System.out.printf("| Your weekly publication of %-49s |\n", String.format("\"%s\" is ready,", mag.getName()));
      System.out.printf("| along with the following supplement(s): %-36s |\n", supplementMessage);
    }

    System.out.println("--------------------------------------------------------------------------------");
  }

  /**
   * Display monthly billing email for paying customers.
   * Satisfies requirement (d).
   * Performed by option [2].
   */
  private void showMonthlyEmails() {
    Customer customer;
    PayingCustomer payer;
    AssociateCustomer associate;
    ArrayList<Customer> all = customers.getAll();

    for (int i = 0; i < all.size(); i++) {
      customer = all.get(i);

      if (customer.isPaying()) {
        payer = (PayingCustomer) customer;
        double customerCost = getCustomerCost(payer.getID());
        ArrayList<String> associates = payer.getAssociates();
        ArrayList<String> supplements = payer.getSupplements();

        System.out.println("----------------------------------------------------------------");
        System.out.printf("| %-20s | %-37s |\n", "Monthly bill for", String.format("%s <%s>", payer.getName(), payer.getEmail()));
        System.out.printf("| %-20s | %-37s |\n", "Payment method", payer.getPaymentMethod());
        System.out.printf("| %-20s | %-37s |\n", "Payment number", payer.getPaymentNumber());
        System.out.printf("| %-20s | %-37s |\n", "Cost breakdown", "Your subscription");
        System.out.printf("| %-20s | %-32s %4.2f |\n", "", "- Magazine", mag.getCost());

        for (int k = 0; k < supplements.size(); k++) {
          System.out.printf(
            "| %-20s | - %-30s %4.2f |\n",
            "",
            mag.getSupplementName(supplements.get(k)),
            mag.getSupplementCost(supplements.get(k))
          );
        }

        for (int j = 0; j < associates.size(); j++) {
          associate = (AssociateCustomer) customers.getCustomerByID(associates.get(j)).get(0);
          supplements = associate.getSupplements();

          System.out.printf("| %-20s | %-37s |\n", "", associate.getName());
          System.out.printf("| %-20s | %-32s %4.2f |\n", "", "- Magazine", mag.getCost());

          for (int m = 0; m < supplements.size(); m++) {
            System.out.printf(
              "| %-20s | - %-30s %4.2f |\n",
              "",
              mag.getSupplementName(supplements.get(m)),
              mag.getSupplementCost(supplements.get(m))
            );
          }

          customerCost += getCustomerCost(associates.get(j));
        }

        System.out.printf("| %-20s | %-37.2f |\n", "Grand total", customerCost);
        System.out.println("----------------------------------------------------------------");
      }
    }
  }

  /**
   * Collects input required for adding a new customer.
   * Satisfies requirement (e).
   * Performed by option [3].
   */
  private void promptAddCustomer() {
    String paying = "";
    String paymentMethodOption = "";
    String name = "";
    String email = "";
    String paymentMethod = "";
    String paymentNumber = "";
    String existingCustomerID = "";
    String supplementID;
    ArrayList<String> supplements = new ArrayList<String>();

    while (name.isEmpty()) {
      System.out.print("Customer's name: ");
      name = scanner.nextLine();
    }

    while (email.isEmpty()) {
      System.out.print("Customer's email: ");
      email = scanner.nextLine();
    }

    while (!paying.equals("y") && !paying.equals("n")) {
      System.out.print("Is the customer paying? (y/n) ");
      paying = scanner.nextLine();
    }

    // change questions based on whether the customer is paying or an associate
    if (paying.equals("y")) {
      while (!paymentMethodOption.equals("0") && !paymentMethodOption.equals("1")) {
        System.out.println("Select customer's payment method:");
        System.out.println("[0] Credit");
        System.out.println("[1] Debit");
        System.out.print("Select option: ");
        paymentMethodOption = scanner.nextLine();
      }

      if (paymentMethodOption.equals("0")) {
        paymentMethod = "Credit";
      } else {
        paymentMethod = "Debit";
      }

      while (paymentNumber.isEmpty()) {
        System.out.print("Customer's payment number: ");
        paymentNumber = scanner.nextLine();
      }
    } else {
      while (true) {
        System.out.print("ID of existing customer paying for new customer: ");
        existingCustomerID = scanner.nextLine();

        if (customers.isValidPayer(existingCustomerID)) {
          break;
        } else {
          System.out.printf("\"%s\" is not a valid paying customer ID.\n", existingCustomerID);
        }
      }
    }

    // select supplement(s) that customer will subscribe to
    while (true) {
      System.out.printf("Enter a supplement ID or an empty entry to skip/continue: ");
      supplementID = scanner.nextLine();

      if (supplementID.equals("")) {
        break;
      } else {
        if (mag.hasSupplement(supplementID)) {
          System.out.printf("** \"%s\" added.\n", supplementID);
          supplements.add(supplementID);
        } else {
          System.out.printf("\"%s\" is not a valid supplement.\n", supplementID);
        }
      }
    }

    customers.add(
      name,
      email,
      supplements,
      paying.equals("y"),
      paymentMethod,
      paymentNumber,
      existingCustomerID
    );

    System.out.println("** Customer added.");
  }

  /**
   * Displays list of customers.
   * Performed by option [5].
   */
  private void listCustomers() {
    Customer customer;
    ArrayList<Customer> all = customers.getAll();
    ArrayList<String> supplements;

    for (int i = 0; i < all.size(); i++) {
      customer = all.get(i);
      supplements = customer.getSupplements();
      System.out.println("-------------------------------------------------------");
      System.out.printf("| %-16s | %-32s |\n", "ID",            customer.getID());
      System.out.printf("| %-16s | %-32s |\n", "Name",          customer.getName());
      System.out.printf("| %-16s | %-32s |\n", "Email",         customer.getEmail());
      System.out.printf("| %-16s | %-32s |\n", "Supplement(s)", supplements.size() > 0 ? supplements : "None");

      if (customer.isPaying()) {
        PayingCustomer payer = (PayingCustomer) customer;
        ArrayList<String> associates = payer.getAssociates();
        System.out.printf("| %-16s | %-32s |\n", "Payment method", payer.getPaymentMethod());
        System.out.printf("| %-16s | %-32s |\n", "Payment number", payer.getPaymentNumber());
        System.out.printf("| %-16s | %-32s |\n", "Associate(s)", associates.size() > 0 ? associates : "None");
      }
    }

    System.out.println("-------------------------------------------------------");
  }

  /**
   * Displays list of supplements.
   * Performed by option [6].
   */
  private void listSupplements() {
    ArrayList<Supplement> supplements = mag.getSupplements();
    Supplement supplement;

    for (int i = 0; i < supplements.size(); i++) {
      supplement = supplements.get(i);
      System.out.println("-------------------------------------------------------");
      System.out.printf("| %-8s | %-40s |\n",   "ID",   supplement.getID());
      System.out.printf("| %-8s | %-40s |\n",   "Name", supplement.getName());
      System.out.printf("| %-8s | %-40.2f |\n", "Cost", supplement.getCost());
    }

    System.out.println("-------------------------------------------------------");
  }

  /**
   * Breaks loop and exits program.
   * Performed by option [q].
   */
  private void exitSystem() {
    System.out.println("Exiting system.");
    runSystem = false;
  }

  /**
   * Display prompt for customer ID of record to be removed.
   */
  private void promptRemoveCustomer() {
    System.out.print("Enter customer ID: ");
    String customerID = scanner.nextLine();

    if (customers.removeCustomer(customerID) == 1) {
      System.out.format("** Customer \"%s\" removed.\n", customerID);
    } else {
      System.out.format("** Customer \"%s\" not found. No record removed.\n", customerID);
    }
  }

  /**
   * Display prompt, after performing action, to continue or exit program.
   */
  private void promptContinue() {
    System.out.print("Enter any key to continue or [q] to exit: ");

    if (scanner.nextLine().equals("q")) {
      exitSystem();
    }
  }

  /**
   * Display program's main options.
   */
  private void promptMainOptions() {
    while (runSystem) {
      System.out.println("-------------------------------------------------------");
      System.out.printf("Welcome to the \"%s\" management system.\n", mag.getName());
      System.out.println("Please select an option below:");
      System.out.println("[1] Show weekly email for all customers.");
      System.out.println("[2] Show monthly email for paying customers.");
      System.out.println("[3] Add a customer.");
      System.out.println("[4] Remove a customer.");
      System.out.println("[5] List all customers.");
      System.out.println("[6] List all supplements.");
      System.out.println("[q] Exit the system.");
      System.out.println("-------------------------------------------------------");

      System.out.print("Select option: ");
      userInput = scanner.next().charAt(0);

      runOption(userInput);
    }
  }

  /**
   * Performs action designated to option.
   * @param {char} option
   */
  private void runOption(char option) {
    // reset line from input from options prompt, otherwise nextLine is skipped.
    scanner.nextLine();

    switch (option) {
      case '1':
        showWeeklyEmails();
        promptContinue();
        break;
      case '2':
        showMonthlyEmails();
        promptContinue();
        break;
      case '3':
        promptAddCustomer();
        promptContinue();
        break;
      case '4':
        promptRemoveCustomer();
        promptContinue();
        break;
      case '5':
        listCustomers();
        promptContinue();
        break;
      case '6':
        listSupplements();
        promptContinue();
        break;
      case 'q':
        exitSystem();
        break;
      default:
        System.out.println("** Invalid option selected.");
        break;
    }
  }

  /**
   * Gets total cost of customer's subscriptions (magazine + supplements).
   * @param customerID Customer ID
   * @return Cost of subscription(s). Returns 0 if an invalid customer ID is provided.
   */
  private double getCustomerCost(String customerID) {
    double cost = 0;
    ArrayList<Customer> matches = customers.getCustomerByID(customerID);

    if (matches.size() > 0) {
      Customer customer = matches.get(0);
      ArrayList<String> supplements = customer.getSupplements();
      cost += mag.calculateCost(supplements);
    }

    return cost;
  }

  /**
   * Show student information, as instructed by assignment brief.
   */
  private void displayStudentDetails() {
    System.out.println("-------------------------------------------------------");
    System.out.printf("| %-51s |\n", "ICT373 Software Architecture Assignment 1 - Question 2");
    System.out.println("|-----------------------------------------------------|");
    System.out.printf("| %-16s | %-32s |\n", "Name", "Benedict Jay Ong"); //name of the student
    System.out.printf("| %-16s | %-32s |\n", "Student ID", "CT0337221"); // Enrollement ID
    System.out.printf("| %-16s | %-32s |\n", "Enrolment mode", "External"); // enrolment mode (External,Internal)
    System.out.printf("| %-16s | %-32s |\n", "Tutor", "Dr.Ferdous Sohel"); // tutor name
    System.out.printf("| %-16s | %-32s |\n", "Class", "PT A");
    System.out.println("-------------------------------------------------------");
  }
}
