package gui;

import classes.DataModels.*;

import javax.swing.*;
import java.awt.event.*;

public class AddArmorDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldPrice;
    private JTextField textFieldName;
    private JTextArea textAreaDescription;
    private JComboBox comboBoxType;
    private Armor item;
    private int result;
    private boolean isNewItem;

    public AddArmorDialog() {
        setGui();
        isNewItem = true;
    }

    private void setGui() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        for (ArmorTypes type: ArmorTypes.values())
            if (type != ArmorTypes.Unset)
                comboBoxType.addItem(type.toString());

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

    public AddArmorDialog(Armor item) {
        setGui();
        isNewItem = false;
        this.item = item;
        textFieldName.setText(item.getName());
        textFieldPrice.setText(String.format("%d", item.getPrice()));
        comboBoxType.setSelectedItem(item.getArmorType().toString());
        textAreaDescription.setText(item.getDescription());
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
        ArmorTypes type;
        String name, description;
        try {
            name = textFieldName.getText();
            price = Integer.parseInt(textFieldPrice.getText());
            type = ArmorTypes.valueOf((String)comboBoxType.getSelectedItem());
            description = textAreaDescription.getText();

            if (name == "" || description.isEmpty()) throw new IllegalArgumentException();

            if (isNewItem)
                item = new Armor(Goods.getMaxIdAndInc(), name, price, GoodsTypes.Armor, type, description);
            else {
                item.setName(name);
                item.setArmorType(type);
                item.setDescription(description);
                item.setPrice(price);
            }

            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public Armor getItem() {
        return item;
    }

    public int showDialog() {
        pack();
        setVisible(true);
        return result;
    }
}
