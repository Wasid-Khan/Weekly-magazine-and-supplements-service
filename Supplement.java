/**
 * A representation of a magazine supplement.
 * @author wakhan
 */
public class Supplement {
  private static int suppCount = 0;
  private String name;
  private double cost;
  private String id;

  /**
   * Constructors
   */
  public Supplement(String t_name, double t_cost) {
    id = "SUPP-" + (suppCount++);
    name = t_name;
    cost = t_cost;
  }

  /**
   * @return Returns customer's name
   */
  public String getID() {
    return id;
  }

  /**
   * @return Returns customer's email address
   */
  public String getName() {
    return name;
  }

  /**
   * @return Returns customer ID
   */
  public double getCost() {
    return cost;
  }
}
