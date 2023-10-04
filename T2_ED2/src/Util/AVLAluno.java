package Util;

import java.io.FileWriter;
import java.io.IOException;

import Entities.Aluno;

public class AVLAluno {
    Node raiz;

    private Node balancear(Node node) {
        int balance = calcularBalance(node);
    
        if (balance > 1) {
            if (calcularBalance(node.esquerda) >= 0)
                return rotacaoDireita(node);
            else {
                node.esquerda = rotacaoEsquerda(node.esquerda);
                return rotacaoDireita(node);
            }
        }
        if (balance < -1) {
            if (calcularBalance(node.direita) <= 0)
                return rotacaoEsquerda(node);
            else {
                node.direita = rotacaoDireita(node.direita);
                return rotacaoEsquerda(node);
            }
        }
    
        return node;
    }
    

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
            return node; 

        node.altura = 1 + Math.max(altura(node.esquerda), altura(node.direita));

        int balance = calcularBalance(node);

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

    public String imprimirEmOrdem() {
        StringBuilder sb = new StringBuilder();
        imprimirEmOrdemRec(raiz, sb);
        return sb.toString();
    }

    private void imprimirEmOrdemRec(Node node, StringBuilder sb) {
        if (node != null) {
            imprimirEmOrdemRec(node.esquerda, sb);
            Aluno aluno = node.aluno;
            sb.append("Matrícula: ").append(aluno.getMatricula()).append("\n");
            sb.append("Nome: ").append(aluno.getNome()).append("\n");
            sb.append("Faltas: ").append(aluno.getFaltas()).append("\n");
            sb.append("Nota 1: ").append(aluno.getNota1()).append("\n");
            sb.append("Nota 2: ").append(aluno.getNota2()).append("\n");
            sb.append("Nota 3: ").append(aluno.getNota3()).append("\n");

            double media = aluno.calcularMedia();
            sb.append("Média: ").append(String.format("%.2f", media)).append("\n");

            sb.append("\n"); 
            imprimirEmOrdemRec(node.direita, sb);
        }
    }

    public class BuscaResultado {
        public Aluno alunoEncontrado;
        public String mensagem;

        public BuscaResultado(Aluno alunoEncontrado, String mensagem) {
            this.alunoEncontrado = alunoEncontrado;
            this.mensagem = mensagem;
        }

        public Aluno getAlunoEncontrado() {
            return alunoEncontrado;
        }

        public String getMensagem() {
            return mensagem;
        }
    }

    public BuscaResultado buscarAluno(int matricula) {
        Aluno alunoEncontrado = buscarAlunoRec(raiz, matricula);

        if (alunoEncontrado != null) {
            return new BuscaResultado(alunoEncontrado, alunoEncontrado.toString());
        } else {
            return new BuscaResultado(null, "Aluno com a matrícula " + matricula + " não encontrado.");
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

    public void editarAluno(Aluno aluno) {
        raiz = editarAlunoRec(raiz, aluno);
    }

    private Node editarAlunoRec(Node node, Aluno aluno) {
        if (node == null)
            return node;

        if (aluno.getMatricula() < node.aluno.getMatricula())
            node.esquerda = editarAlunoRec(node.esquerda, aluno);
        else if (aluno.getMatricula() > node.aluno.getMatricula())
            node.direita = editarAlunoRec(node.direita, aluno);
        else {
            node.aluno.setFaltas(aluno.getFaltas());
            node.aluno.setNota1(aluno.getNota1());
            node.aluno.setNota2(aluno.getNota2());
            node.aluno.setNota3(aluno.getNota3());

            double novaMedia = (aluno.getNota1() * 0.2) + (aluno.getNota2() * 0.35) + (aluno.getNota3() * 0.45);
            node.aluno.setMedia(novaMedia);

            node.altura = 1 + Math.max(altura(node.esquerda), altura(node.direita));
        }

        return balancear(node);
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
