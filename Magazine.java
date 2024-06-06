import java.util.ArrayList;

/**
 * Determines attributes like name and cost of the magazine and manages
 * supplements.
 * @author wakhan
 */
public class Magazine {
  private final String NAME = "Programming Weekly";
  private final double COST = 5;
  private ArrayList<Supplement> supplements;

  /**
   * Constructor
   */
  public Magazine() {
    supplements = new ArrayList<Supplement>();
  }

  /**
   * @return Returns magazine name
   */
  public String getName() {
    return NAME;
  }

  /**
   * @return Returns magazine cost
   */
  public double getCost() {
    return COST;
  }

  /**
   * Adds a Supplement instance to records.
   * @param name Name of supplement
   * @param cost Cost of supplement
   */
  public void addSupplement(String name, double cost) {
    supplements.add(new Supplement(name, cost));
  }

  /**
   * @return Returns list of Supplement instances.
   */
  public ArrayList<Supplement> getSupplements() {
    return supplements;
  }

  /**
   * Check that the magazine has a supplement thatt matches the provided ID.
   * @param id Supplement ID.
   * @return Returns true if a match is found, else false.
   */
  public Boolean hasSupplement(String id) {
    for (int i = 0; i < supplements.size(); i++) {
      if (id.equals(supplements.get(i).getID())) {
        return true;
      }
    }

    return false;
  }

  /**
   * Gets the name of the supplement that matches the provided ID.
   * @param id Supplement ID
   * @return Returns supplement name if a match is found, else an empty string.
   */
  public String getSupplementName(String id) {
    String match = "";

    for (int i = 0; i < supplements.size(); i++) {
      if (id.equals(supplements.get(i).getID())) {
        return supplements.get(i).getName();
      }
    }

    return match;
  }

  /**
   * Gets the cost of the supplement that matches the provided ID.
   * @param id Supplement ID
   * @return Returns supplement cost if a match is found, else -1.
   *         We assume that supplements can be free.
   */
  public double getSupplementCost(String id) {
    double cost = -1;

    for (int i = 0; i < supplements.size(); i++) {
      if (id.equals(supplements.get(i).getID())) {
        return supplements.get(i).getCost();
      }
    }

    return cost;
  }

  /**
   * Calculates cost of magazine along with selected supplements.
   * @param supplementIDs List of supplement IDs
   * @return Total cost of magazine price + supplement price
   */
  public double calculateCost(ArrayList<String> supplementIDs) {
    double total = getCost();

    for (int i = 0; i < supplementIDs.size(); i++) {
      total += getSupplementCost(supplementIDs.get(i));
    }

    return total;
  }
}
