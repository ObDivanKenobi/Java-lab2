package gui;

import classes.DataModels.Goods;
import classes.FileHandler;
import classes.GoodsList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Илья on 20.05.2017.
 */
public class MainForm {
    private JPanel panel1;
    private JButton buttonLoadFromFile;
    private JButton buttonLoadFromDB;
    private JButton buttonSaveToFile;
    private JButton buttonSaveToDB;
    private JComboBox comboBox1;
    private JButton buttonFilter;
    private JComboBox comboBox2;
    private JButton buttonSort;
    private JTable tableGoods;
    private JButton buttonAdd;
    private JButton buttonDelete;
    private JButton buttonClear;
    private JButton buttonShowOrEditItem;
    private JFrame frame;

    private GoodsList goods = new GoodsList();
    private String[] columnNames = {"ID", "Название", "Цена", "Тип"};
    private Object[][] data = new Object[0][0];
    private MainTableModel model = new MainTableModel(goods, columnNames);

    public MainForm() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
            UIManager.put("FileChooser.cancelButtonText", "Отмена");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            return;
        }

        tableGoods.setFillsViewportHeight(true);

        buttonLoadFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnLoadFromFile_Click(e);
            }
        });

        buttonShowOrEditItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnShowOrEditItem_Click(e);
            }
        });
    }

    public void btnShowOrEditItem_Click(ActionEvent e) {
        int row = tableGoods.getSelectedRow();
        if (row == -1)
        {
            JOptionPane.showMessageDialog(null, "Строка не выбрана!");
            return;
        }

        Goods item = model.getAssignedValue(row);
        ViewItemForm itemForm = new ViewItemForm();
        itemForm.show(item);
    }

    public void btnLoadFromFile_Click(ActionEvent e){
        JFileChooser fileopen = new JFileChooser();
        fileopen.setApproveButtonText("Открыть файл");
        fileopen.setMultiSelectionEnabled(false);
        fileopen.setDialogType(JFileChooser.OPEN_DIALOG);
        int ret = fileopen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File f = fileopen.getSelectedFile();
            goods = FileHandler.ReadData(f.getPath());

            model = new MainTableModel(goods, columnNames);
            tableGoods.setModel(model);
        }
    }

    public void Show() {
        frame = new JFrame("App");
        frame.setContentPane(new MainForm().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
