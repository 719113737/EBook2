package com.example.demo.controller;

import com.example.demo.dao.CollectionInfo;
import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import com.example.demo.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "书本操作模块")
public class BookController {
    @Autowired
    BookService bookService;


    /**
     * 获得书列表
     * @return
     */
    @Operation(description = "获得书籍列表")
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
    @Operation(description = "获得特定书籍信息")
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
    @Operation(description = "获得个人收藏信息")
    @RequestMapping(path = "/user/{username}/collections",method = RequestMethod.GET)
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
    @Operation(description = "查看某书是否被收藏")
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
    @Operation(description = "添加收藏")
    @RequestMapping(path = "/user/{username}/collections/{title}",method = RequestMethod.POST)
    public Map addCollection(@PathVariable("username")String username,@PathVariable("title")String title) {
        bookService.setCollection(username,title);

        Map <String,Object> result = new HashMap<>();
        result.put("msg","");
        result.put("code",200);
        return result;
    }

    @Operation(description = "删除收藏")
    @RequestMapping(path = "/user/{username}/collections/{title}",method = RequestMethod.DELETE)
    public Map deleteCollection(@PathVariable("username")String username,@PathVariable("title")String title) {
        bookService.deleteCollection(username,title);

        Map <String,Object> result = new HashMap<>();
        result.put("msg","");
        result.put("code",200);
        return result;
    }
}
