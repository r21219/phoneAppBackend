package cz.osu.phoneappbackend.repository;

import cz.osu.phoneappbackend.model.conversation.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, String> {
    Optional<Conversation> findByConversationNameAndCustomers_UserName(String conversationName, String customerName);
    Optional<List<Conversation>> findAllByCustomers_UserName(String customerName);

}