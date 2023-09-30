package Entities;

public class Aluno {
    int matricula;
    String nome;
    int faltas;
    double nota1, nota2, nota3;
    double media;

    public Aluno() {
        super();
    }

    public Aluno(int matricula, String nome, int faltas, double nota1, double nota2, double nota3) {
        this.matricula = matricula;
        this.nome = nome;
        this.faltas = faltas;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.media = calcularMedia();
    }

    public Aluno editarAluno(int faltas, double nota1, double nota2, double nota3){
        Aluno alunoEditado = new Aluno();
        alunoEditado.setFaltas(faltas);
        alunoEditado.setNota1(nota1);
        alunoEditado.setNota2(nota2);
        alunoEditado.setNota3(nota3);
        alunoEditado.calcularMedia();
        return alunoEditado;
    }

    public double calcularMedia() {
        return (nota1 * 0.2) + (nota2 * 0.35) + (nota3 * 0.45);
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public void setNota1(double nota1) {
        this.nota1 = nota1;
    }

    public void setNota2(double nota2) {
        this.nota2 = nota2;
    }

    public void setNota3(double nota3) {
        this.nota3 = nota3;
    }

    public int getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public int getFaltas() {
        return faltas;
    }

    public double getNota1() {
        return nota1;
    }

    public double getNota2() {
        return nota2;
    }

    public double getNota3() {
        return nota3;
    }

    public double getMedia() {
        return media;
    }

    @Override
    public String toString() {
        return "Matrícula: " + matricula + ", Nome: " + nome + ", Faltas: " + faltas + ", Nota1: " + nota1 + ", Nota2: "
                + nota2 + ", Nota3: " + nota3 + ", Média: " + media;
    }
}