import java.util.ArrayList;
import java.util.List;

public class StorageMySymbol {
    private List<MySymbol> symbolList;
    private Integer length;

    public StorageMySymbol() {
        symbolList = new ArrayList<MySymbol>();
        length = 0;
    }

    public void add(MySymbol mySymbol) {
        symbolList.add(mySymbol);
        length++;
    }

    public List<MySymbol> getSymbolList() {
        return symbolList;
    }

    public MySymbol getSymbolById(Integer index) {
        return symbolList.get(index);
    }

    public Integer getLength() {
        return length;
    }
}
