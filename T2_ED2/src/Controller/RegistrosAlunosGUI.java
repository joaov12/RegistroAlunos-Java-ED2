
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
                // Lógica para exibir todos os alunos na JTextArea
                // Pode usar tree.imprimirEmOrdem() e exibir na JTextArea
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
                            // System.out.println("Aluno inserido com sucesso: " + aluno.getNome());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Font font = new Font("SansSerif", Font.PLAIN, 16); // Você pode ajustar o tamanho e o estilo da fonte
                                                                   // conforme desejado

                // Configure a fonte na JTextArea
                textArea.setFont(font);
                textArea.setText("");

                // Chamar o método para imprimir os alunos em ordem na árvore

                // Exibir os alunos na JTextArea
                textArea.append(tree.imprimirEmOrdem());

            }
        });

        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Crie um JOptionPane para obter os dados do novo aluno
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

                // Se o usuário pressionou OK, prossiga com a inserção do aluno
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        int matricula = Integer.parseInt(matriculaField.getText());
                        // Verifique se a matrícula está dentro do intervalo de 10 a 99
                        if (matricula < 10 || matricula > 99) {
                            JOptionPane.showMessageDialog(null, "A matrícula deve estar no intervalo de 10 a 99.",
                                    "Erro", JOptionPane.ERROR_MESSAGE);
                            return; // Saia da inserção
                        }

                        // Verifique se a matrícula já existe
                        if (tree.verificarMatriculaExistente(matricula)) {
                            JOptionPane.showMessageDialog(null, "Esta matrícula já existe.", "Erro",
                                    JOptionPane.ERROR_MESSAGE);
                            return; // Saia da inserção
                        }

                        String nome = nomeField.getText();
                        int faltas = Integer.parseInt(faltasField.getText());
                        double nota1 = Double.parseDouble(nota1Field.getText());
                        double nota2 = Double.parseDouble(nota2Field.getText());
                        double nota3 = Double.parseDouble(nota3Field.getText());

                        Aluno aluno = new Aluno(matricula, nome, faltas, nota1, nota2, nota3);
                        tree.inserir(aluno);

                        // Limpe a JTextArea
                        textArea.setText("");

                        // Exibir os alunos atualizados na JTextArea
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
                // Solicite a matrícula do aluno que deseja buscar
                String matriculaInput = JOptionPane.showInputDialog(null, "Digite a matrícula do aluno:");

                try {
                    int matriculaBusca = Integer.parseInt(matriculaInput);

                    // Chame o método buscarAluno da sua classe AVLAluno
                    BuscaResultado resultadoBusca = tree.buscarAluno(matriculaBusca);

                    if (resultadoBusca.getAlunoEncontrado() != null) {
                        // Se o aluno foi encontrado, exiba suas informações na JTextArea
                        textArea.setText(resultadoBusca.getAlunoEncontrado().toString());
                    } else {
                        // Se o aluno não foi encontrado, exiba uma mensagem de erro
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
                // Solicite a matrícula do aluno que deseja editar
                String matriculaInput = JOptionPane.showInputDialog(null,
                        "Digite a matrícula do aluno que deseja editar:");

                try {
                    int matriculaBusca = Integer.parseInt(matriculaInput);

                    // Chame o método buscarAluno da sua classe AVLAluno
                    BuscaResultado resultadoBusca = tree.buscarAluno(matriculaBusca);

                    if (resultadoBusca.getAlunoEncontrado() != null) {
                        // Se o aluno foi encontrado, exiba um novo diálogo para atualizar informações
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

                        // Se o usuário pressionou OK, atualize as informações do aluno
                        if (option == JOptionPane.OK_OPTION) {
                            int novasFaltas = Integer.parseInt(faltasField.getText());
                            double novaNota1 = Double.parseDouble(nota1Field.getText());
                            double novaNota2 = Double.parseDouble(nota2Field.getText());
                            double novaNota3 = Double.parseDouble(nota3Field.getText());

                            // Atualize as informações do aluno
                            alunoParaEditar.setFaltas(novasFaltas);
                            alunoParaEditar.setNota1(novaNota1);
                            alunoParaEditar.setNota2(novaNota2);
                            alunoParaEditar.setNota3(novaNota3);

                            // Recalcule a média do aluno e atualize as informações na árvore AVL
                            tree.editarAluno(alunoParaEditar);

                            // Limpe a JTextArea
                            textArea.setText("");

                            // Exiba os alunos atualizados na JTextArea
                            textArea.append(tree.imprimirEmOrdem());
                        }
                    } else {
                        // Se o aluno não foi encontrado, exiba uma mensagem de erro
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
                // Solicite a matrícula do aluno que deseja remover
                String matriculaInput = JOptionPane.showInputDialog(null,
                        "Digite a matrícula do aluno que deseja remover:");

                try {
                    int matricula = Integer.parseInt(matriculaInput);

                    // Verificar se a matrícula existe na árvore
                    if (tree.verificarMatriculaExistente(matricula)) {
                        // Remover o aluno da árvore
                        tree.removerAluno(matricula);

                        // Atualizar a exibição da árvore na JTextArea após a remoção
                        textArea.setText(""); // Limpa o texto anterior
                        textArea.append(tree.imprimirEmOrdem()); // Atualiza com a nova árvore

                        // Exibir uma mensagem de sucesso (opcional)
                        JOptionPane.showMessageDialog(null, "Aluno removido com sucesso!");
                    } else {
                        // Exibir uma mensagem de erro se a matrícula não existir
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
