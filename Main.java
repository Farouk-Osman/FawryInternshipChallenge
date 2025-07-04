import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ShippableProduct cheese = new ShippableProduct("Cheese", 100, 10, 0.2);
        ShippableProduct tv = new ShippableProduct("TV", 300, 5, 5.0);
        Product scratchCard = new Product("Mobile Scratch Card", 50, 20) {};

        ExpirableProduct biscuits = new ExpirableProduct("Biscuits", 150, 3, LocalDate.now().plusDays(1));

        Customer customer = new Customer("Ali", 1000);

        Cart cart = new Cart();
        try {
            cart.add(cheese, 2);
            cart.add(tv, 1);
            cart.add(scratchCard, 1);
            cart.add(biscuits, 1);

            CheckoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
