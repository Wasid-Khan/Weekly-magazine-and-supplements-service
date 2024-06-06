import java.util.ArrayList;

/**
 * Abstract class that defines the basic functionality of the Customer instance.
 * @author wakhan
 */
abstract class Customer {
  protected static int custCount = 0;
  protected String email;
  protected String name;
  protected String id;
  protected ArrayList<String> supplements;
  protected Boolean paying = false;

  /**
   * Creates customer ID
   */
  protected String createID() {
    return "CUST-" + (custCount++);
  }

  /**
   * @return Returns customer's name
   */
  public String getName() {
    return name;
  }

  /**
   * @return Returns customer's email address
   */
  public String getEmail() {
    return email;
  }

  /**
   * @return Returns customer ID
   */
  public String getID() {
    return id;
  }

  /**
   * Get list of supplements that customer subscribes to
   * @return Returns array list of supplement IDs
   */
  public ArrayList<String> getSupplements() {
    return supplements;
  }

  /**
   * Adds supplement ID to records.
   * @param id Supplement ID
   * @return Returns 1 if successfully added, 0 if ID already exists in list.
   */
  public int addSupplement(String id) {
    if (supplements.contains(id)) {
      return 0;
    }

    supplements.add(id);
    return 1;
  }

  /**
   * @return Returns boolean indicating whether the customer is a paying one or
   *         an associate
   */
  public Boolean isPaying() {
    return paying;
  }
}
