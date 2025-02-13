package store.csolved.csolved.common;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Post extends BaseEntity
{
    private String title;
    private boolean anonymous;
    private Long authorId;
    private String authorNickname;
    private String content;
}
