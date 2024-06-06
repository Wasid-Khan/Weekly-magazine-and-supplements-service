import java.util.ArrayList;

/**
 * An extension of the Customer class, representing an associate customer.
 * An associate customer is one who has another customer pay for his or her
 * subscription fees.
 * @author wakhan
 */
public class AssociateCustomer extends Customer {
  public AssociateCustomer(String t_name, String t_email) {
    name = t_name;
    email = t_email;
    supplements = new ArrayList<String>();
    id = createID();
  }
}
