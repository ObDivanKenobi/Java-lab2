package classes.DataModels;

import java.util.Arrays;

/**
 * Created by Илья on 21.05.2017.
 */
public class Sword extends Goods {
    private float bladeLength = Float.NaN;
    private float fullLength = Float.NaN;
    private float guardWidth = Float.NaN;
    private float weight = Float.NaN;

    public float getBladeLength() { return bladeLength; }

    public void setBladeLength(float bladeLength) { this.bladeLength = bladeLength; }

    public float getFullLength() { return fullLength; }

    public void setFullLength(float fullLength) { this.fullLength = fullLength; }

    public float getGuardWidth() { return guardWidth; }

    public void setGuardWidth(float guardWidth) { this.guardWidth = guardWidth; }

    public float getWeight() { return weight; }

    public void setWeight(float weight) { this.weight = weight; }

    public Sword(int id, String name, int price, GoodsTypes type, float blade_length,
                 float full_length, float guard_width, float weight )
    {
        super(id, name, price, type);
        bladeLength = blade_length;
        fullLength = full_length;
        guardWidth = guard_width;
        this.weight = weight;
    }

    @Override
    public Object[][] toTableModelDataSource() {
        return new Object[][] {
                { "ID", getID()},
                { "Название", getName()},
                { "Цена", getPrice()},
                { "Тип", getType()},
                { "Длина клинка", bladeLength},
                { "Общая длина", fullLength},
                { "Ширина гарды", guardWidth},
                {"Масса", weight}
        };
    }

    @Override
    public String toString() {
        return String.format("%d|%s|%d|%s|%f|%f|%f|%f\n", getID(), getName(), getPrice(), getType().toString(),
                             bladeLength, fullLength, guardWidth, weight);
    }
}
