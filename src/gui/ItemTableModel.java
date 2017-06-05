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
}
