import java.util.HashMap;

public class symbolTable {
    private HashMap<String,tableRow> map;
    private int static_count;
    private int field_count;
    private int arg_count;
    private int var_count;

    public symbolTable ()
    {
        this.map = new HashMap<>();
        this.static_count = 0;
        this.field_count = 0;
        this.arg_count = 0;
        this.var_count = 0;
    }
    public int getSize()
    {
        return this.map.size();
    }
    public void reset ()
    {
        this.map.clear();
    }
    public boolean containsKey(String key)
    {
        return this.map.containsKey(key);
    }
    public tableRow get(String key)
    {
        return this.map.get(key);
    }
    public void define ( tableRow line)
    {
        if (map.containsKey(line.getName()))
        {

        }
        else
        {
            map.put(line.getName(), line);
            if (line.getKind().equals("static"))
            {
                static_count++;
            }
            else if (line.getKind().equals("this"))
            {
                field_count++;
            }
            else if (line.getKind().equals("argument"))
            {
                arg_count++;
            }
            else if (line.getKind().equals("local"))
            {
                var_count++;
            }

        }
    }
    public int getVar_count(String kind)
    {
        if (kind.equals("static"))
        {
            return static_count;
        }
        else if (kind.equals("this"))
        {
            return field_count;
        }
        else if (kind.equals("argument"))
        {
            return arg_count;
        }
        else
        {
            return var_count;
        }
    }
    public String kindOf (String name)
    {
        if (map.containsKey(name))
        {
            return map.get(name).getKind();
        }
        else {
            return "none";
        }
    }
    public String typeOf (String name)
    {
        if (map.containsKey(name))
        {
            return map.get(name).getType();
        }
        else {
            return "none";
        }
    }
    public int indexOf (String name)
    {
        if (map.containsKey(name))
        {
            return map.get(name).getNum();
        }
        else {
            return -1;
        }
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("symbolTable {\n");
        sb.append("  entries=[\n");
        for (tableRow row : map.values()) {
            sb.append("    ").append(row.toString()).append(",\n");
        }
        sb.append("  ]\n");
        sb.append("}");
        return sb.toString();
    }



}


