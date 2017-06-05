package gui;

import classes.DataModels.Goods;
import classes.DataModels.GoodsTypes;
import classes.DataModels.Sword;
import com.sun.jnlp.ApiDialog;

import javax.swing.*;
import java.awt.event.*;
import java.util.Locale;

public class AddSwordDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldPrice;
    private JTextField textFieldName;
    private JTextField textFieldBladeLength;
    private JTextField textFieldFullLength;
    private JTextField textFieldGuardWidth;
    private JTextField textFieldWeight;
    private int result;
    private Sword item;
    private boolean isNewItem;

    public AddSwordDialog() {
        setUI();
        isNewItem = true;
    }

    public AddSwordDialog(Sword item) {
        setUI();
        this.item = item;
        isNewItem = false;

        textFieldName.setText(item.getName());
        textFieldPrice.setText(String.format("%d", item.getPrice()));
        textFieldBladeLength.setText(String.format(Locale.ENGLISH,"%f", item.getBladeLength()));
        textFieldFullLength.setText(String.format(Locale.ENGLISH,"%f", item.getFullLength()));
        textFieldGuardWidth.setText(String.format(Locale.ENGLISH,"%f", item.getGuardWidth()));
        textFieldWeight.setText(String.format(Locale.ENGLISH,"%f", item.getWeight()));
    }

    private void setUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if (Validate()) {
            result = JOptionPane.OK_OPTION;
            this.dispose();
        }
        else {
            JOptionPane.showMessageDialog(null, "Проверьте корректность введённых данных!");
        }
    }

    private void onCancel() {
        result = JOptionPane.CANCEL_OPTION;
        this.dispose();
    }

    private boolean Validate() {
        int price;
        String name;
        float bladeLength, fullLength, guardWidth, weight;
        try {
            name = textFieldName.getText();
            if (name == "") throw new IllegalArgumentException();

            price = Integer.parseInt(textFieldPrice.getText());
            bladeLength = Float.parseFloat(textFieldBladeLength.getText());
            fullLength = Float.parseFloat(textFieldFullLength.getText());
            guardWidth = Float.parseFloat(textFieldGuardWidth.getText());
            weight = Float.parseFloat(textFieldWeight.getText());

            if (isNewItem)
                item = new Sword(Goods.getMaxIdAndInc(), name, price, GoodsTypes.Sword, bladeLength, fullLength, guardWidth, weight);
            else {
                item.setPrice(price);
                item.setWeight(weight);
                item.setGuardWidth(guardWidth);
                item.setFullLength(fullLength);
                item.setBladeLength(bladeLength);
                item.setName(name);;
            }

            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public Sword getItem() {
        return item;
    }

    public int showDialog() {
        pack();
        setVisible(true);
        return result;
    }
}
