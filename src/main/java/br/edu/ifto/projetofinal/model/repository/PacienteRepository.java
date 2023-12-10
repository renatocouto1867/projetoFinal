package br.edu.ifto.projetofinal.model.repository;

import br.edu.ifto.projetofinal.model.entity.Consulta;
import br.edu.ifto.projetofinal.model.entity.Paciente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PacienteRepository {
    @PersistenceContext
    private EntityManager em;

    public void save (Paciente paciente){
        em.persist(paciente);
    }//save

    public Paciente paciente (Long id){
        return em.find(Paciente.class, id);
    } //paciente

    public List pacientes (){
        Query query = em.createQuery("from Paciente");
        return query.getResultList();
    }

    public void remove (Long id){
        Paciente paciente = em.find(Paciente.class, id);
        em.remove(paciente);
    }//remove

    public List<Paciente> pacientesNome(String parteDoNome) {
        String jpql = "SELECT p FROM Paciente p WHERE LOWER(p.nome) LIKE LOWER(:parteDoNome)"; // lower para ignorar maiusculas e minusculas.
        Query query = em.createQuery(jpql, Paciente.class);
        query.setParameter("parteDoNome", "%" + parteDoNome.toLowerCase() + "%");

        return query.getResultList();
    }



    public void update(Paciente paciente){
        em.merge(paciente);
    }//update

}
