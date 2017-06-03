package classes.DataModels;

/**
 * Created by Илья on 21.05.2017.
 */
public abstract class Goods {
    static Object[] GoodsFields = new Object[] {"ID", "Название", "Цена", "Тип"};

    private int ID;
    private String name;
    private int price;
    private GoodsTypes type;

    public int getID() { return ID; }

    public void setID(int ID) { this.ID = ID; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public GoodsTypes getType() { return type; }

    public void setType(GoodsTypes type) { this.type = type; }

    public Goods(int id, String name, int price, GoodsTypes type)
    {
        ID = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public Goods()
    {
        ID = -1;
        name = null;
        price = -1;
        type = GoodsTypes.Unset;
    }

    public abstract Object[][] toTableModelDataSource();

    @Override
    public abstract String toString();
}
