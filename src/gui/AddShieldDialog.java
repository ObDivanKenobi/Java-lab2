package gui;

import javax.swing.*;
import java.awt.event.*;

import classes.DataModels.Goods;
import classes.DataModels.GoodsTypes;
import classes.DataModels.Shield;

public class AddShieldDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldPrice;
    private JTextField textFieldName;
    private JTextField textFieldShape;
    private JTextField textFieldMaterial;
    private JTextArea textAreaDescription;
    private Shield item;
    private int result;
    private boolean isNewItem;

    public AddShieldDialog() {
        isNewItem = true;
        setUI();
    }

    public AddShieldDialog(Shield item) {
        setUI();
        isNewItem = false;
        textFieldName.setText(item.getName());
        textFieldPrice.setText(String.format("%d", item.getPrice()));
        textFieldShape.setText(item.getShape());
        textFieldMaterial.setText(item.getShape());
        textAreaDescription.setText(item.getDescription());
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
        String name, shape, material, description;
        try {
            name = textFieldName.getText();
            price = Integer.parseInt(textFieldPrice.getText());
            shape = textFieldShape.getText();
            material = textFieldMaterial.getText();
            description = textAreaDescription.getText();

            if (name == "" || shape == "" || material == "" || description.isEmpty()) throw new IllegalArgumentException();

            if (isNewItem)
                item = new Shield(Goods.getMaxIdAndInc(), name, price, GoodsTypes.Shield, shape, material, description);
            else {
                item.setShape(shape);
                item.setMaterial(material);
                item.setName(name);
                item.setDescription(description);
                item.setPrice(price);
            }
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public Shield getItem() {
        return item;
    }

    public int showDialog() {
        pack();
        setVisible(true);
        return result;
    }
}
