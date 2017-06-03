package gui;

import classes.DataModels.Goods;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Илья on 21.05.2017.
 */
public class ViewItemForm{
    private JFrame frame;
    private JPanel panelMain;
    private JTable tableItemInfo;
    private JButton buttonOk;

    public ViewItemForm() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
            UIManager.put("FileChooser.cancelButtonText", "Отмена");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            return;
        }

        tableItemInfo.setFillsViewportHeight(true);
        buttonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnOk_Click(e);
            }
        });
    }

    public void btnOk_Click(ActionEvent e){
        frame.dispose();
    }

    public void show(Goods item) {
        ItemTableModel model = new ItemTableModel(item);
        tableItemInfo.setModel(model);
        tableItemInfo.setColumnSelectionInterval(1, 1);

        frame = new JFrame("App");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
