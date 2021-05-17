package com.example.demo.controller;

import com.example.demo.dao.CollectionInfo;
import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BookController {
    @Autowired
    BookService bookService;


    /**
     * 获得书列表
     * @return
     */
    @RequestMapping(path = "/books",method = RequestMethod.GET)
    public Map getBookes() {
        List<Book>books =  bookService.findAllBooks();
        Map<String,Object> result = new HashMap<>();
        result.put("msg","");
        result.put("code",200);
        result.put("data",books);

        return result;
    }

    /**
     * 获得特定书信息
     * @param title
     * @return
     */
    @RequestMapping(path = "/books/{title}",method = RequestMethod.GET)
    public Map getBookByTitle(@PathVariable("title")String title) {
        Book book = bookService.getBookByTitle(title);
        Map <String,Object> map = new HashMap<>();
        map.put("msg","");
        map.put("code",200);
        map.put("data",book);
        return map;
    }

    /**
     * 获得个人收藏信息
     * @param username
     * @return
     */
    @RequestMapping(path = "/user/{username}//collections",method = RequestMethod.GET)
    public Map getCollectionByUsername(@PathVariable("username") String username){
        Map<String,Object> result =new HashMap();
        List<CollectionInfo> collections = bookService.getCollectionByUsername(username);
        result.put("msg","");
        result.put("code",200);
        result.put("data",collections);

        return result;
    }

    /**
     * 查看某书是否被收藏
     * @param username
     * @param title
     * @return
     */
    @RequestMapping(path = "/user/{username}/collections/{title}",method = RequestMethod.GET)
    public Map getCollection(@PathVariable("username") String username,@PathVariable("title")String title){
        Map<String,Object> result =new HashMap();
        boolean isCollection = bookService.isCollect(username,title);
        result.put("msg","");
        result.put("code",200);
        result.put("data",isCollection);

        return result;
    }

    /**
     * 添加收藏
     * @param username
     * @param title
     * @return
     */
    @RequestMapping(path = "/user/{username}/collections/{title}",method = RequestMethod.POST)
    public Map addCollection(@PathVariable("username")String username,@PathVariable("title")String title) {
        bookService.setCollection(username,title);

        Map <String,Object> result = new HashMap<>();
        result.put("msg","");
        result.put("code",200);
        return result;
    }

    @RequestMapping(path = "/user/{username}/collections/{title}",method = RequestMethod.DELETE)
    public Map deleteCollection(@PathVariable("username")String username,@PathVariable("title")String title) {
        bookService.deleteCollection(username,title);

        Map <String,Object> result = new HashMap<>();
        result.put("msg","");
        result.put("code",200);
        return result;
    }
}
