package com.github.prgrms.social.service.post;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentServiceTest {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired private PostService postService;

  @Autowired private CommentService commentService;

  @BeforeAll
  void setUp() {
  }

  @Test
  @Order(1)
  void 코멘트를_작성한다() {
    // TODO 테스트 추가
  }

  @Test
  @Order(3)
  void 댓글_목록을_조회한다() {
    // TODO 테스트 추가
  }

}