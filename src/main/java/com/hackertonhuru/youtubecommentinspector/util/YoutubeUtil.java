package com.hackertonhuru.youtubecommentinspector.util;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Comment;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.CommentThreadReplies;
import com.hackertonhuru.youtubecommentinspector.exception.YoutubeApiRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class YoutubeUtil {
    private final YouTube youtube;
    @Value("${youtube.api.key}")
    private String apiKey;
    private List<String> comments;

    public YoutubeUtil() {
        youtube = new YouTube.Builder(
                new NetHttpTransport(),
                new JacksonFactory(),
                request -> {
                }).build();
    }

    public List<String> getAllComment(String videoId) throws YoutubeApiRequestException, IOException {
        String pageToken = "";
        comments = new ArrayList<>();

        do {
            CommentThreadListResponse ctlr;
            try {
                ctlr = requestCommentThreadToYoutube(videoId, pageToken);
            } catch (IOException e) {
                throw new YoutubeApiRequestException();
            }

            extractAllCommentInCommentThread(ctlr);
            pageToken = ctlr.getNextPageToken();
        } while (pageToken != null);

        return comments;
    }

    private CommentThreadListResponse requestCommentThreadToYoutube(String videoId, String pageToken) throws IOException {
        return youtube.commentThreads().list(
                        Arrays.asList("id", "snippet", "replies"))
                .setVideoId(videoId)
                .setMaxResults(100L)
                .setKey(apiKey)
                .setPageToken(pageToken)
                .execute();
    }

    private void extractAllCommentInCommentThread(CommentThreadListResponse ctlr) {
        for (CommentThread commentThread : ctlr.getItems()) {
            comments.add(getCommentText(commentThread.getSnippet().getTopLevelComment()));

            CommentThreadReplies ctr = commentThread.getReplies();
            if (ctr != null) {
                extractAllRepliesInCommentThread(ctr);
            }
        }
    }

    private void extractAllRepliesInCommentThread(CommentThreadReplies ctr) {
        for (Comment comment : ctr.getComments()) {
            comments.add(getCommentText(comment));
        }
    }

    public String getCommentText(Comment comment) {
        return comment.getSnippet().getTextOriginal();
    }
}
