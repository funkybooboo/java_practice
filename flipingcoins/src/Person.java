import java.util.Random;

public class Person {
    private final int id;
    private String coin;
    private final Random random = new Random();

    public Person(int id){
        this.id = id;
    }

    public void flipCoin(){
        if (random.nextBoolean()){
            coin = "Heads";
        } else{
            coin = "Tails";
        }
    }

    public String getCoin(){
        return coin;
    }

    public int getId(){
        return id;
    }

}
