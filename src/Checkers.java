import java.util.HashMap;

public class Checkers {

    public boolean epicsHasSubTaskId(HashMap<Integer, Epic> epics, int id) {
        return epics.get(id) != null;
    }

}
