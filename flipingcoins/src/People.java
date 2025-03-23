import java.util.ArrayList;
import java.util.Objects;

public class People {
    private final ArrayList<Person> people = new ArrayList<>();

    public People(int numOfPerson){
        for (int i = 0; i < numOfPerson; i++) {
            Person person = new Person(i);
            people.add(person);
        }
    }

    public void printPeople(){
        if (people.isEmpty()){
            System.out.println("No people");
        }
        for (Person person : people) {
            System.out.println(person.getId() + " : " + person.getCoin());
        }
    }

    public void updatePeople() {
        ArrayList<Person> personsToRemove = new ArrayList<>();
        for (Person person : people) {
            person.flipCoin();
            if (Objects.equals(person.getCoin(), "Tails")) {
                personsToRemove.add(person);
            }
        }
        people.removeAll(personsToRemove);
    }

}
