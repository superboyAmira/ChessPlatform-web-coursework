package ru.chessplatform.infrastructure.hibernate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.repository.NoRepositoryBean;

import ru.chessplatform.domain.model.entity.BaseEntity;

@NoRepositoryBean
public abstract class GeneralRepository<E> {

    @PersistenceContext
    private EntityManager entityManager;
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    private final Class<E> entityClass;

    protected GeneralRepository(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Transactional
    public void save(E entity) {
        if (entity instanceof BaseEntity && findById(((BaseEntity) entity).getId()).isPresent()) {
            entityManager.merge(entity);
        } else {
            entityManager.persist(entity);
        }
    }

    public Optional<E> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    public List<E> findAll(int limit, int offset) {
        return entityManager.createQuery("FROM " + entityClass.getSimpleName(), entityClass)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    // @Transactional
    // public void deleteById(UUID id) {
    //     findById(id).ifPresent(entityManager::remove);
    // }
}
