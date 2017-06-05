package gui;

import classes.DataModels.Goods;
import classes.DataModels.GoodsTypes;
import classes.GoodsList;
import com.sun.javaws.exceptions.InvalidArgumentException;

import javax.swing.table.DefaultTableModel;
import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Created by Илья on 02.06.2017.
 */
public class MainTableModel extends DefaultTableModel {
    private GoodsList goods;
    private GoodsList shownGoods;
    private boolean isFiltered = false;;
    private boolean isSorted = false;
    private Predicate<Goods> filter = null;
    private Comparator<Goods> comparer = null;

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

    public MainTableModel(GoodsList goods) {
        super();
        this.goods = goods;
        shownGoods = goods;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public void removeRow(int row) {
        Goods item = shownGoods.getItem(row);
        shownGoods.removeItem(row);
        if (isFiltered || isSorted)
            goods.removeItem(item);
        fireTableDataChanged();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch (columnIndex) {
            case 0: return int.class;
            case 1: return String.class;
            case 2: return int.class;
            case 3: return GoodsTypes.class;
            default: throw new IllegalArgumentException();
        }
    }

    @Override
    public int getColumnCount() { return 4;}

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0: return "ID";
            case 1: return "Наименование";
            case 2: return "Цена";
            case 3: return "Тип";
            default: throw new IllegalArgumentException();
        }
    }

    @Override
    public int getRowCount() {
        if (shownGoods != null)
            return shownGoods.count();

        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Goods g = shownGoods.getItem(rowIndex);

        switch (columnIndex) {
            case 0: return g.getID();
            case 1: return g.getName();
            case 2: return g.getPrice();
            case 3: return g.getType();
            default: return "";
        }
    }

    public void addItem(Goods g) {
        goods.addItem(g);
        shownGoods = new GoodsList(goods);
        if (isFiltered)
            shownGoods = shownGoods.filter(filter);
        if (isSorted)
            shownGoods = shownGoods.sort(comparer);

        fireTableDataChanged();
    }

    public void clear() {
        dataVector.clear();
    }

    public void filterByType(GoodsTypes type) {
        filter = g -> g.getType() == type;
        shownGoods = shownGoods.filter(filter);
        isFiltered = true;
        fireTableDataChanged();
    }

    public void unfilter() {
        filter = null;
        isFiltered = false;
        shownGoods = goods;
        fireTableDataChanged();
    }

    public void sort(Comparator<Goods> cmp) {
        isSorted = true;
        comparer = cmp;
        shownGoods = shownGoods.sort(cmp);
        fireTableDataChanged();
    }

    public void unsort() {
        comparer = null;
        isSorted = false;
        shownGoods = goods;
        fireTableDataChanged();
    }

    public GoodsList getBaseValuse() {
        return goods;
    }

    public GoodsList getSortedAndFilteredValues() {
        return shownGoods;
    }

    public Goods getAssignedValue(int row) throws ArrayIndexOutOfBoundsException{
        if (row < 0 || row > getRowCount())
            throw new ArrayIndexOutOfBoundsException();

        return shownGoods.getItem(row);
    }

    public void updateData(int row, Goods g) throws ArrayIndexOutOfBoundsException {
        if (row < 0 || row > getRowCount())
            throw new ArrayIndexOutOfBoundsException();

        Goods item = shownGoods.getItem(row);
        int index = goods.indexOf(item);
        shownGoods.setItem(row, g);
        if (isFiltered)
            goods.setItem(index, g);
        fireTableDataChanged();
    }
}
