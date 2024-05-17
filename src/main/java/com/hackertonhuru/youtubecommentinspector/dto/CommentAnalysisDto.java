package com.hackertonhuru.youtubecommentinspector.dto;

import com.hackertonhuru.youtubecommentinspector.controller.Comment;

import java.util.List;

public record CommentAnalysisDto(Rate rate, List<Comment> positiveResult, List<Comment>negativeResult) {

}
