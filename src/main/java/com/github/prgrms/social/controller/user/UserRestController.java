package com.github.prgrms.social.controller.user;

import com.github.prgrms.social.aws.S3Client;
import com.github.prgrms.social.controller.ApiResult;
import com.github.prgrms.social.error.NotFoundException;
import com.github.prgrms.social.model.commons.AttachedFile;
import com.github.prgrms.social.model.user.Email;
import com.github.prgrms.social.model.user.Role;
import com.github.prgrms.social.model.user.User;
import com.github.prgrms.social.security.Jwt;
import com.github.prgrms.social.security.JwtAuthentication;
import com.github.prgrms.social.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.github.prgrms.social.controller.ApiResult.OK;
import static com.github.prgrms.social.model.commons.AttachedFile.toAttachedFile;
import static java.util.Optional.empty;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api")
@Api(tags = "사용자 APIs")
public class UserRestController {

  private final Jwt jwt;

  private final S3Client s3Client;

  private final UserService userService;

  public UserRestController(Jwt jwt, S3Client s3Client, UserService userService) {
    this.jwt = jwt;
    this.s3Client = s3Client;
    this.userService = userService;
  }

  @PostMapping(path = "user/exists")
  @ApiOperation(value = "이메일 중복확인 (API 토큰 필요없음)")
  public ApiResult<Boolean> checkEmail(
    @RequestBody @ApiParam(value = "example: {\"address\": \"test00@gmail.com\"}") Map<String, String> request
  ) {
    Email email = new Email(request.get("address"));
    return OK(
      userService.findByEmail(email).isPresent()
    );
  }

  /**
   * TODO S3Client를 사용해 이미지를 S3에 업로드한다.
   */
  public Optional<String> uploadProfileImage(AttachedFile profileFile) {
    return empty();
  }

  @PostMapping(path = "user/join", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ApiOperation(value = "회원가입 (API 토큰 필요없음)")
  public ApiResult<JoinResult> join(
    @ModelAttribute JoinRequest joinRequest,
    @RequestPart(required = false) MultipartFile file
  ) {
    User user = userService.join(
      joinRequest.getName(),
      new Email(joinRequest.getPrincipal()),
      joinRequest.getCredentials()
    );

    toAttachedFile(file).ifPresent(attachedFile -> {
      // TODO User 이미지 업데이트 처리 (가능하다면 CompletableFuture를 활용해 비동기로 이미지 업로드 처리)
    });

    String apiToken = user.newApiToken(jwt, new String[]{Role.USER.value()});
    return OK(
      new JoinResult(apiToken, new UserDto(user))
    );
  }

  @GetMapping(path = "user/me")
  @ApiOperation(value = "내 정보")
  public ApiResult<UserDto> me(@AuthenticationPrincipal JwtAuthentication authentication) {
    return OK(
      userService.findById(authentication.id)
        .map(UserDto::new)
        .orElseThrow(() -> new NotFoundException(User.class, authentication.id))
    );
  }

  @GetMapping(path = "user/connections")
  @ApiOperation(value = "내 친구 목록")
  public ApiResult<List<ConnectedUserDto>> connections(@AuthenticationPrincipal JwtAuthentication authentication) {
    return OK(
      userService.findAllConnectedUser(authentication.id).stream()
        .map(ConnectedUserDto::new)
        .collect(toList())
    );
  }

}