public class MagicPotionMain {
    public static void main(String[] args) {

        System.out.println("==================================");
        System.out.println("        MAGIC POTION LAB");
        System.out.println("==================================");

        Potion potion = new Potion(
                "Moon Elixir",
                60,
                70,
                20
        );

        System.out.println("\n[ Initial Status ]");
        potion.showStatus();

        potion.infuseMagic();

        System.out.println("\n[ After Magic Infusion ]");
        potion.showStatus();

        System.out.println("\nPotion stability test finished!");
    }
}
