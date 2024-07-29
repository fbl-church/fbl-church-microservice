package com.fbl.app.user.client.domain.jpa;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * Description
 * 
 * @author Sam Butler
 * @since Jul 29, 2024
 */
public abstract class AbstractSearchSpecification<T> {
    public AbstractSearchSpecification() {
    }

    public Specification<T> build() {
        return (root, query, builder) -> {
            List<Predicate> predicates = this.getSearchPredicates(root, query, builder);
            return predicates.isEmpty() ? builder.conjunction()
                    : builder.or((Predicate[]) predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected abstract List<Predicate> getSearchPredicates(Root<?> root, CriteriaQuery<?> query,
            CriteriaBuilder builder);
}
