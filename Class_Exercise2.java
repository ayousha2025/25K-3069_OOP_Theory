/* ======================== Theoritical Questions ======================= */

/* c) Prevent Instantiation of Watch
  How can you ensure that an object of class Watch cannot be created directly?
  Explain the Java concept used and why it is suitable in this scenario.

Answer: We will make the Watch class abstract to prevent instantation.
This is suitable because Watch is general real world entity and luxury and
non luxury watches are its types.   */

/* d) Track Total Watches Produced
The shop wants to maintain a count of all watches created.
Provide a mechanism such that:
-> The count increases whenever a new watch object is created
-> Separate counters are maintained for:
   o Luxury Watches
   o Non-Luxury Watches
Explain which Java feature is used to achieve this.
Answer: The "static" keyword is used to achieve this mechanism. It actually belongs 
to a class not the object; therefore, whenever the constructor run , counter increases. */
abstract class Watch implements TaxCalculator {

    protected final int WatchID;
    protected double capitalCost;

    protected final double importDutyTax = 0.15;
    protected final double profitRate = 0.75;
    protected final double GST_Rate = 0.06;
    protected static int totalWatches = 0;

    public Watch(int WatchID, double capitalCost) {
        this.WatchID = WatchID;
        this.capitalCost = capitalCost;
        totalWatches++;
    }

    public abstract double calculateSalesTax(); // this function will be override

    public double calculateImportDuty() {
        return importDutyTax * capitalCost;
    }

    public double calculateGST(double amount) {
        return amount * GST_Rate;
    }

    public double calculateRetailPrice() {
        double importDuty = calculateImportDuty();
        double salesTax = calculateSalesTax();
        double totalCost = capitalCost + importDuty + salesTax;
        double profit = totalCost * profitRate;
        double price = totalCost + profit;
        double GST = calculateGST(price);
        return price + GST;

    }

    public double calculateRetailPrice(double discount) { // overloading
        return calculateRetailPrice() - discount;
    }
}

class LuxuryWatch extends Watch {

    public static int luxTotal = 0;
    public final double salesTaxRate = 0.10;

    public LuxuryWatch(int WatchID, double capitalCost) {
        super(WatchID, capitalCost);
        luxTotal++;
    }

    public double calculateSalesTax() {
        return capitalCost * salesTaxRate;
    }
}

class NonLuxuryWatch extends Watch {

    public static int NonluxTotal = 0;
    public final double salesTaxRate = 0.065;

    public NonLuxuryWatch(int WatchID, double capitalCost) {
        super(WatchID, capitalCost);
        NonluxTotal++;
    }
    public double calculateSalesTax() {
        return capitalCost * salesTaxRate;
    }

}

class Customer{

    private final String name;
    private final String address;

    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}

interface TaxCalculator{

    double calculateImportDuty();

    double calculateSalesTax();

    double calculateGST(double amount);
}

class Main{

    public static void main(String[] args) {
        Customer c1 = new Customer("Ali Khan", "Karachi");

        LuxuryWatch w1 = new LuxuryWatch(101, 50000);
        NonLuxuryWatch w2 = new NonLuxuryWatch(102, 20000);

        // Calculate prices
        double price1 = w1.calculateRetailPrice();
        double price2 = w2.calculateRetailPrice();

        // Overloaded method
        double priceAfterDiscount = w1.calculateRetailPrice(50000);

        System.out.println("Customer Name: " + c1.getName());
        System.out.println("Customer Address: " + c1.getAddress());
        System.out.println("\nLuxury Watch ID: " + w1.WatchID);
        System.out.println("Luxury Watch Retail Price: " + price1);
        System.out.println("\nNon Luxury Watch ID: " + w2.WatchID);
        System.out.println("Non Luxury Watch Retail Price: " + price2);

        System.out.println("\nLuxury Watch Price After Discount: " + priceAfterDiscount);
        // Overriding
        System.out.println("\nLuxury Watch Sales Tax: " + w1.calculateSalesTax());
        System.out.println("NonLuxury Watch Sales Tax: " + w2.calculateSalesTax());
    }

}
