import java.util.ArrayList;

public class MyBorder {
    private Integer minX;
    private Integer maxX;
    private Integer minY;
    private Integer maxY;
    private ArrayList<Integer> rows;

    public MyBorder() {
    }

    public MyBorder(Integer minX, Integer maxX, Integer minY, Integer maxY, ArrayList<Integer> rows) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.rows = rows;
    }

    public Integer getMinX() {
        return minX;
    }

    public void setMinX(Integer minX) {
        this.minX = minX;
    }

    public Integer getMaxX() {
        return maxX;
    }

    public void setMaxX(Integer maxX) {
        this.maxX = maxX;
    }

    public Integer getMinY() {
        return minY;
    }

    public void setMinY(Integer minY) {
        this.minY = minY;
    }

    public Integer getMaxY() {
        return maxY;
    }

    public void setMaxY(Integer maxY) {
        this.maxY = maxY;
    }

    public ArrayList<Integer> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Integer> rows) {
        this.rows = rows;
    }
}
