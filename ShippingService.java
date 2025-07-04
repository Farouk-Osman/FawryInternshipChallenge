import java.util.List;

public class ShippingService {
    private static final double SHIPPING_FEE_PER_ITEM = 15.0;

    public static void ship(List<Shippable> shippables) {
        if (shippables.isEmpty()) return;

        System.out.println("** Shipment notice **");
        double totalWeight = 0;
        for (Shippable item : shippables) {
            System.out.println(item.getName() + " " + item.getWeight() + "kg");
            totalWeight += item.getWeight();
        }
        System.out.printf("Total package weight %.1fkg\n", totalWeight);
    }

    public static double calculateShippingFee(int itemCount) {
        return itemCount * SHIPPING_FEE_PER_ITEM;
    }
}
