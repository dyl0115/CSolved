package store.csolved.csolved.domain.answer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.auth.etc.annotation.LoginRequest;
import store.csolved.csolved.auth.etc.annotation.LoginUser;
import store.csolved.csolved.domain.answer.controller.dto.AnswerScoreResponse;
import store.csolved.csolved.domain.answer.entity.Answer;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.user.User;

@RequestMapping("/api/answers")
@RequiredArgsConstructor
@RestController
public class AnswerRestController
{
    private final AnswerService answerService;

    @LoginRequest
    @PostMapping("/{answerId}/score")
    @ResponseStatus(HttpStatus.OK)
    public AnswerScoreResponse saveScore(@LoginUser User user,
                                         @PathVariable Long answerId,
                                         @RequestBody Long score)
    {
        Long prevScore = answerService.getScore(answerId, user.getId());
        if (prevScore != null)
        {
            return AnswerScoreResponse.duplicate(prevScore);
        }

        Answer answer = answerService.saveScore(answerId, user.getId(), score);
        return AnswerScoreResponse.success(answer);
    }

    @LoginRequest
    @PutMapping("/{answerId}/score")
    @ResponseStatus(HttpStatus.OK)
    public AnswerScoreResponse updateScore(@LoginUser User user,
                                           @PathVariable Long answerId,
                                           @RequestBody Long score)
    {
        Answer answer = answerService.updateScore(answerId, user.getId(), score);
        return AnswerScoreResponse.success(answer);
    }

    @LoginRequest
    @DeleteMapping("/{answerId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long answerId)
    {
        answerService.delete(answerId);
    }
}
