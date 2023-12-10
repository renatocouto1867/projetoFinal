package br.edu.ifto.projetofinal.model.repository;
import br.edu.ifto.projetofinal.model.entity.Consulta;
import br.edu.ifto.projetofinal.model.entity.Medico;
import br.edu.ifto.projetofinal.model.entity.Paciente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ConsultaRepository {
    @PersistenceContext
    private EntityManager em;

    public void save (Consulta consulta){
        em.persist(consulta);
    }//save

    public Consulta consulta(Long id){
        return em.find(Consulta.class, id);
    } //consulta

    public List consultas(){
        Query query = em.createQuery("from Consulta");
        return query.getResultList();
    }//consultas

    public Double valorTotal(){
        Query query = em.createQuery("select sum(valor) from Consulta");
        List total;
        total= query.getResultList();
        return (Double) total.get(0);
    }//consultas

    public List<Consulta> consultasDoMedico(Medico medico) {
        Query query = em.createQuery("SELECT c FROM Consulta c WHERE c.medico = :medico");
        query.setParameter("medico", medico);
        return query.getResultList();
    }

    public Double valorTotalMedico(Medico medico) {
        Query query = em.createQuery("SELECT sum(c.valor) FROM Consulta c WHERE c.medico = :medico");
        query.setParameter("medico", medico);

        List<Double> total = query.getResultList();

        return total.isEmpty() ? 0.0 : total.get(0);
    }

    public List<Consulta> consultasDoMedicoEpaciente(Medico medico, Paciente paciente) {
        Query query = em.createQuery("SELECT c FROM Consulta c WHERE c.medico = :medico AND c.paciente = :paciente");
        query.setParameter("medico", medico);
        query.setParameter("paciente", paciente);
        return query.getResultList();
    }

    public Double valorTotalMedicoEpaciente(Medico medico, Paciente paciente) {
        Query query = em.createQuery("SELECT sum(c.valor) FROM Consulta c WHERE c.medico = :medico AND c.paciente = :paciente");
        query.setParameter("medico", medico);
        query.setParameter("paciente", paciente);
        List<Double> total = query.getResultList();
        return total.isEmpty() || total.get(0) == null ? 0.0 : total.get(0);
    }




    public void remove (Long id){
        Consulta consulta = em.find(Consulta.class, id);
        em.remove(consulta);
    }//remove

    public void update(Consulta consulta){
        em.merge(consulta);
    }//update

}
