package com.example.demo.mapper;

import com.example.demo.dao.CollectionInfo;
import com.example.demo.entity.Collection;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CollectionMapperTest {
    @Autowired
    CollectionMapper collectionMapper;

    @Test
    void insertCollection() {
        Collection collection = new Collection("username", "title");
        int res = collectionMapper.insertCollection(collection);
        assertEquals(res, 1);
    }

    @Test
    void getCollectionByUsername() {
        List<CollectionInfo> collections = collectionMapper.getCollectionByUsername("username");
        assertNotNull(collections);
    }

    @Test
    void deleteCollection() {
        Collection collection = new Collection("username", "title");
        int res = collectionMapper.deleteCollection(collection);
        assertEquals(res, 1);
    }
}