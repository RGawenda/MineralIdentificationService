package com.mineralidentificationservice.specification;

import com.mineralidentificationservice.model.FoundMineral;
import com.mineralidentificationservice.model.Tags;
import com.mineralidentificationservice.model.TagsFoundMineral;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.List;

public class FoundMineralSpecifications {

    public static Specification<FoundMineral> findByMineralAndMohsScaleAndTagsAndFields(FoundMineralFilter foundMineralFilter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            root.join("accountId");
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("accountId").get("id"), foundMineralFilter.getUserID()));


            root.join("mineralId");

            if (foundMineralFilter.getMineralName() != null && !foundMineralFilter.getMineralName().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("mineralId").get("mineralName"), foundMineralFilter.getMineralName()));
            }

            if (foundMineralFilter.getMinMohsScale() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("mineralId").get("mohsScale"), foundMineralFilter.getMinMohsScale()));
            }

            if (foundMineralFilter.getMaxMohsScale() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("mineralId").get("mohsScale"), foundMineralFilter.getMaxMohsScale()));
            }

            Join<FoundMineral, TagsFoundMineral> tagsFoundMineralJoin = root.join("tags", JoinType.LEFT);
            Join<TagsFoundMineral, Tags> tagsJoin = tagsFoundMineralJoin.join("tagId", JoinType.LEFT);

            if (foundMineralFilter.getTagName() != null && !foundMineralFilter.getTagName().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(tagsJoin.get("tagName"), foundMineralFilter.getTagName()));
            }

            if (foundMineralFilter.getName() != null && !foundMineralFilter.getName().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("name"), "%" + foundMineralFilter.getName() + "%"));
            }

            if (foundMineralFilter.getComment() != null && !foundMineralFilter.getComment().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("comment"), "%" + foundMineralFilter.getComment() + "%"));
            }

            if (foundMineralFilter.getDiscoveryPlace() != null && !foundMineralFilter.getDiscoveryPlace().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("discoveryPlace"), "%" + foundMineralFilter.getDiscoveryPlace() + "%"));
            }

            if (foundMineralFilter.getMinValue() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("value"), String.valueOf(foundMineralFilter.getMinValue())));
            }
            if (foundMineralFilter.getMaxValue() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("value"), String.valueOf(foundMineralFilter.getMaxValue())));
            }

            if (foundMineralFilter.getMinSize() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("size"), String.valueOf(foundMineralFilter.getMinSize())));
            }
            if (foundMineralFilter.getMaxSize() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("size"), String.valueOf(foundMineralFilter.getMaxSize())));
            }

            if (foundMineralFilter.getMinWeight() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("weight"), String.valueOf(foundMineralFilter.getMinWeight())));
            }
            if (foundMineralFilter.getMaxWeight() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("weight"), String.valueOf(foundMineralFilter.getMaxWeight())));
            }

            if (foundMineralFilter.getIsNotNullInclusion() != null && foundMineralFilter.getIsNotNullInclusion()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.isNotNull(root.get("inclusion")));
            }

            if (foundMineralFilter.getIsNotNullClarity() != null && foundMineralFilter.getIsNotNullClarity()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.isNotNull(root.get("clarity")));
            }

            return predicate;
        };
    }
}
