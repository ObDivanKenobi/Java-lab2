package gui;

import classes.DataModels.*;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 * Created by Илья on 03.06.2017.
 */
public class ItemTableModel extends DefaultTableModel{
    static Object[] columns = new Object[] {"Параметр", "Значение"};

    GoodsTypes type;

    public ItemTableModel(Goods item) {
        super(null, columns);

        type = item.getType();
        setDataVector(item.toTableModelDataSource(), columns);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        //return row !=0 && column != 0 && getValueAt(row, 0) != "Тип" && getValueAt(row, 0) != "Тип доспеха";
        return false;
    }

    public boolean updateValue(Goods item) {
        int price;
        String name;
        try {
            name = (String)getValueAt(0, 1);
            price = Integer.parseInt((String)getValueAt(0, 2));
            switch(type) {
                case Sword: {
                    float bladeLength = Float.parseFloat((String)getValueAt(0,4 ));
                    float fullLength = Float.parseFloat((String)getValueAt(0,5 ));
                    float guardWidth = Float.parseFloat((String)getValueAt(0,6 ));
                    float weight = Float.parseFloat((String)getValueAt(0,7 ));
                    Sword s = (Sword)item;
                    s.setName(name);
                    s.setPrice(price);
                    s.setBladeLength(bladeLength);
                    s.setFullLength(fullLength);
                    s.setGuardWidth(guardWidth);
                    s.setWeight(weight);
                    break;
                }
                case Armor: {
                    ArmorTypes armorType = ArmorTypes.valueOf((String)getValueAt(0, 4));
                    String description = (String)getValueAt(0, 5);
                    Armor a = (Armor)item;
                    a.setArmorType(armorType);
                    a.setName(name);
                    a.setPrice(price);
                    a.setDescription(description);
                    break;
                }
                case Shield: {
                    String shape = (String)getValueAt(0, 4),
                           material = (String)getValueAt(0, 5),
                           description = (String)getValueAt(0, 6);
                    Shield s = (Shield)item;
                    s.setName(name);
                    s.setPrice(price);
                    s.setShape(shape);
                    s.setMaterial(material);
                    s.setDescription(description);
                    break;
                }
                default: {
                    return false;
                }
            }
        }
        catch (Exception e){
            return false;
        }

        return true;
    }

    public Goods getValue() {
        int id, price;
        String name;
        try {
            id = Integer.parseInt((String)getValueAt(0, 0));
            name = (String)getValueAt(0, 1);
            price = Integer.parseInt((String)getValueAt(0, 2));
            switch(type) {
                case Sword: {
                    float bladeLength = Float.parseFloat((String)getValueAt(0,4 ));
                    float fullLength = Float.parseFloat((String)getValueAt(0,5 ));
                    float guardWidth = Float.parseFloat((String)getValueAt(0,6 ));
                    float weight = Float.parseFloat((String)getValueAt(0,7 ));
                    return new Sword(id, name, price, type, bladeLength, fullLength, guardWidth, weight);
                }
                case Armor: {
                    ArmorTypes armorType = ArmorTypes.valueOf((String)getValueAt(0, 4));
                    String description = (String)getValueAt(0, 5);
                    return new Armor(id, name, price, type, armorType, description);
                }
                case Shield: {
                    String shape = (String)getValueAt(0, 4),
                           material = (String)getValueAt(0, 5),
                           description = (String)getValueAt(0, 6);
                    return new Shield(id, name, price, type, shape, material, description);
                }
                default: {
                    return null;
                }
            }
        }
        catch (Exception e){
            return null;
        }
    }
}
