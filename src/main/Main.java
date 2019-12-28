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
        try { 
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
        } catch(Exception ignored){}
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
        this.setMinimumSize(new Dimension(500,150));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().add(applicationPanel(),BorderLayout.NORTH);
        this.getContentPane().add(applicationPanel2(),BorderLayout.CENTER);
        this.getContentPane().add(applicationPanel3(),BorderLayout.SOUTH);
    }

    private JPanel applicationPanel() {
        JPanel panel = new JPanel();
        panel.add(originalLabel());
        panel.add(originalCombo());
        panel.add(originalMoneyLabel());
        panel.add(originalMoneyTextField());
        panel.add(exchangeToLabel());
        panel.add(exchangeToCombo());
        return panel;
    }
    
    private JPanel applicationPanel2() {
        JPanel panel = new JPanel();
        panel.add(exchangeButton());
        return panel;
    }
    
    private JPanel applicationPanel3() {
        JPanel panel = new JPanel();
        panel.add(resultLabel());
        panel.add(resultTextField());
        return panel;
    }

    private JButton exchangeButton() {
        JButton button = new JButton("Calculate");
        button.setPreferredSize(new Dimension(80,40));
        button.addActionListener(e -> commands.get("Calculate").execute());
        return button;
    }

    private JTextField resultTextField() {
        JTextField text = new JTextField();
        text.setLayout(new FlowLayout(FlowLayout.LEFT));
        text.setPreferredSize(new Dimension(160,24));
        text.setEditable(false);
        components.put("ResultTextField",text);
        return text;
    }

    private JLabel resultLabel() {
        JLabel label = new JLabel("Exchange: ");
        label.setLayout(new FlowLayout(FlowLayout.CENTER));
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
        JLabel label = new JLabel("Amount: ");
        return label;
    }

    private JLabel exchangeToLabel() {
        JLabel label = new JLabel("To: ");
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
        JLabel label = new JLabel("From: ");
        return label;
    }

    public static Map<String, JComponent> components(){
        return components;
    }

    public static CurrencySet currencySet(){
        return currencySet;
    }
}