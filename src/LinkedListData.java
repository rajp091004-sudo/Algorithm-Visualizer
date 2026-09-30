import java.util.List;

public class LinkedListData {

    private List<Integer> values;
    private LinkedListType type;

    public LinkedListData(
            List<Integer> values,
            LinkedListType type) {

        this.values = values;
        this.type = type;
    }

    public List<Integer> getValues() {
        return values;
    }

    public LinkedListType getType() {
        return type;
    }
}