package store.csolved.csolved.domain.post.controller.community.dto.view_model;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.answer.entity.Answer;
import store.csolved.csolved.domain.answer.entity.AnswerWithComments;
import store.csolved.csolved.domain.comment.entity.Comment;
import store.csolved.csolved.domain.post.entity.Post;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class CommunityDetailVM
{
    private Post post;
    private List<AnswerWithComments> answers;

    public static CommunityDetailVM from(Post question,
                                         List<Answer> answers,
                                         Map<Long, List<Comment>> comments)
    {
        return CommunityDetailVM.builder()
                .answers(AnswerWithComments.from(answers, comments))
                .post(question)
                .build();
    }
}
