package com.project.blog.payloads;

import com.project.blog.entities.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

//@EqualsAndHashCode(exclude = {"comments"})
public class PostWithCommentsResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime created;
    private LocalDateTime edited;
    private List<Comment> comments;

    public PostWithCommentsResponse() {
    }

    public PostWithCommentsResponse(Long id, String title, String content, LocalDateTime created, LocalDateTime edited,
                                    List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.created = created;
        this.edited = edited;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getEdited() {
        return edited;
    }

    public void setEdited(LocalDateTime edited) {
        this.edited = edited;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostWithCommentsResponse that = (PostWithCommentsResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(content, that.content) && Objects.equals(created, that.created) && Objects.equals(edited, that.edited);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, created, edited);
    }
}
