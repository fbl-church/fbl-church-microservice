package com.fbl.app.user.client.domain.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.Setter;

/**
 * Holds search criteria and turns them into query predicates
 *
 * @author Sam Butler
 * @since Jul 29, 2024
 */
@Getter
@Setter
public class UserSearch extends AbstractSearchSpecification<UserEntity> {

    private Set<String> search;

    @Override
    public List<Predicate> getSearchPredicates(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (CollectionUtils.isEmpty(search)) {
            return predicates;
        }

        for (String term : search) {
            String like = "%" + term.toLowerCase() + "%";
            predicates.add(builder.like(builder.lower(root.get("firstName")), like));
            predicates.add(builder.like(builder.lower(root.get("lastName")), like));
            predicates.add(builder.like(builder.lower(root.get("email")), like));
        }

        return predicates;
    }
}