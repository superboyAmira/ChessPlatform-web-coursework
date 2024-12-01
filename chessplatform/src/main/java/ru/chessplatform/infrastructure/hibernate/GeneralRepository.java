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
        if (entity instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) entity;
            if (baseEntity.getId() != null && findById(baseEntity.getId()).isPresent()) {
                entityManager.merge(entity); // Если ID существует и сущность найдена
            } else {
                entityManager.persist(entity); // Если ID отсутствует или сущность новая
            }
        } else {
            throw new IllegalArgumentException("Entity must extend BaseEntity");
        }
    }


    public Optional<E> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    public List<E> findAll(int limit, int offset) {
        String query = "FROM " + entityClass.getSimpleName() + " ORDER BY id";
        List <E> res = entityManager.createQuery(query, entityClass)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        return res;
    }

    // @Transactional
    // public void deleteById(UUID id) {
    //     findById(id).ifPresent(entityManager::remove);
    // }
}
