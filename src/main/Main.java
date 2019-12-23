package main;

import control.CalculateMoneyCommand;
import control.Command;
import model.CurrencySet;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main extends JFrame {
    private Map<String, Command> commands = new HashMap<>();
    private static Map<String, JComponent> components = new HashMap<>();
    private static CurrencySet currencySet;

    public static void main(String[] args) throws IOException {
        new Main().setVisible(true);
    }

    public Main() throws HeadlessException, IOException {
        currencySet = new CurrencySetFileReader().load();
        deployUI();
        addCommands();
    }

    private void addCommands() {
        commands.put("Calculate",new CalculateMoneyCommand());
    }

    private void deployUI() {
        this.setTitle("Money Calculator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(800,200));
        this.setLocationRelativeTo(null);
        this.getContentPane().add(applicationPanel());
    }

    private JPanel applicationPanel() {
        JPanel panel = new JPanel();
        panel.add(originalLabel());
        panel.add(originalCombo());
        panel.add(originalMoneyLabel());
        panel.add(originalMoneyTextField());
        panel.add(exchangeToLabel());
        panel.add(exchangeToCombo());
        panel.add(exchangeButton());
        panel.add(resultLabel());
        panel.add(resultTextField());
        return panel;
    }

    private JButton exchangeButton() {
        JButton button = new JButton("Calculate");
        button.addActionListener(e -> commands.get("Calculate").execute());
        return button;
    }

    private JTextField resultTextField() {
        JTextField text = new JTextField();
        text.setPreferredSize(new Dimension(160,24));
        components.put("ResultTextField",text);
        return text;
    }

    private JLabel resultLabel() {
        JLabel label = new JLabel("Result: ");
        return label;
    }

    private JTextField originalMoneyTextField() {
        JTextField text = new JTextField();
        text.setPreferredSize(new Dimension(160,24));
        components.put("OriginalMoneyTextField",text);
        return text;
    }

    private JComboBox exchangeToCombo() {
        JComboBox combo = new JComboBox();
        components.put("ExchangeToCombo",combo);
        currencySet.currencyMap().
                keySet().
                stream().
                filter(code -> !((JComboBox)components.get("OriginalCombo")).getSelectedItem().toString().equals(code)).
                forEach(combo::addItem);
        combo.addActionListener(e -> ((JTextField) components.get("ResultTextField")).setText(""));
        return combo;
    }

    private JLabel originalMoneyLabel() {
        JLabel label = new JLabel("Money: ");
        return label;
    }

    private JLabel exchangeToLabel() {
        JLabel label = new JLabel("Exchange to: ");
        return label;
    }

    private JComboBox originalCombo() {
        JComboBox combo = new JComboBox();
        components.put("OriginalCombo",combo);
        currencySet.currencyMap().
                keySet().
                forEach(combo::addItem);
        combo.addActionListener(e -> {
            ((JComboBox)components.get("ExchangeToCombo")).removeAllItems();
            currencySet.currencyMap().
                    keySet().
                    stream().
                    filter(code -> !combo.getSelectedItem().toString().equals(code)).
                    forEach(((JComboBox)components.get("ExchangeToCombo"))::addItem);
            ((JTextField) components.get("ResultTextField")).setText("");
        });
        return combo;
    }

    private JLabel originalLabel() {
        JLabel label = new JLabel("Original: ");
        return label;
    }

    public static Map<String, JComponent> components(){
        return components;
    }

    public static CurrencySet currencySet(){
        return currencySet;
    }
}