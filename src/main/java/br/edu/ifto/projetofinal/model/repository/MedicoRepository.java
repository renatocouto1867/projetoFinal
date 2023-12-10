package br.edu.ifto.projetofinal.model.repository;

import br.edu.ifto.projetofinal.model.entity.Medico;
import br.edu.ifto.projetofinal.model.entity.Paciente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicoRepository {
    @PersistenceContext
    private EntityManager em;

    public void save (Medico medico){
        em.persist(medico);
    }//save

    public Medico medico (Long id){
        return em.find(Medico.class, id);
    } //medico

    public List medicos (){
        Query query = em.createQuery("from Medico");
        return query.getResultList();
    }

    public void remove (Long id){
        Medico medico = em.find(Medico.class, id);
        em.remove(medico);
    }//remove

    public void update(Medico medico){
        em.merge(medico);
    }//update

    public List<Medico> medicoNome(String parteDoNome) {
        String jpql = "SELECT p FROM Medico p WHERE LOWER(p.nome) LIKE LOWER(:parteDoNome)"; // lower para ignorar maiusculas e minusculas.
        Query query = em.createQuery(jpql, Medico.class);
        query.setParameter("parteDoNome", "%" + parteDoNome.toLowerCase() + "%");

        return query.getResultList();
    }
}
