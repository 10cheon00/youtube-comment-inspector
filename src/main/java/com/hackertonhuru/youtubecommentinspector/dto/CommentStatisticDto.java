package com.hackertonhuru.youtubecommentinspector.dto;

import com.hackertonhuru.youtubecommentinspector.controller.PositiveAndNegativeCommentDto;

public record CommentStatisticDto(Rate rate, PositiveAndNegativeCommentDto comments) {}
