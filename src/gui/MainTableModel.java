package gui;

import classes.DataModels.Goods;
import classes.GoodsList;

import javax.swing.table.DefaultTableModel;

/**
 * Created by Илья on 02.06.2017.
 */
public class MainTableModel extends DefaultTableModel {
    GoodsList goods;

    public MainTableModel(GoodsList goods, Object[] columns) {
        super();
        this.goods = goods;
        Object[][] data = new Object[goods.count()][columns.length];
        for (int i = 0; i < goods.count(); ++i) {
            Goods g = goods.getItem(i);
            data[i] = new Object[]{g.getID(), g.getName(), g.getPrice(), g.getType()};
        }

        setDataVector(data, columns);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public Goods getAssignedValue(int row){
        if (row < 0 || row > getRowCount())
            throw new ArrayIndexOutOfBoundsException();

        return goods.getItem(row);
    }
}
