package store.csolved.csolved.domain.question.dto;

import lombok.Getter;
import lombok.Setter;
import store.csolved.csolved.domain.category.Category;

import java.util.List;

@Getter
@Setter
public class QuestionCreateDto
{
    private String title;

    private Long userId;

    private String nickname;

    private String content;

    private List<Category> categoryList;
}