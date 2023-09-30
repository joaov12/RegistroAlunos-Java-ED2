package Util;

import Entities.Aluno;

public class Node {
    public Aluno aluno;
    public Node esquerda, direita;
    public int altura;

    public Node(Aluno aluno) {
        this.aluno = aluno;
        this.altura = 1;
    }
}