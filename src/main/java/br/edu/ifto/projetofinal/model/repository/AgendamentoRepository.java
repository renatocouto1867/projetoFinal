package br.edu.ifto.projetofinal.model.repository;

import br.edu.ifto.projetofinal.model.entity.Agendamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AgendamentoRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Agendamento agendamento) {
        em.persist(agendamento);
    }//save

    public Agendamento agendamento(Long id) {
        return em.find(Agendamento.class, id);
    } //Agendamento

    public List agendamentos() {
        Query query = em.createQuery("from Agendamento");
        return query.getResultList();
    }

    public void remove(Long id) {
        Agendamento agendamento = em.find(Agendamento.class, id);
        em.remove(agendamento);
    }//remove

    public List<Agendamento> pacientesNome(String parteDoNome) {
        String jpql = "SELECT p FROM Agendamento p WHERE LOWER(p.nome) LIKE LOWER(:parteDoNome)"; // lower para ignorar maiusculas e minusculas.
        Query query = em.createQuery(jpql, Agendamento.class);
        query.setParameter("parteDoNome", "%" + parteDoNome.toLowerCase() + "%");

        return query.getResultList();
    }

    public void update(Agendamento agendamento) {
        em.merge(agendamento);
    }//update

}
