package cz.osu.phoneappbackend.repository;

import cz.osu.phoneappbackend.model.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
