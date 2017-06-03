package classes.DataModels;

/**
 * Created by Илья on 21.05.2017.
 */
public class Shield extends Goods {
    private String shape;
    private String material;
    private String description;

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Shield (int id, String name, int price, GoodsTypes type, String shape,
                   String material, String description)
    {
        super(id, name, price, type);
        this.shape = shape;
        this.material = material;
        this.description = description;
    }

    @Override
    public Object[][] toTableModelDataSource() {
        return new Object[][] {
                { "ID", getID()},
                { "Название", getName()},
                { "Цена", getPrice()},
                { "Тип", getType()},
                { "Форма", shape},
                { "Материал", material},
                { "Описание", description}
        };
    }

    @Override
    public String toString() {
        return String.format("%d|%s|%d|%s|%s|%s|%s\n", getID(), getName(), getPrice(), getType().toString(),
                            shape, material, description );
    }
}
