package com.hackertonhuru.youtubecommentinspector.controller;

import com.hackertonhuru.youtubecommentinspector.util.YoutubeUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/")
public class YoutubeLinkController {
    private final YoutubeUtil youtubeUtil;

    @Autowired
    public YoutubeLinkController(YoutubeUtil youtubeUtil) {
        this.youtubeUtil = youtubeUtil;
    }

    @GetMapping("")
    public String test() {
        return "test";
    }

    @GetMapping("comments")
    public List<String> getComments(@RequestParam String videoId, HttpServletRequest request) throws Exception {
        List<String> allComment = youtubeUtil.getAllComment(videoId);
        //asdf
        //
        // request external cloud ai server
        //
        System.out.println("asdfasdfa");
        return allComment;
    }
}