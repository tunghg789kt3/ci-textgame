package novels;

import java.util.Arrays;
import java.util.List;

public class Story {
    public String id;
    public String text;
    public List<Choice> choises;

    public Story(String id, String text, Choice... choises) {
        this.id = id;
        this.text = text;
        this.choises = Arrays.asList(choises);
    }
}
