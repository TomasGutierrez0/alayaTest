package cl.usach.alaya.service;

import cl.usach.alaya.model.Challenge;
import cl.usach.alaya.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChallengeService {
    @Autowired
    private ChallengeRepository challengeRepository;

    public Challenge create(Challenge challenge, Date deadline) {
        challenge.setDeadline(deadline);
        challenge.setCreateDate(new Date());
        challenge.setUpdateDate(new Date());
        return challengeRepository.save(challenge);
    }

    public Iterable<Challenge> getAll() {
        return challengeRepository.findAll();
    }

    public Challenge getOne(String id) {
        Optional<Challenge> challenge = challengeRepository.findById(id);
        return challenge.orElse(null);
    }

    public Iterable<Challenge> getAllChallengesOfCompany(String id) {
        return challengeRepository.findByUserId(id);
    }

    public Challenge update(String id, Challenge updateInfo, Date deadline) {
        Challenge challenge = getOne(id);
        challenge.setTitle(updateInfo.getTitle());
        challenge.setText(updateInfo.getText());
        challenge.setInterestArea(updateInfo.getInterestArea());
        challenge.setDeadline(deadline);
        challenge.setUpdateDate(new Date());
        return challengeRepository.save(challenge);
    }

    public void delete(String id) {
        challengeRepository.deleteById(id);
    }
}
