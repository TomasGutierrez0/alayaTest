package cl.usach.alaya.service;

import cl.usach.alaya.model.Idea;
import cl.usach.alaya.repository.IdeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class IdeaService {
    @Autowired
    private IdeaRepository ideaRepository;

    public Idea createIdea(Idea idea) {
        idea.setStatus("new idea");
        idea.setCreateDate(new Date());
        idea.setUpdateDate(new Date());
        return ideaRepository.save(idea);
    }

    public Iterable<Idea> getAll() {
        return ideaRepository.findAll();
    }

    public Iterable<Idea> getAllByChallenge(String challengeId) {
        return ideaRepository.findAllByChallengeId(challengeId);
    }

    public Iterable<Idea> getAllByUser(String userId) {
        return ideaRepository.findAllByUserId(userId);
    }

    public Idea getOneIdea(String id) {
        Optional<Idea> idea = ideaRepository.findById(id);
        return idea.orElse(null);
    }

    public Idea updateIdea(String id, Idea updateInfo) {
        Idea idea = getOneIdea(id);
        idea.setText(updateInfo.getText());
        idea.setUpdateDate(new Date());
        return ideaRepository.save(idea);
    }

    public void deleteIdea(String id) {
        ideaRepository.deleteById(id);
    }
}
