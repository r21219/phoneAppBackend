package cz.osu.phoneappbackend.repository;

import cz.osu.phoneappbackend.model.conversation.CustomerConversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerConversationRepository extends JpaRepository<CustomerConversation, String> {
    CustomerConversation findByConversationNameAndCustomers_UserName(String conversationName, String customerName);
    Optional<List<CustomerConversation>> findAllByCustomers_UserName(String customerName);
}