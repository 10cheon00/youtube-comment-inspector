package com.hackertonhuru.youtubecommentinspector.controller;

import com.hackertonhuru.youtubecommentinspector.dto.CommentAnalysisDto;
import com.hackertonhuru.youtubecommentinspector.dto.CommentStatisticDto;
import com.hackertonhuru.youtubecommentinspector.util.YoutubeUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/")
public class YoutubeLinkController {
    private final YoutubeUtil youtubeUtil;
    private final RestTemplate restTemplate;

    @Autowired
    public YoutubeLinkController(YoutubeUtil youtubeUtil, RestTemplate restTemplate) {
        this.youtubeUtil = youtubeUtil;
        this.restTemplate = restTemplate;
    }

    @GetMapping("")
    public String test() {
        return "test";
    }

    @GetMapping("comments")
    public CommentStatisticDto getComments(@RequestParam String videoId, HttpServletRequest request) throws Exception {
        List<String> allComment = youtubeUtil.getAllComment(videoId);
        String url = "129.154.211.230:5000/anaysis";
        CommentAnalysisDto commentAnalysisDto = restTemplate.getForObject(url, CommentAnalysisDto.class);
        return new CommentStatisticDto(
                commentAnalysisDto.rate(),
                new PositiveAndNegativeCommentDto(
                        commentAnalysisDto.positiveResult().subList(0, 5),
                        commentAnalysisDto.negativeResult().subList(0, 5)));
    }
}