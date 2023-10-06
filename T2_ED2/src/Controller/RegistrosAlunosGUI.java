
package Controller;

import Util.AVLAluno;
import Util.AVLAluno.BuscaResultado;

import javax.swing.*;

import Entities.Aluno;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class RegistrosAlunosGUI extends JFrame {

    public AVLAluno tree = new AVLAluno();
    public JTextArea textArea;

    public RegistrosAlunosGUI() {

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        tree = new AVLAluno();
        textArea = new JTextArea(20, 40);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton viewButton = new JButton("Ver Todos Alunos");
        JButton insertButton = new JButton("Inserir Aluno");
        JButton searchButton = new JButton("Buscar Aluno");
        JButton editButton = new JButton("Editar Aluno");
        JButton removeButton = new JButton("Remover Aluno");
        JButton exitButton = new JButton("Sair");

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try (BufferedReader br = new BufferedReader(new FileReader("T2_ED2\\src\\entrada.txt"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(" ");
                        if (parts.length == 6) {
                            int matricula = Integer.parseInt(parts[0]);
                            String nome = parts[1];
                            int faltas = Integer.parseInt(parts[2]);
                            double nota1 = Double.parseDouble(parts[3]);
                            double nota2 = Double.parseDouble(parts[4]);
                            double nota3 = Double.parseDouble(parts[5]);

                            Aluno aluno = new Aluno(matricula, nome, faltas, nota1, nota2, nota3);
                            tree.inserir(aluno);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Font font = new Font("SansSerif", Font.PLAIN, 24); 
                textArea.setFont(font);
                textArea.setText("");

                textArea.append(tree.imprimirEmOrdem());

            }
        });

        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextField matriculaField = new JTextField();
                JTextField nomeField = new JTextField();
                JTextField faltasField = new JTextField();
                JTextField nota1Field = new JTextField();
                JTextField nota2Field = new JTextField();
                JTextField nota3Field = new JTextField();

                Object[] message = {
                        "Matrícula:", matriculaField,
                        "Nome:", nomeField,
                        "Faltas:", faltasField,
                        "Nota 1:", nota1Field,
                        "Nota 2:", nota2Field,
                        "Nota 3:", nota3Field
                };

                int option = JOptionPane.showConfirmDialog(null, message, "Inserir Aluno",
                        JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    try {
                        int matricula = Integer.parseInt(matriculaField.getText());
                        if (matricula < 10 || matricula > 99) {
                            JOptionPane.showMessageDialog(null, "A matrícula deve estar no intervalo de 10 a 99.",
                                    "Erro", JOptionPane.ERROR_MESSAGE);
                            return; 
                        }

                        if (tree.verificarMatriculaExistente(matricula)) {
                            JOptionPane.showMessageDialog(null, "Esta matrícula já existe.", "Erro",
                                    JOptionPane.ERROR_MESSAGE);
                            return; 
                        }

                        String nome = nomeField.getText();
                        int faltas = Integer.parseInt(faltasField.getText());
                        double nota1 = Double.parseDouble(nota1Field.getText());
                        double nota2 = Double.parseDouble(nota2Field.getText());
                        double nota3 = Double.parseDouble(nota3Field.getText());

                        Aluno aluno = new Aluno(matricula, nome, faltas, nota1, nota2, nota3);
                        tree.inserir(aluno);

                        textArea.setText("");

                        textArea.append(tree.imprimirEmOrdem());

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null,
                                "Certifique-se de que todos os campos foram preenchidos corretamente.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String matriculaInput = JOptionPane.showInputDialog(null, "Digite a matrícula do aluno:");

                try {
                    int matriculaBusca = Integer.parseInt(matriculaInput);

                    BuscaResultado resultadoBusca = tree.buscarAluno(matriculaBusca);

                    if (resultadoBusca.getAlunoEncontrado() != null) {
                        textArea.setText(resultadoBusca.getAlunoEncontrado().toString());
                    } else {
                        JOptionPane.showMessageDialog(null, resultadoBusca.getMensagem(), "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Matrícula inválida. Digite um número válido.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String matriculaInput = JOptionPane.showInputDialog(null,
                        "Digite a matrícula do aluno que deseja editar:");

                try {
                    int matriculaBusca = Integer.parseInt(matriculaInput);

                    BuscaResultado resultadoBusca = tree.buscarAluno(matriculaBusca);

                    if (resultadoBusca.getAlunoEncontrado() != null) {
                        Aluno alunoParaEditar = resultadoBusca.getAlunoEncontrado();

                        JTextField faltasField = new JTextField(String.valueOf(alunoParaEditar.getFaltas()));
                        JTextField nota1Field = new JTextField(String.valueOf(alunoParaEditar.getNota1()));
                        JTextField nota2Field = new JTextField(String.valueOf(alunoParaEditar.getNota2()));
                        JTextField nota3Field = new JTextField(String.valueOf(alunoParaEditar.getNota3()));

                        Object[] message = {
                                "Faltas:", faltasField,
                                "Nota 1:", nota1Field,
                                "Nota 2:", nota2Field,
                                "Nota 3:", nota3Field
                        };

                        int option = JOptionPane.showConfirmDialog(null, message, "Editar Aluno",
                                JOptionPane.OK_CANCEL_OPTION);

                        if (option == JOptionPane.OK_OPTION) {
                            int novasFaltas = Integer.parseInt(faltasField.getText());
                            double novaNota1 = Double.parseDouble(nota1Field.getText());
                            double novaNota2 = Double.parseDouble(nota2Field.getText());
                            double novaNota3 = Double.parseDouble(nota3Field.getText());

                            alunoParaEditar.setFaltas(novasFaltas);
                            alunoParaEditar.setNota1(novaNota1);
                            alunoParaEditar.setNota2(novaNota2);
                            alunoParaEditar.setNota3(novaNota3);

                            tree.editarAluno(alunoParaEditar);

                            textArea.setText("");

                            textArea.append(tree.imprimirEmOrdem());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, resultadoBusca.getMensagem(), "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Matrícula inválida. Digite um número válido.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String matriculaInput = JOptionPane.showInputDialog(null,
                        "Digite a matrícula do aluno que deseja remover:");

                try {
                    int matricula = Integer.parseInt(matriculaInput);

                    if (tree.verificarMatriculaExistente(matricula)) {
                        tree.removerAluno(matricula);

                        textArea.setText(""); 
                        textArea.append(tree.imprimirEmOrdem()); 

                        JOptionPane.showMessageDialog(null, "Aluno removido com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Matrícula não encontrada. Verifique a matrícula inserida.",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Matrícula inválida. Digite um número válido.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tree.gravarEmOrdem("saida.txt");
                System.exit(0);
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(6, 1));
        buttonPanel.add(viewButton);
        buttonPanel.add(insertButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(exitButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);

        add(mainPanel);
        setTitle("Registro de Alunos");
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RegistrosAlunosGUI gui = new RegistrosAlunosGUI();
                gui.setVisible(true);
            }
        });
    }
}
