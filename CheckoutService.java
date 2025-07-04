import java.util.ArrayList;
import java.util.List;

public class CheckoutService {
    public static void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Cannot checkout: Cart is empty.");
        }

        List<Shippable> shippables = new ArrayList<>();
        double subtotal = 0;

        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();

            if (!product.isAvailable(item.getQuantity())) {
                throw new IllegalStateException(product.getName() + " is out of stock.");
            }

            if (product instanceof ExpirableProduct) {
                ExpirableProduct exp = (ExpirableProduct) product;
                if (exp.isExpired()) {
                    throw new IllegalStateException(product.getName() + " is expired.");
                }
            }

            if (product instanceof Shippable) {
                for (int i = 0; i < item.getQuantity(); i++) {
                    shippables.add((Shippable) product);
                }
            }

            subtotal += item.getTotalPrice();
        }

        double shippingFee = ShippingService.calculateShippingFee(shippables.size());
        double total = subtotal + shippingFee;

        if (customer.getBalance() < total) {
            throw new IllegalStateException("Insufficient balance to complete purchase.");
        }

        customer.pay(total);

        for (CartItem item : cart.getItems()) {
            item.getProduct().reduceQuantity(item.getQuantity());
        }

        ShippingService.ship(shippables);

        System.out.println("** Checkout receipt **");
        for (CartItem item : cart.getItems()) {
            System.out.println(item.getQuantity() + "x " + item.getProduct().getName() + " " + item.getTotalPrice());
        }
        System.out.println("----------------------");
        System.out.println("Subtotal " + subtotal);
        System.out.println("Shipping " + shippingFee);
        System.out.println("Amount " + total);
        System.out.println("Remaining Balance: " + customer.getBalance());
    }
}
