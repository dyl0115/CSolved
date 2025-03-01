package store.csolved.csolved.domain.code_review.controller.view_model;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.answer.Answer;
import store.csolved.csolved.domain.answer.AnswerWithComments;
import store.csolved.csolved.domain.comment.Comment;
import store.csolved.csolved.domain.code_review.CodeReview;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class CodeReviewDetailVM
{
    private CodeReview post;
    private boolean bookmarked;
    private List<AnswerWithComments> answers;

    public static CodeReviewDetailVM from(CodeReview codeReview,
                                          boolean bookmarked,
                                          List<Answer> answers,
                                          Map<Long, List<Comment>> comments)
    {
        return CodeReviewDetailVM.builder()
                .bookmarked(bookmarked)
                .answers(AnswerWithComments.from(answers, comments))
                .post(codeReview)
                .build();
    }
}
