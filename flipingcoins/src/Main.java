public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            People people = new People(1000);
            for (int j = 0; j < 10; j++) {
                people.updatePeople();
            }
            people.printPeople();
            System.out.println();
        }
    }
}
