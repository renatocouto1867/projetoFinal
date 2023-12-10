package br.edu.ifto.projetofinal.model.repository;

import br.edu.ifto.projetofinal.model.entity.Agenda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AgendaRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Agenda agenda) {
        em.persist(agenda);
    }//save

    public Agenda agenda(Long id) {
        return em.find(Agenda.class, id);
    } //agenda

    public List agendamentos() {
        Query query = em.createQuery("from Agenda");
        return query.getResultList();
    }

    public void remove(Long id) {
        Agenda agenda = em.find(Agenda.class, id);
        em.remove(agenda);
    }//remove

    public void update(Agenda agenda) {
        em.merge(agenda);
    }//update

}
