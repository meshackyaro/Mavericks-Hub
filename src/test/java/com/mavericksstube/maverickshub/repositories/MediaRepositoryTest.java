package com.mavericksstube.maverickshub.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class MediaRepositoryTest {

    @Autowired
    private MediaRepository mediaRepository;

    @Test
    public void findAllMediaFor(){
        assertThat(mediaRepository.findAllMediaFor(200L)).hasSize(3);
    }
}
