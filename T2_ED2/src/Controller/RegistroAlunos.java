/*
package Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import Entities.Aluno;
import Util.AVLAluno;
import Util.Node;

public class RegistroAlunos {
    public static void main(String[] args) {

        AVLAluno tree = new AVLAluno();
        Scanner scanner = new Scanner(System.in);

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

        while (true) {
            System.out.println("\nOpções:");
            System.out.println("1 - Ver todos alunos");
            System.out.println("2 - Inserir aluno");
            System.out.println("3 - Buscar aluno");
            System.out.println("4 - Editar aluno (Atribuir notas ou faltas)");
            System.out.println("5 - Remover aluno");
            System.out.println("6 - Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer de entrada

            switch (opcao) {
                case 1:
                    System.out.println("Imprimindo alunos em ordem de matrícula:");
                    tree.imprimirEmOrdem();
                    break;
                case 2:
                    System.out.println("Informe a matrícula do aluno:");
                    int matricula = scanner.nextInt();
                    scanner.nextLine();
                    if (matricula < 10 || matricula > 99) {
                        System.out.println("Matrícula fora do intervalo permitido (10-99): " + matricula);
                        break;
                    }

                    // Verificar se a matrícula já existe na árvore
                    if (tree.verificarMatriculaExistente(matricula)) {
                        System.out.println("Matrícula já existente. Não é permitido inserir um aluno com matrícula duplicada.");
                    } else {
                        System.out.println("Informe o nome do aluno:");
                        String nome = scanner.nextLine();
                        System.out.println("Informe o número de faltas:");
                        int faltas = scanner.nextInt();
                        System.out.println("Informe a nota 1:");
                        double nota1 = scanner.nextDouble();
                        System.out.println("Informe a nota 2:");
                        double nota2 = scanner.nextDouble();
                        System.out.println("Informe a nota 3:");
                        double nota3 = scanner.nextDouble();

                        Aluno aluno = new Aluno(matricula, nome, faltas, nota1, nota2, nota3);
                        tree.inserir(aluno);
                        System.out.println("Aluno inserido com sucesso!");
                    }
                    break;
                case 3:
                    System.out.println("Digite a matricula do aluno que você quer buscar");
                    int matriculaBuscar = scanner.nextInt();
                    scanner.nextLine();
                    tree.buscarAluno(matriculaBuscar);
                    break;

                case 4:
                    System.out.println("Digite a matrícula do aluno que você quer editar:");
                    int matriculaEditar = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Informe o novo número de faltas:");
                    int faltasEditar = scanner.nextInt();
                    System.out.println("Informe a nova nota 1:");
                    double nota1Editar = scanner.nextDouble();
                    System.out.println("Informe a nova nota 2:");
                    double nota2Editar = scanner.nextDouble();
                    System.out.println("Informe a nova nota 3:");
                    double nota3Editar = scanner.nextDouble();

                    tree.editarAluno(matriculaEditar, faltasEditar, nota1Editar, nota2Editar, nota3Editar);
                    System.out.println("Aluno editado com sucesso!");
                    break;
                case 5:
                    System.out.println("Informe a matrícula do aluno que deseja remover:");
                    int matriculaRemover = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer de entrada
                    tree.removerAluno(matriculaRemover);
                    break;
                case 6:
                    System.out.println("Dados gravados no arquivo saida.txt");
                    tree.gravarEmOrdem("saida.txt");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void imprimirEmOrdem(Node node) {
        if (node != null) {
            imprimirEmOrdem(node.esquerda);
            System.out.println(node.aluno.toString());
            imprimirEmOrdem(node.direita);
        }
    }
}


 */
