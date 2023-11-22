package cz.osu.phoneappbackend.repository;

import cz.osu.phoneappbackend.model.CustomerConversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerConversationRepository extends JpaRepository<CustomerConversation, String> {
}