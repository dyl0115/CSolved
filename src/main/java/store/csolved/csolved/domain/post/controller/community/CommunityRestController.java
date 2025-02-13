package store.csolved.csolved.domain.post.controller.community;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.auth.etc.annotation.LoginRequest;
import store.csolved.csolved.domain.auth.etc.annotation.LoginUser;
import store.csolved.csolved.domain.post.facade.CommunityFacade;
import store.csolved.csolved.domain.user.User;

@RequestMapping("/api/community")
@RequiredArgsConstructor
@RestController
public class CommunityRestController
{
    private final CommunityFacade communityFacade;

    @LoginRequest
    @PostMapping("/{postId}/likes")
    public ResponseEntity<Void> addLike(@LoginUser User user,
                                        @PathVariable Long postId)
    {
        boolean valid = communityFacade.addLike(postId, user.getId());
        if (!valid)
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginRequest
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long postId)
    {
        communityFacade.delete(postId);
    }

}
