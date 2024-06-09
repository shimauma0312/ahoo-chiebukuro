package com.shima.chiebukuro;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class QuestionRepositoryImpl implements QuestionRepository {

    @Override
    public List<Question> findAll() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("title1", "質問1質問1質問1質問1質問1質問1質問1質問1質問1質問1質問1質問1質問1質問1質問1質問1質問1質問1質問1質問1質問1質問1"));
        questions.add(new Question("title2", "質問2質問2質問2質問2質問2質問2質問2質問2質問2質問2質問2質問2質問2質問2質問2質問2質問2質問2質問2質問2質問2"));
        questions.add(new Question("title3", "質問3質問3質問3質問3質問3質問3質問3質問3質問3質問3質問3質問3質問3質問3質問3質問3質問3質問3質問3質問3"));

        return questions;
    }
}
