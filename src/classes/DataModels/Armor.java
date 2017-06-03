package classes.DataModels;

/**
 * Created by Илья on 21.05.2017.
 */
public class Armor extends Goods {
    private ArmorTypes armorType = ArmorTypes.Unset;
    private String description;

    public ArmorTypes getArmorType() {
        return armorType;
    }

    public void setArmorType(ArmorTypes armorType) {
        this.armorType = armorType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Armor(int id, String name, int price, GoodsTypes type, ArmorTypes armor_type,
                 String description)
    {
        super(id, name, price, type);
        this.armorType = armor_type;
        this.description = description;
    }

    @Override
    public Object[][] toTableModelDataSource() {
        return new Object[][] {
                { "ID", getID()},
                { "Название", getName()},
                { "Цена", getPrice()},
                { "Тип", getType()},
                { "Тип доспеха", armorType},
                { "Описание", description}
        };
    }

    @Override
    public String toString() {
        return String.format("%d|%s|%d|%s|%s|%s\n", getID(), getName(), getPrice(), getType().toString(),
                armorType.toString(), description );
    }
}
