package ru.binarysimple.users.filter;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.binarysimple.users.model.User;

public record UserFilter(String usernameStarts, String emailStarts, String phoneStarts) {
    public Specification<User> toSpecification() {
        return usernameStartsSpec()
                .and(emailStartsSpec())
                .and(phoneStartsSpec());
    }

    private Specification<User> usernameStartsSpec() {
        return ((root, query, cb) -> StringUtils.hasText(usernameStarts)
                ? cb.like(cb.lower(root.get("username")), usernameStarts.toLowerCase() + "%")
                : null);
    }

    private Specification<User> emailStartsSpec() {
        return ((root, query, cb) -> StringUtils.hasText(emailStarts)
                ? cb.like(cb.lower(root.get("email")), emailStarts.toLowerCase() + "%")
                : null);
    }

    private Specification<User> phoneStartsSpec() {
        return ((root, query, cb) -> StringUtils.hasText(phoneStarts)
                ? cb.like(root.get("phone"), phoneStarts + "%")
                : null);
    }
}