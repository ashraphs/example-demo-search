package com.example.search.exampledemosearch.repositories.impl;


import com.example.search.exampledemosearch.repositories.MasterEntityRepository;
import com.touchngo.abt.utils.entities.MasterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @param <T>
 * @param
 * @author kent
 */
public class MasterEntityRepositoryImpl<T extends MasterEntity> extends BaseEntityRepositoryImpl<T>
        implements MasterEntityRepository<T> {

    private static final String DELETED = "deleted";

    public MasterEntityRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#
     * findAll()
     */
    @Override
    public List<T> findAll() {
        return super.findAll((root, query, cb) -> cb.and(cb.isFalse(root.get(DELETED))));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#
     * findAll(org.springframework.data.domain.Pageable)
     */
    @Override
    public Page<T> findAll(Pageable pageable) {
        return super.findAll((root, query, cb) -> cb.and(cb.isFalse(root.get(DELETED))), pageable);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#
     * findAll(org.springframework.data.domain.Sort)
     */
    @Override
    public List<T> findAll(Sort sort) {
        return super.findAll((root, query, cb) -> cb.and(cb.isFalse(root.get(DELETED))), sort);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#
     * findAll(org.springframework.data.jpa.domain.Specification)
     */
    @Override
    public List<T> findAll(Specification<T> spec) {
        return super.findAll(Specification.where(spec).and((root, query, cb) -> cb.and(cb.isFalse(root.get(DELETED)))));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#
     * findAll(org.springframework.data.jpa.domain.Specification,
     * org.springframework.data.domain.Pageable)
     */
    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return super.findAll(Specification.where(spec).and((root, query, cb) -> cb.and(cb.isFalse(root.get(DELETED)))),
                pageable);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#
     * findAll(org.springframework.data.jpa.domain.Specification,
     * org.springframework.data.domain.Sort)
     */
    @Override
    public List<T> findAll(Specification<T> spec, Sort sort) {
        return super.findAll(Specification.where(spec).and((root, query, cb) -> cb.and(cb.isFalse(root.get(DELETED)))),
                sort);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.data.jpa.repository.support.SimpleJpaRepository#count
     * ()
     */
    @Override
    public long count() {
        return count((root, query, cb) -> cb.and(cb.isFalse(root.get(DELETED))));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.data.jpa.repository.support.SimpleJpaRepository#count
     * (org.springframework.data.jpa.domain.Specification)
     */
    @Override
    public long count(Specification<T> spec) {
        return super.count(Specification.where(spec).and((root, query, cb) -> cb.and(cb.isFalse(root.get(DELETED)))));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#
     * delete(java.io.Serializable)
     */
    @Transactional
    @Override
    public void delete(String id) {
        T t = getOne(id);

        if (t.getRestricted()) {
            return;
        }

        t.setDeleted(true);
        saveAndFlush(t);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#
     * delete(java.lang.Object)
     */
    @Transactional
    @Override
    public void delete(T t) {
        if (t.getRestricted()) {
            return;
        }

        t.setDeleted(true);
        saveAndFlush(t);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#
     * delete(java.lang.Iterable)
     */
    @Transactional
    @Override
    public void delete(Iterable<? extends T> entities) {
        for (T t : entities) {
            if (t.getRestricted()) {
                return;
            }

            t.setDeleted(true);
            saveAndFlush(t);
        }
    }


}
