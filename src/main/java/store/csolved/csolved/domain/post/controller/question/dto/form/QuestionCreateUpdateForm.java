package store.csolved.csolved.domain.post.controller.question.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.post.entity.Post;
import store.csolved.csolved.domain.post.entity.PostType;
import store.csolved.csolved.domain.tag.entity.Tag;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class QuestionCreateUpdateForm
{
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 2, max = 50, message = "제목은 최소 2글자에서 50자까지 가능합니다.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @NotNull
    private Long authorId;

    @NotNull(message = "실명/익명 여부를 선택해주세요.")
    private boolean anonymous;

    @NotNull(message = "카테고리를 하나 선택해주세요.")
    private Long categoryId;

    @NotEmpty(message = "태그는 반드시 하나 이상 있어야 합니다.")
    private List<String> tags;

    public static QuestionCreateUpdateForm empty()
    {
        return QuestionCreateUpdateForm.builder()
                .anonymous(false)
                .tags(new ArrayList<>())
                .build();
    }

    public static QuestionCreateUpdateForm from(Post question)
    {
        return QuestionCreateUpdateForm.builder()
                .title(question.getTitle())
                .content(question.getContent())
                .authorId(question.getAuthorId())
                .anonymous(question.isAnonymous())
                .categoryId(question.getCategoryId())
                .tags(question.getTags().stream()
                        .map(Tag::getName)
                        .toList())
                .build();
    }

    public Post getQuestion()
    {
        return Post.builder()
                .postType(PostType.QUESTION.getCode())
                .title(title)
                .content(content)
                .authorId(authorId)
                .anonymous(anonymous)
                .views(0L)
                .likes(0L)
                .answerCount(0L)
                .categoryId(categoryId)
                .build();
    }

    public List<Tag> getTagList()
    {
        return tags.stream()
                .map(name -> Tag.builder()
                        .name(name)
                        .build())
                .toList();
    }
}
