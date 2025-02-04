package store.csolved.csolved.domain.answer.entity;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Answer
{
    private Long id;
    private Long postId;
    private Long authorId;
    private String authorProfileImage;
    private String authorNickname;
    private boolean anonymous;
    private String content;
    private Long totalScore;
    private Long voterCount;
    private LocalDateTime createdAt;
}
