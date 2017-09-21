package org.axonframework.labs.president;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("matchViewRepository")
public interface MatchRepository extends JpaRepository<MatchView, String> {
}
