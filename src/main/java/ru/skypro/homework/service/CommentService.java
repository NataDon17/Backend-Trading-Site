package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.comment.CommentDTO;
import ru.skypro.homework.dto.comment.CommentsDTO;
import ru.skypro.homework.dto.comment.CreateOrUpdateCommentDTO;

/**
 * Interface with CRUD methods for comment
 */

public interface CommentService {
    CommentsDTO getComments(int adId);

    CommentDTO addComment(int adId, CreateOrUpdateCommentDTO comment);

    void deleteComment(Authentication authentication, int adId, int commentId);

    CommentDTO updateComment(Authentication authentication,int adId, int commentId, CreateOrUpdateCommentDTO comment);
}
