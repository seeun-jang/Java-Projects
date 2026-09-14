public class Potion {

    private String name;
    private int temperature;
    private int mana;
    private int toxicity;

    // 포션에 마법 에너지를 주입했을 때 내부 상태가 어떻게 변하는지 확인하고 안정성을 판정하는 시스템.

    public Potion(String name, int temperature, int mana, int toxicity) {
        this.name = name;
        setTemperature(temperature);
        setMana(mana);
        setToxicity(toxicity);
    }

    // Getter
    public String getName() {
        return name;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getMana() {
        return mana;
    }

    public int getToxicity() {
        return toxicity;
    }

    // Setter
    public void setTemperature(int temperature) {
        if (temperature < 0) {
            this.temperature = 0;
        } else if (temperature > 100) {
            this.temperature = 100;
        } else {
            this.temperature = temperature;
        }
    }

    public void setMana(int mana) {
        if (mana < 0) {
            this.mana = 0;
        } else if (mana > 100) {
            this.mana = 100;
        } else {
            this.mana = mana;
        }
    }

    public void setToxicity(int toxicity) {
        if (toxicity < 0) {
            this.toxicity = 0;
        } else if (toxicity > 100) {
            this.toxicity = 100;
        } else {
            this.toxicity = toxicity;
        }
    }

    // 마법 에너지 주입
    public void infuseMagic() {
        System.out.println("\nApplying magical energy to " + name + "...");
        System.out.println("Potion reaction detected!");

        setTemperature(temperature + 15);
        setMana(mana + 10);
        setToxicity(toxicity + 5);

        System.out.println("Magic infusion complete!");
    }

    // 안정성 판정
    public String getStatus() {
        if (temperature >= 80) {
            return "OVERHEATED";
        } else if (toxicity >= 70) {
            return "TOXIC";
        } else if (mana < 20) {
            return "UNSTABLE";
        } else {
            return "STABLE";
        }
    }

    // 상태 출력
    public void showStatus() {
        System.out.println();
        System.out.println("+--------------------------------+");
        System.out.println("|         POTION STATUS          |");
        System.out.println("+--------------------------------+");
        System.out.printf("| %-12s : %-15s |\n", "Name", getName());
        System.out.printf("| %-12s : %-15d |\n", "Temperature", getTemperature());
        System.out.printf("| %-12s : %-15d |\n", "Mana", getMana());
        System.out.printf("| %-12s : %-15d |\n", "Toxicity", getToxicity());
        System.out.printf("| %-12s : %-15s |\n", "Status", getStatus());
        System.out.println("+--------------------------------+");
    }
}
