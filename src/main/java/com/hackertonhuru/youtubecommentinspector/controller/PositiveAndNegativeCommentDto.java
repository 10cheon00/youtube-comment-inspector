package com.hackertonhuru.youtubecommentinspector.controller;

import java.util.List;

public record PositiveAndNegativeCommentDto(
        List<Comment> positive,
        List<Comment> negative) {
}
