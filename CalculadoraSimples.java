
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CalculadoraSimples extends JFrame {

    private JTextField visor;
    private double numero1;
    private String operacao = "";
    private boolean novoNumero = true;

    private final Color FUNDO = Color.decode("#121629");
    private final Color ROXO = Color.decode("#7B61A8");
    private final Color ROSA = Color.decode("#D98BA0");
    private final Color DOURADO = Color.decode("#E8B86D");
    private final Color CLARO = Color.decode("#F2EDF7");

    public CalculadoraSimples() {

        setTitle("Calculadora - Gustavo Henrique Oliveira Costa - RA: 0025427");

        setSize(400, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setBackground(FUNDO);
        setLayout(new BorderLayout(10, 10));

        visor = new JTextField("0");
        visor.setFont(new Font("Arial", Font.BOLD, 28));
        visor.setHorizontalAlignment(JTextField.RIGHT);
        visor.setEditable(false);
        visor.setBackground(CLARO);
        visor.setForeground(FUNDO);
        visor.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        add(visor, BorderLayout.NORTH);

        // Painel dos botões
        JPanel painel = new JPanel(new GridLayout(5, 4, 8, 8));
        painel.setBackground(FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] botoes = {
            "7", "8", "9", "C",
            "4", "5", "6", "/",
            "1", "2", "3", "*",
            "0", ",", "^", "-",
            "+", "="
        };

        for (String texto : botoes) {

            JButton botao = new JButton(texto);
            botao.setFont(new Font("Arial", Font.BOLD, 23));
            botao.setFocusPainted(false);
            botao.setOpaque(true);

            if (texto.equals("C")) {
                botao.setBackground(ROSA);
                botao.setForeground(FUNDO);

            } else if (texto.equals("=")) {
                botao.setBackground(DOURADO);
                botao.setForeground(FUNDO);

            } else if (texto.equals("+") || texto.equals("-")
                    || texto.equals("*") || texto.equals("/")
                    || texto.equals("^")) {
                botao.setBackground(ROXO);
                botao.setForeground(Color.WHITE);

            } else {
                botao.setBackground(CLARO);
                botao.setForeground(FUNDO);
            }

            botao.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    executarBotao(texto);
                }
            });

            painel.add(botao);
        }

        painel.add(new JLabel(""));
        painel.add(new JLabel(""));

        add(painel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void executarBotao(String texto) {

        if (texto.equals("C")) {
            limpar();
        }
        else if (texto.equals("=")) {
            calcular();
        }
        else if (texto.equals("+") || texto.equals("-")
                || texto.equals("*") || texto.equals("/")
                || texto.equals("^")) {
            selecionarOperacao(texto);
        }
        else {
            adicionarNumero(texto);
        }
    }

    private void adicionarNumero(String numero) {

        if (novoNumero) {
            visor.setText(numero.equals(",") ? "0," : numero);
            novoNumero = false;
        } else {
            if (numero.equals(",") && visor.getText().contains(",")) {
                return;
            }

            visor.setText(visor.getText() + numero);
        }
    }

    private void selecionarOperacao(String op) {

        try {
            numero1 = Double.parseDouble(
                visor.getText().replace(",", ".")
            );

            operacao = op;
            novoNumero = true;

        } catch (NumberFormatException erro) {
            visor.setText("Erro");
            novoNumero = true;
        }
    }

    private void calcular() {

        if (operacao.isEmpty()) {
            return;
        }

        try {
            double numero2 = Double.parseDouble(
                visor.getText().replace(",", ".")
            );

            double resultado = 0;

            switch (operacao) {

                case "+":
                    resultado = adicao(numero1, numero2);
                    break;

                case "-":
                    resultado = subtracao(numero1, numero2);
                    break;

                case "*":
                    resultado = multiplicacao(numero1, numero2);
                    break;

                case "/":
                    resultado = divisao(numero1, numero2);
                    break;

                case "^":
                    resultado = potencia(numero1, numero2);
                    break;
            }

            DecimalFormatSymbols simbolos =
                new DecimalFormatSymbols(new Locale("pt", "BR"));

            DecimalFormat formato = new DecimalFormat(
                "0.##########", simbolos
            );

            visor.setText(formato.format(resultado));

            operacao = "";
            novoNumero = true;

        } catch (ArithmeticException erro) {

            JOptionPane.showMessageDialog(
                this,
                erro.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );

            limpar();

        } catch (NumberFormatException erro) {

            visor.setText("Erro");
            novoNumero = true;
        }
    }

    public static double adicao(double a, double b) {
        return a + b;
    }

    public static double subtracao(double a, double b) {
        return a - b;
    }

    public static double multiplicacao(double a, double b) {
        return a * b;
    }

    public static double divisao(double a, double b) {

        if (b == 0) {
            throw new ArithmeticException(
                "Não é possível dividir por zero!"
            );
        }

        return a / b;
    }

    public static double potencia(double a, double b) {
        return Math.pow(a, b);
    }

    private void limpar() {
        visor.setText("0");
        numero1 = 0;
        operacao = "";
        novoNumero = true;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CalculadoraSimples();
            }
        });
    }
}
