package org.axonframework.labs.president;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<MatchView, String> {
}
