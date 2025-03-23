import java.util.HashMap;
import java.util.Map;
import org.jfugue.player.Player;

public class theSoundOfPI {

    public static void main(String[] args) {

        Map<Character, String> notes = new HashMap<>();
        notes.put('0', "C");
        notes.put('1', "D");
        notes.put('2', "E");
        notes.put('3', "F");
        notes.put('4', "G");
        notes.put('5', "A");
        notes.put('6', "B");
        notes.put('7', "G#");
        notes.put('8', "D#");
        notes.put('9', "E#");

        Player player = new Player();

        String sPi = Pi.pi(10000).toPlainString();
        for(int i = 0; i < sPi.length(); i++){
            if (sPi.charAt(i) != '.'){
                player.play(notes.get(sPi.charAt(i)));
            }
        }

    }

}
