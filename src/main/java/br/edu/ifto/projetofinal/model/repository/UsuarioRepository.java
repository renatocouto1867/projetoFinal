package br.edu.ifto.projetofinal.model.repository;

import br.edu.ifto.projetofinal.model.entity.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager em;

    public Usuario usuario(String login) {
        try {
            String jpql = "from Usuario u where u.login = :login";
            TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
            query.setParameter("login", login);
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

    }

    public void save(Usuario usuario) {
        em.persist(usuario);
    }

}