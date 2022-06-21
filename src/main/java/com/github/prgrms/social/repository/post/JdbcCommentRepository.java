package com.github.prgrms.social.repository.post;

import com.github.prgrms.social.model.commons.Id;
import com.github.prgrms.social.model.post.Comment;
import com.github.prgrms.social.model.post.Post;
import com.github.prgrms.social.model.post.Writer;
import com.github.prgrms.social.model.user.Email;
import com.github.prgrms.social.model.user.User;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.github.prgrms.social.util.DateTimeUtils.dateTimeOf;
import static java.util.Optional.ofNullable;

@Repository
public class JdbcCommentRepository implements CommentRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcCommentRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Comment insert(Comment comment) {
    // TODO comment 추가
    throw new NotImplementedException("구현이 필요합니다.");
  }

  @Override
  public void update(Comment comment) {
    jdbcTemplate.update(
      "UPDATE comments SET contents=? WHERE seq=?",
      comment.getContents(),
      comment.getSeq()
    );
  }

  @Override
  public Optional<Comment> findById(Id<Comment, Long> commentId) {
    List<Comment> results = jdbcTemplate.query(
      "SELECT c.*,u.email,u.name FROM comments c JOIN users u ON c.user_seq=u.seq WHERE c.seq=?",
      mapper,
      commentId.value()
    );
    return ofNullable(results.isEmpty() ? null : results.get(0));
  }

  @Override
  public List<Comment> findAll(Id<Post, Long> postId) {
    // TODO comment 목록 조회
    throw new NotImplementedException("구현이 필요합니다.");
  }

  static RowMapper<Comment> mapper = (rs, rowNum) -> new Comment.Builder()
    .seq(rs.getLong("seq"))
    .userId(Id.of(User.class, rs.getLong("user_seq")))
    .postId(Id.of(Post.class, rs.getLong("post_seq")))
    .contents(rs.getString("contents"))
    .writer(new Writer(new Email(rs.getString("email")), rs.getString("name")))
    .createAt(dateTimeOf(rs.getTimestamp("create_at")))
    .build();

}