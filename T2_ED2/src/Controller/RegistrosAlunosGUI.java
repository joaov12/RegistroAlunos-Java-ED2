
package Controller;

import Util.AVLAluno;
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
            public void actionPerformed(ActionEvent e) {
                // Lógica para exibir todos os alunos na JTextArea
                // Pode usar tree.imprimirEmOrdem() e exibir na JTextArea
            }
        });

        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para inserir um novo aluno
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para buscar um aluno
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para editar um aluno
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para remover um aluno
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
