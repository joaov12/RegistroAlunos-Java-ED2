package Util;

import java.io.FileWriter;
import java.io.IOException;

import Entities.Aluno;

public class AVLAluno {
    Node raiz;

    private int altura(Node node) {
        if (node == null)
            return 0;
        return node.altura;
    }

    private int calcularBalance(Node node) {
        if (node == null)
            return 0;
        return altura(node.esquerda) - altura(node.direita);
    }

    private Node rotacaoDireita(Node y) {
        Node x = y.esquerda;
        Node T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;

        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;

        return x;
    }

    private Node rotacaoEsquerda(Node x) {
        Node y = x.direita;
        Node T2 = y.esquerda;

        y.esquerda = x;
        x.direita = T2;

        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;

        return y;
    }

    public void inserir(Aluno aluno) {
        raiz = inserirRec(raiz, aluno);
    }

    private Node inserirRec(Node node, Aluno aluno) {
        if (node == null)
            return new Node(aluno);

        if (aluno.getMatricula() < node.aluno.getMatricula())
            node.esquerda = inserirRec(node.esquerda, aluno);
        else if (aluno.getMatricula() > node.aluno.getMatricula())
            node.direita = inserirRec(node.direita, aluno);
        else
            return node; // Chaves duplicadas não são permitidas

        node.altura = 1 + Math.max(altura(node.esquerda), altura(node.direita));

        int balance = calcularBalance(node);

        // Casos de rotação
        if (balance > 1 && aluno.getMatricula() < node.esquerda.aluno.getMatricula())
            return rotacaoDireita(node);

        if (balance < -1 && aluno.getMatricula() > node.direita.aluno.getMatricula())
            return rotacaoEsquerda(node);

        if (balance > 1 && aluno.getMatricula() > node.esquerda.aluno.getMatricula()) {
            node.esquerda = rotacaoEsquerda(node.esquerda);
            return rotacaoDireita(node);
        }

        if (balance < -1 && aluno.getMatricula() < node.direita.aluno.getMatricula()) {
            node.direita = rotacaoDireita(node.direita);
            return rotacaoEsquerda(node);
        }

        return node;
    }

    public void imprimirEmOrdem() {
        imprimirEmOrdemRec(raiz);
    }

    private void imprimirEmOrdemRec(Node node) {
        if (node != null) {
            imprimirEmOrdemRec(node.esquerda);
            System.out.println(node.aluno.toString());
            imprimirEmOrdemRec(node.direita);
        }
    }

    public void buscarAluno(int matricula) {
        Aluno alunoEncontrado = buscarAlunoRec(raiz, matricula);

        if (alunoEncontrado != null) {
            System.out.println(alunoEncontrado.toString());
        } else {
            System.out.println("Aluno com a matrícula " + matricula + " não encontrado.");
        }
    }

    private Aluno buscarAlunoRec(Node node, int matricula) {
        if (node == null)
            return null;

        if (matricula == node.aluno.getMatricula())
            return node.aluno;
        else if (matricula < node.aluno.getMatricula())
            return buscarAlunoRec(node.esquerda, matricula);
        else
            return buscarAlunoRec(node.direita, matricula);
    }

    public void editarAluno(int matricula, int faltas, double nota1, double nota2, double nota3) {
        editarAlunoRec(raiz, matricula, faltas, nota1, nota2, nota3);
    }
    private void editarAlunoRec(Node node, int matricula, int faltas, double nota1, double nota2, double nota3) {
        if (node == null)
            return;
    
        if (matricula < node.aluno.getMatricula())
            editarAlunoRec(node.esquerda, matricula, faltas, nota1, nota2, nota3);
        else if (matricula > node.aluno.getMatricula())
            editarAlunoRec(node.direita, matricula, faltas, nota1, nota2, nota3);
        else {
            // Nó encontrado, realizar a edição
            node.aluno.setFaltas(faltas);
            node.aluno.setNota1(nota1);
            node.aluno.setNota2(nota2);
            node.aluno.setNota3(nota3);
    
            // Atualizar a média do aluno após a edição
            node.aluno.setMedia((nota1 * 0.2) + (nota2 * 0.35) + (nota3 * 0.45));
    
            // Atualizar a altura do nó após a edição
            node.altura = 1 + Math.max(altura(node.esquerda), altura(node.direita));
        }
    }
    
    

    public void removerAluno(int matricula) {
        raiz = removerAlunoRec(raiz, matricula);
    }

    private Node removerAlunoRec(Node node, int matricula) {
        if (node == null)
            return node;

        if (matricula < node.aluno.getMatricula())
            node.esquerda = removerAlunoRec(node.esquerda, matricula);
        else if (matricula > node.aluno.getMatricula())
            node.direita = removerAlunoRec(node.direita, matricula);
        else {
            if (node.esquerda == null || node.direita == null) {
                Node temp = null;
                if (temp == node.esquerda)
                    temp = node.direita;
                else
                    temp = node.esquerda;
                if (temp == null) {
                    temp = node;
                    node = null;
                } else 
                    node = temp; 
            } else {
                Node temp = minNode(node.direita);

                node.aluno = temp.aluno;

                node.direita = removerAlunoRec(node.direita, temp.aluno.getMatricula());
            }
        }
        
        if (node == null)
            return node;

        node.altura = 1 + Math.max(altura(node.esquerda), altura(node.direita));

        int balance = calcularBalance(node);

       
        if (balance > 1 && calcularBalance(node.esquerda) >= 0)
            return rotacaoDireita(node);

        if (balance > 1 && calcularBalance(node.esquerda) < 0) {
            node.esquerda = rotacaoEsquerda(node.esquerda);
            return rotacaoDireita(node);
        }

        if (balance < -1 && calcularBalance(node.direita) <= 0)
            return rotacaoEsquerda(node);

        if (balance < -1 && calcularBalance(node.direita) > 0) {
            node.direita = rotacaoDireita(node.direita);
            return rotacaoEsquerda(node);
        }

        return node;
    }

    private Node minNode(Node node) {
        Node current = node;
        while (current.esquerda != null)
            current = current.esquerda;
        return current;
    }

    public boolean verificarMatriculaExistente(int matricula) {
        return verificarMatriculaExistenteRec(raiz, matricula);
    }

    private boolean verificarMatriculaExistenteRec(Node node, int matricula) {
        if (node == null)
            return false;

        if (matricula == node.aluno.getMatricula())
            return true;
        else if (matricula < node.aluno.getMatricula())
            return verificarMatriculaExistenteRec(node.esquerda, matricula);
        else
            return verificarMatriculaExistenteRec(node.direita, matricula);
    }

    public void gravarEmOrdem(String nomeArquivo) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            gravarEmOrdemRec(raiz, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void gravarEmOrdemRec(Node node, FileWriter writer) throws IOException {
        if (node != null) {
            gravarEmOrdemRec(node.esquerda, writer);
            writer.write(node.aluno.toString() + "\n");
            gravarEmOrdemRec(node.direita, writer);
        }
    }
}
