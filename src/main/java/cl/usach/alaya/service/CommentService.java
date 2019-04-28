package cl.usach.alaya.service;

import cl.usach.alaya.model.Comment;
import cl.usach.alaya.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment createComment(Comment comment) {
        comment.setCreateDate(new Date());
        comment.setUpdateDate(new Date());
        return commentRepository.save(comment);
    }

    public Iterable<Comment> getAll() {
        return commentRepository.findAll();
    }

    public Comment getOneComment(String id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.orElse(null);
    }

    public Comment updateComment(String id, Comment updateInfo) {
        Comment comment = getOneComment(id);
        comment.setText(updateInfo.getText());
        comment.setUpdateDate(new Date());
        return commentRepository.save(comment);
    }

    public void deleteComment(String id) {
        commentRepository.deleteById(id);
    }
}