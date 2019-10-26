package com.nju.netease.tests;

import com.nju.netease.model.CommentData;
import com.nju.netease.respository.CommentDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private CommentDataRepository commentDataRepository;


    @Test
    public void test() {
        CommentData commentData = commentDataRepository.findBySongId(510034511);
        System.out.println(commentData.toString());
    }
}
