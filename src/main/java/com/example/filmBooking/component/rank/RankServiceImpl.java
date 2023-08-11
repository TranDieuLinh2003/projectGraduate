package com.example.filmBooking.component.rank;

import com.example.filmBooking.model.Rank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class RankServiceImpl implements RankService {

    @Autowired
    private RankRepository repository;

    @Override
    public List<Rank> fillAll() {
        return repository.findAll();
    }

    @Override
    public Rank save(Rank rank) {
        Random generator = new Random();
        int value = generator.nextInt((100000 - 1) + 1) + 1;
        rank.setCode("code_" + value);
        return repository.save(rank);
    }

    @Override
    public Rank update(UUID id, Rank rank) {
        Rank rankNew = findById(id);
        rankNew.setName(rank.getName());
        rankNew.setPoint(rank.getPoint());
        rankNew.setDescription(rank.getDescription());
        return repository.save(rankNew);
    }

    @Override
    public Rank findById(UUID id) {
        return repository.findById(id).get();
    }

    @Override
    public void delete(UUID id) {
        repository.delete(findById(id));
    }
}
