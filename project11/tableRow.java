public class tableRow {
    private String name;
    private String type;
    private String kind;
    private int num;
    public tableRow(String name, String type, String kind, int num) {
        this.name = name;
        this.type = type;
        this.kind = kind;
        this.num = num;

    }
    public String getName() {
        return name;

    }
    public String getType() {
        return type;
    }
    public String getKind() {
        return kind;
    }
    public int getNum() {
        return num;
    }

    public String toString() {
        return "tableRow {" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", kind='" + kind + '\'' +
                ", num=" + num +
                '}';
    }

}
