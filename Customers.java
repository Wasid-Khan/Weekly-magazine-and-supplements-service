import java.util.ArrayList;

/**
 * Manages Customer (and its sub-classes) instances.
 * @author wakhan
 */
public class Customers {
  private ArrayList<Customer> all;

  /**
   * Constructor
   */
  public Customers() {
    all = new ArrayList<Customer>();
  }

  /**
   * Add a customer to records.
   * @param name             Customer's name
   * @param email            Customer's email address
   * @param supplements      List of supplement IDs. Can be an empty ArrayList.
   * @param paying           Determines if we create an AssociateCustomer or PayingCustomer instance
   * @param paymentMethod    Method of payment: credit or debit
   * @param paymentNumber    Account number
   * @param payingCustomerID ID of PayingCustomer if "paying" argument is false
   */
  public void add(
    String name,
    String email,
    ArrayList<String> supplements,
    Boolean paying,
    String paymentMethod,
    String paymentNumber,
    String payingCustomerID
  ) {
    if (paying) {
      PayingCustomer newCustomer = new PayingCustomer(name, email);
      newCustomer.setPaymentMethod(paymentMethod);
      newCustomer.setPaymentNumber(paymentNumber);

      for (int i = 0; i < supplements.size(); i++) {
        newCustomer.addSupplement(supplements.get(i));
      }

      all.add(newCustomer);
    } else {
      AssociateCustomer newCustomer = new AssociateCustomer(name, email);

      for (int i = 0; i < supplements.size(); i++) {
        newCustomer.addSupplement(supplements.get(i));
      }

      ArrayList<Customer> matches = getCustomerByID(payingCustomerID);

      if (matches.size() > 0) {
        PayingCustomer payer = (PayingCustomer) matches.get(0);
        payer.addAssociate(newCustomer.getID());
      }

      all.add(newCustomer);
    }
  }

  /**
   * Add an AssociateCustomer instance to records.
   * @param associate AssociateCustomer instance
   */
  public void add(AssociateCustomer associate) {
    all.add(associate);
  }

  /**
   * Add a PayingCustomer instance to records.
   * @param payer PayingCustomer instance
   */
  public void add(PayingCustomer payer) {
    all.add(payer);
  }

  /**
   * Removes a customer from record.
   * @param id ID of the customer
   * @return Returns 1 if successfully removed, else 0.
   */
  public int removeCustomer(String id) {
    for (int i = 0; i < all.size(); i++) {
      Customer customer = all.get(i);

      if (id.equals(customer.getID())) {
        // an associate customer; remove from paying customer's associates list
        if (!customer.isPaying()) {
          String PayerID = getAssociatePayer(id);
          ArrayList<Customer> matches = getCustomerByID(PayerID);

          if (matches.size() > 0) {
            PayingCustomer payer = (PayingCustomer) matches.get(0);
            ArrayList<String> associates = payer.getAssociates();
            int associateIndex = associates.indexOf(id);

            if (associateIndex > -1) {
              associates.remove(associateIndex);
            }
          }
        }

        all.remove(i);
        return 1;
      }
    }

    return 0;
  }

  /**
   * Checks if a customer is a payer.
   * @param id ID of the customer
   * @return Returns true if the customer is a payer, else false.
   */
  public Boolean isPayer(String id) {
    return false;
  }

  /**
   * Gets a Customer instance via ID.
   * @param id ID of the customer
   * @return Returns an array of customers that match the ID. size() can be 0 or 1.
   */
  public ArrayList<Customer> getCustomerByID(String id) {
    ArrayList<Customer> matches = new ArrayList<Customer>();

    for (int i = 0; i < all.size(); i++) {
      if (id.equals(all.get(i).getID())) {
        matches.add(all.get(i));
      }
    }

    return matches;
  }

  /**
   * Get all customers in a merged array list with instances upcasted to Customer.
   */
  public ArrayList<Customer> getAll() {
    return all;
  }

  /**
   * Checks if the selected customer is a paying customer.
   * @param id Customer ID
   * @return Returns true if the customer is a paying customer, else false.
   */
  public Boolean isValidPayer(String id) {
    Customer customer;

    for (int i = 0; i < all.size(); i++) {
      customer = all.get(i);

      if (id.equals(customer.getID()) && customer.isPaying()) {
        return true;
      }
    }

    return false;
  }

  /**
   * Get ID of customer who is paying for the associate.
   * @param associateID ID of associate customer
   * @return Customer ID if a match is found, else an empty string.
   */
  private String getAssociatePayer(String associateID) {
    String match = "";

    for (int i = 0; i < all.size(); i++) {
      if (all.get(i).isPaying()) {
        PayingCustomer customer = (PayingCustomer) all.get(i);
        ArrayList<String> associates = customer.getAssociates();

        if (associates.indexOf(associateID) > -1) {
          return customer.getID();
        }
      }
    }

    return match;
  }
}
