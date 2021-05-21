package com.example.demo.mapper;

import com.example.demo.entity.Book;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class BookMapperTest {
    @Autowired
    BookMapper bookMapper;

    @Test
    void insertBook() {
        Book book = new Book("a", "a", "a", "a", "a", "a", "a");
        int res = bookMapper.insertBook(book);
        assertEquals(res, 1);
    }

    @Test
    void getBookByTitle() {
        Book book = bookMapper.getBookByTitle("算法导论");
        assertNotNull(book);
    }

    @Test
    void findAllBooks() {
        List<Book> books = bookMapper.findAllBooks();
        assertNotNull(books);
    }
}