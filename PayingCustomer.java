import java.util.ArrayList;

/**
 * An extension of the Customer class, representing a paying customer.
 * A paying customer is one who pays for the monthly subscription, including
 * any associates tagged with him or her.
 * @author wakhan
 */
public class PayingCustomer extends Customer {
  private String paymentMethod;
  private String paymentNumber;
  private ArrayList<String> associates;

  /**
   * Constructor
   */
  public PayingCustomer(String t_name, String t_email) {
    name        = t_name;
    email       = t_email;
    associates  = new ArrayList<String>();
    supplements = new ArrayList<String>();
    id = createID();
    paying = true;
  }

  /**
   * Defines customer's payment method.
   * @param method Method of payment: "Credit" or "debit"
   */
  public void setPaymentMethod(String method) {
    paymentMethod = method;
  }

  /**
   * Defines customer's account number to which payment will be charged.
   * @param number Customer's account number
   */
  public void setPaymentNumber(String number) {
    paymentNumber = number;
  }

  /**
   * @return Returns customer's selected payment method.
   */
  public String getPaymentMethod() {
    return paymentMethod;
  }

  /**
   * @return Returns customer's account number to which payment will be charged.
   */
  public String getPaymentNumber() {
    return paymentNumber;
  }

  /**
   * Adds associate customer ID to records.
   * @param id ID of associate customer
   * @return Returns 1 if successfully added, 0 if ID already exists in list.
   */
  public int addAssociate(String id) {
    if (associates.contains(id)) {
      return 0;
    }

    associates.add(id);
    return 1;
  }

  /**
   * @return Returns IDs of any associates in a list.
   */
  public ArrayList<String> getAssociates() {
    return associates;
  }
}
