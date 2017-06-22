package gui;

import classes.DataModels.*;
import classes.FileHandler;
import classes.GoodsList;
import com.bulenkov.darcula.DarculaLaf;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicLookAndFeel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;

/**
 * Created by Илья on 20.05.2017.
 */
public class MainForm {
    private JPanel panel1;
    private JButton buttonLoadFromFile;
    private JButton buttonLoadFromDB;
    private JButton buttonSaveToFile;
    private JButton buttonSaveToDB;
    private JComboBox comboBoxFilterByType;
    private JButton buttonFilter;
    private JComboBox comboBoxSortField;
    private JButton buttonSort;
    private JTable tableGoods;
    private JButton buttonAdd;
    private JButton buttonDelete;
    private JButton buttonClear;
    private JButton buttonShowItem;
    private JComboBox comboBoxItemTypes;
    private JButton buttonEdit;
    private JButton buttonRemoveFilter;
    private JButton buttonRemoveSorting;
    private JComboBox comboBoxSortOrdering;
    private JFrame frame;

    private GoodsList goods = new GoodsList();
    private MainTableModel model = new MainTableModel();//, columnNames);

    public MainForm() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
            UIManager.put("FileChooser.cancelButtonText", "Отмена");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            return;
        }

/*        BasicLookAndFeel laf = new DarculaLaf();
        try {
            UIManager.setLookAndFeel(laf);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }*/

        tableGoods.setModel(model);
        tableGoods.setFillsViewportHeight(true);

        for (GoodsTypes type: GoodsTypes.values())
            if (type != GoodsTypes.Unset) {
                comboBoxItemTypes.addItem(type.toString());
                comboBoxFilterByType.addItem(type.toString());
            }

        comboBoxSortField.addItem("ID");
        comboBoxSortField.addItem("наименованию");
        comboBoxSortField.addItem("цене");
        comboBoxSortField.addItem("типу");

        comboBoxSortOrdering.addItem("по возрастанию");
        comboBoxSortOrdering.addItem("по убыванию");

        buttonLoadFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnLoadFromFile_Click(e);
            }
        });
        buttonShowItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnShowOrEditItem_Click(e);
            }
        });
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAddItem_Click(e);
            }
        });
        buttonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnEditItem_Click(e);
            }
        });
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnRemoveItem_Click(e);
            }
        });
        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnClear_Click(e);
            }
        });
        buttonFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnFilter_Click(e);
            }
        });
        buttonRemoveFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnRemoveFilter_Click(e);
            }
        });
        buttonSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSort_Click(e);
            }
        });
        buttonRemoveSorting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnRemoveSorting_Click(e);
            }
        });
        buttonSaveToFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSaveToFile_Click(e);
            }
        });
    }

    private void btnShowOrEditItem_Click(ActionEvent e) {
        int row = tableGoods.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Строка не выбрана!");
            return;
        }

        Goods item = model.getAssignedValue(row);
        ViewItemForm itemForm = new ViewItemForm();
        itemForm.show(item);
    }

    private void btnLoadFromFile_Click(ActionEvent e){
        JFileChooser fileOpen = new JFileChooser();
        fileOpen.setApproveButtonText("Открыть файл");
        fileOpen.setMultiSelectionEnabled(false);
        fileOpen.setDialogType(JFileChooser.OPEN_DIALOG);
        int ret = fileOpen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File f = fileOpen.getSelectedFile();
            goods = FileHandler.ReadData(f.getPath());

            model = new MainTableModel(goods);//, columnNames);
            tableGoods.setModel(model);
        }
    }

    private void btnRemoveItem_Click(ActionEvent e){
        int row = tableGoods.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Строка не выбрана!");
            return;
        }
        if (JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить запись?") ==
                JOptionPane.YES_OPTION) {
            model.removeRow(row);
            model.fireTableDataChanged();
        }
    }

    private void btnClear_Click(ActionEvent e){
        int dialogResult = JOptionPane.showConfirmDialog(null,
                                                         "Вы действительно хотите удалить все записи?" );
        if (dialogResult == JOptionPane.YES_OPTION) {
            goods = new GoodsList();
            model.clear();
        }
    }

    private void btnFilter_Click(ActionEvent e){
        if (comboBoxFilterByType.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Вы не выбрали тип!");
            return;
        }

        GoodsTypes type = GoodsTypes.valueOf((String)comboBoxFilterByType.getSelectedItem());
        model.filterByType(type);
    }

    private void btnRemoveFilter_Click(ActionEvent e){
        model.unfilter();
    }

    private void btnSort_Click(ActionEvent e) {
        int selectedIndex = comboBoxSortField.getSelectedIndex();
        if (selectedIndex == -1 || comboBoxSortOrdering.getSelectedIndex() == -1) {
            JOptionPane.showConfirmDialog(null, "Не выбрано поле для сортировки.");
            return;
        }

        Comparator<Goods> sorting;
        switch (selectedIndex){
            case 0: {sorting = Comparator.comparing(Goods::getID); break;}
            case 1: {sorting = Comparator.comparing(Goods::getName); break;}
            case 2: {sorting = Comparator.comparing(Goods::getPrice); break;}
            case 3: {sorting = Comparator.comparing(Goods::getType); break;}
            default: {
                JOptionPane.showConfirmDialog(null, "Вы смогли выбрать поле для сортировки, которое вам не предлагали. Позравления, вы могли всё сломать!");
                return;
            }
        }

        if (comboBoxSortOrdering.getSelectedIndex() == 1) {
            sorting = sorting.reversed();
        }

        model.sort(sorting);
    }

    private void btnRemoveSorting_Click(ActionEvent e){
        model.unsort();
    }

    private void btnAddItem_Click(ActionEvent e){
        GoodsTypes itemType = GoodsTypes.valueOf((String)comboBoxItemTypes.getSelectedItem());
        switch (itemType) {
            case Sword: {
                AddSwordDialog dialog = new AddSwordDialog();
                if (dialog.showDialog() == JOptionPane.YES_OPTION) {
                    model.addItem(dialog.getItem());
                }
                break;
            }
            case Shield: {
                AddShieldDialog dialog = new AddShieldDialog();
                if (dialog.showDialog() == JOptionPane.YES_OPTION) {
                    model.addItem(dialog.getItem());
                }
                break;
            }
            case Armor: {
                AddArmorDialog dialog = new AddArmorDialog();
                if (dialog.showDialog() == JOptionPane.YES_OPTION) {
                    model.addItem(dialog.getItem());
                }
                break;
            }
            default:
                JOptionPane.showMessageDialog(null, "Пока этого сделать нельзя!");
        }
    }

    private void btnEditItem_Click(ActionEvent e){
        int row = tableGoods.getSelectedRow();

        if (row < 0)
        {
            JOptionPane.showMessageDialog(null, "Строка не выбрана!");
            return;
        }

        Goods item = model.getAssignedValue(row);
        switch (item.getType()) {
            case Sword: {
                AddSwordDialog dialog = new AddSwordDialog((Sword)item);
                if (dialog.showDialog() == JOptionPane.YES_OPTION) {
                    item = dialog.getItem();
                    model.updateData(row, item);
                }
                break;
            }
            case Shield: {
                AddShieldDialog dialog = new AddShieldDialog((Shield)item);
                if (dialog.showDialog() == JOptionPane.YES_OPTION) {
                    item = dialog.getItem();
                    model.updateData(row, item);
                }
                break;
            }
            case Armor: {
                AddArmorDialog dialog = new AddArmorDialog((Armor)item);
                if (dialog.showDialog() == JOptionPane.YES_OPTION) {
                    item = dialog.getItem();
                    model.updateData(row, item);
                }
                break;
            }
            default:
                JOptionPane.showMessageDialog(null, "Пока этого сделать нельзя!");
        }
    }

    private void btnSaveToFile_Click(ActionEvent e){
        JFileChooser fileSave = new JFileChooser();
        fileSave.setApproveButtonText("Сохранить как");
        fileSave.setMultiSelectionEnabled(false);
        fileSave.setDialogType(JFileChooser.OPEN_DIALOG);
        fileSave.addChoosableFileFilter(new FileNameExtensionFilter("Текстовые файлы", "txt"));
        if (fileSave.showDialog(null, "Сохранить") == JFileChooser.APPROVE_OPTION){
            GoodsList list;
            switch (JOptionPane.showConfirmDialog(null,
                    "Сохранить текущую сортировку и фильтрацию при сохранении?")){
                case JOptionPane.YES_OPTION: { list = model.getSortedAndFilteredValues(); break; }
                case JOptionPane.NO_OPTION: { list = model.getBaseValuse(); break; }
                default: return;
            }

            try {
                FileHandler.WriteData(fileSave.getSelectedFile().getCanonicalPath(), list);
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Упс. Что-то пошло не так!");
            }
        }
    }

    public void show() {
        frame = new JFrame("App");
        frame.setContentPane(new MainForm().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
