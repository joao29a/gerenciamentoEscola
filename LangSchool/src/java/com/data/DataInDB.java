package com.data;

import com.entity.Aluno;
import com.entity.Curso;
import com.entity.FluxoCaixa;
import com.entity.LogIn;
import com.entity.Matricula;
import com.entity.Mensalidade;
import com.entity.Nivel;
import com.entity.Nota;
import com.entity.Professor;
import com.entity.Turma;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataInDB {

    private static final EntityPersist ep = new EntityPersist();

    private static Object getObjectClass(Class cName, int id) {
        return ep.search(cName, new CriteriaGroup("eq", "id", id, null)).get(0);
    }

    public static void addAll() {
        addAlunos();
        addProfessores();
        addCursos();
        addNiveis();
        addTurmas();
        addLogin();
        addMatriculas();
        addMensalidades(2, 1);
        addFluxoCaixa();
    }

    public static void addAlunos() {
        try {
            ep.save(new Aluno("Marcos Augusto", new Date(1993, 2, 17), "111111111",
                    "13", "rua 11111 -111", "PR", "Maringa", 'M'));
            ep.save(new Aluno("Lucas SSolipra", new Date(1992, 4, 27), "222222222",
                    "29", "rua 22222 -22", "PR", "Astorga", 'F'));
            ep.save(new Aluno("Joao Miranda", new Date(1989, 11, 13), "33333333",
                    "2", "rua 3333 3-333", "PR", "Arapongas", 'M'));
            ep.save(new Aluno("Claudio Santos", new Date(2000, 7, 9), "4444444444",
                    "29", "rua 444444- -444", "PR", "Campo Mourao", 'M'));
            ep.save(new Aluno("Rodrigo Guilherme", new Date(1991, 12, 29), "5555555555",
                    "37", "rua 55555 -555", "PR", "Foz do Iguacu", 'M'));
        } catch (Exception ex) {
            Logger.getLogger(DataInDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addCursos() {
        try {
            ep.save(new Curso("Japones", "Um curso para o aprendizado de japones."));
            ep.save(new Curso("Ingles", "Um curso para o aprendizado de ingles."));
            ep.save(new Curso("Espanhol", "Um curso para o aprendizado de espanhol."));
        } catch (Exception ex) {
            Logger.getLogger(DataInDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addNiveis() {
        try {
            ep.save(new Nivel("Japones Basico", "Curso basico de japones", 30,
                    (Curso) ep.search(Curso.class, new CriteriaGroup("eq", "nome", "Japones", null)).get(0)));
            ep.save(new Nivel("Ingles Basico", "Curso basico de ingles", 30,
                    (Curso) ep.search(Curso.class, new CriteriaGroup("eq", "nome", "Ingles", null)).get(0)));
            ep.save(new Nivel("Espanhol Basico", "Curso basico de espanhol", 30,
                    (Curso) ep.search(Curso.class, new CriteriaGroup("eq", "nome", "Espanhol", null)).get(0)));

            ep.save(new Nivel("Japones Intermediario", "Curso intermediario de japones", 30,
                    (Curso) ep.search(Curso.class, new CriteriaGroup("eq", "nome", "Japones", null)).get(0)));
            ep.save(new Nivel("Ingles Intermediario", "Curso intermediario de ingles", 30,
                    (Curso) ep.search(Curso.class, new CriteriaGroup("eq", "nome", "Ingles", null)).get(0)));
            ep.save(new Nivel("Espanhol Intermediario", "Curso intermediario de espanhol", 30,
                    (Curso) ep.search(Curso.class, new CriteriaGroup("eq", "nome", "Espanhol", null)).get(0)));

            ep.save(new Nivel("Japones Avancado", "Curso avancado de japones", 30,
                    (Curso) ep.search(Curso.class, new CriteriaGroup("eq", "nome", "Japones", null)).get(0)));
            ep.save(new Nivel("Ingles Avancado", "Curso avancado de ingles", 30,
                    (Curso) ep.search(Curso.class, new CriteriaGroup("eq", "nome", "Ingles", null)).get(0)));
            ep.save(new Nivel("Espanhol Avancado", "Curso avancado de espanhol", 30,
                    (Curso) ep.search(Curso.class, new CriteriaGroup("eq", "nome", "Espanhol", null)).get(0)));
        } catch (Exception ex) {
            Logger.getLogger(DataInDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addProfessores() {
        try {
            ep.save(new Professor("Jose Almeida", "1111111111", "25", "Rua 1-1", "PR", "Maringa", 'M'));
            ep.save(new Professor("Alberto Siraichi", "2222222222", "26", "Rua 2-2", "SP", "Sao Paulo", 'M'));
            ep.save(new Professor("Joao Uyeno", "3333333333", "27", "Rua 3-3", "MG", "Belo Horizonte", 'M'));
            ep.save(new Professor("Carla Perassoli", "4444444444", "28", "Rua 4-4", "PE", "Recife", 'F'));
            ep.save(new Professor("Icaro Juda", "5555555555", "29", "Rua 5-5", "GO", "Goiania", 'M'));
        } catch (Exception ex) {
            Logger.getLogger(DataInDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addTurmas() {
        try {
            ep.save(new Turma((Professor) getObjectClass(Professor.class, 1),
                    (Nivel) getObjectClass(Nivel.class, 5), "Turma 1", "Turma do barulho", 30, 30));
            ep.save(new Turma((Professor) getObjectClass(Professor.class, 3),
                    (Nivel) getObjectClass(Nivel.class, 2), "Turma 2", "Turma de meth", 30, 30));
            ep.save(new Turma((Professor) getObjectClass(Professor.class, 1),
                    (Nivel) getObjectClass(Nivel.class, 8), "Turma 3", "Turma dividida ou nao", 30, 30));
            ep.save(new Turma((Professor) getObjectClass(Professor.class, 2),
                    (Nivel) getObjectClass(Nivel.class, 5), "Turma 4", "Turma 0 por 0", 30, 30));
            ep.save(new Turma((Professor) getObjectClass(Professor.class, 4),
                    (Nivel) getObjectClass(Nivel.class, 1), "Turma 5", "Turma japan", 30, 30));
            ep.save(new Turma((Professor) getObjectClass(Professor.class, 5),
                    (Nivel) getObjectClass(Nivel.class, 3), "Turma 6", "Turma spain", 30, 30));
        } catch (Exception ex) {
            Logger.getLogger(DataInDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addLogin() {
        try {
            ep.save(new LogIn("Sec", "secretario", (Professor) getObjectClass(Professor.class, 1), 1));
            ep.save(new LogIn("Prof", "professor", (Professor) getObjectClass(Professor.class, 3), 0));
        } catch (Exception ex) {
            Logger.getLogger(DataInDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void addMat(Aluno a, Turma t) {
        Matricula m = new Matricula(a, t, new Date());
        m.setNotas(new Nota());
        m.getTurma().setVagasRest(m.getTurma().getVagasRest() - 1);
        try {
            ep.save(m.getNotas());
            ep.update(m.getTurma());
            ep.save(m);
        } catch (Exception ex) {
            Logger.getLogger(DataInDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addMatriculas() {
        addMat((Aluno) getObjectClass(Aluno.class, 1), (Turma) getObjectClass(Turma.class, 3));
        addMat((Aluno) getObjectClass(Aluno.class, 2), (Turma) getObjectClass(Turma.class, 2));
        addMat((Aluno) getObjectClass(Aluno.class, 3), (Turma) getObjectClass(Turma.class, 2));
        addMat((Aluno) getObjectClass(Aluno.class, 4), (Turma) getObjectClass(Turma.class, 3));
        addMat((Aluno) getObjectClass(Aluno.class, 4), (Turma) getObjectClass(Turma.class, 2));
        addMat((Aluno) getObjectClass(Aluno.class, 3), (Turma) getObjectClass(Turma.class, 3));
        addMat((Aluno) getObjectClass(Aluno.class, 2), (Turma) getObjectClass(Turma.class, 3));
    }

    public static void addMensalidades(int n, int start) {
        try {
            //Mensalidade
            for (int i = start; i < start+n; i++) {
                System.out.println("i " + i);
                ep.save(new Mensalidade((Matricula) getObjectClass(Matricula.class, i), "jan"));
                ep.save(new Mensalidade((Matricula) getObjectClass(Matricula.class, i), "fev"));
                ep.save(new Mensalidade((Matricula) getObjectClass(Matricula.class, i), "mar"));
                ep.save(new Mensalidade((Matricula) getObjectClass(Matricula.class, i), "abr"));
                ep.save(new Mensalidade((Matricula) getObjectClass(Matricula.class, i), "mai"));
                ep.save(new Mensalidade((Matricula) getObjectClass(Matricula.class, i), "jun"));
                ep.save(new Mensalidade((Matricula) getObjectClass(Matricula.class, i), "jul"));
                ep.save(new Mensalidade((Matricula) getObjectClass(Matricula.class, i), "ago"));
                ep.save(new Mensalidade((Matricula) getObjectClass(Matricula.class, i), "set"));
                ep.save(new Mensalidade((Matricula) getObjectClass(Matricula.class, i), "out"));
                ep.save(new Mensalidade((Matricula) getObjectClass(Matricula.class, i), "nov"));
                ep.save(new Mensalidade((Matricula) getObjectClass(Matricula.class, i), "dez"));
            }
        } catch (Exception ex) {
            Logger.getLogger(DataInDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addFluxoCaixa() {
        try {
            ep.save(new FluxoCaixa("xerox", (float) 0.25, new Date(), "Cred"));
            ep.save(new FluxoCaixa("Material de Escritorio", (float) 4.00, new Date(), "Deb"));
            ep.save(new FluxoCaixa("3 Livros", (float) 150.60, new Date(), "Deb"));
        } catch (Exception ex) {
            Logger.getLogger(DataInDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
