package com.project.blog.repositories;

import com.project.blog.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
//    @Query(Hibernate: select comment0_.id as id1_0_, comment0_.content as content2_0_, comment0_.date_created as date_cre3_0_, comment0_.date_edited as date_edi4_0_, comment0_.post_id as post_id5_0_ from comment comment0_ inner join post post1_ on comment0_.post_id=post1_.id where post1_.id=?)
//    @Query("SELECT com.project.blog.dtos.PostWithCommentsResponse(p.id, p.title, p.content, p.dateCreated, p.dateEdited, c) FROM Post p INNER JOIN Comment c ON p.id = c.post_id WHERE p.id = :pid")
//    PostWithCommentsResponse getPostWithComment(@Param("pid") Long pid);
}
