package com.project.blog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
//@EqualsAndHashCode(exclude = {"post"})
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime dateCreated;
    private LocalDateTime dateEdited;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.REFRESH, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;
    @JsonIgnore
    @ManyToOne
    public BlogUser creator;

    public Comment() {
    }

    public Comment(Long id, String content, LocalDateTime dateCreated, LocalDateTime dateEdited, Post post,
                   BlogUser creator) {
        this.id = id;
        this.content = content;
        this.dateCreated = dateCreated;
        this.dateEdited = dateEdited;
        this.post = post;
        this.creator = creator;
    }

    public Comment(Long id, String content, Post post, BlogUser creator) {
        this.id = id;
        this.content = content;
        this.post = post;
        this.creator = creator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateEdited() {
        return dateEdited;
    }

    public void setDateEdited(LocalDateTime dateEdited) {
        this.dateEdited = dateEdited;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public BlogUser getCreator() {
        return creator;
    }

    public void setCreator(BlogUser creator) {
        this.creator = creator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(content, comment.content) && Objects.equals(dateCreated, comment.dateCreated) && Objects.equals(dateEdited, comment.dateEdited) && Objects.equals(creator, comment.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, dateCreated, dateEdited, creator);
    }
}
