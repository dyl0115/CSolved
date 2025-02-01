package store.csolved.csolved.domain.search.filter;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Filtering
{
    private String filterType;
    private Long filterValue;

    public static Filtering create(String filterType,
                                   Long filterValue)
    {
        return Filtering.builder()
                .filterType(filterType)
                .filterValue(filterValue)
                .build();
    }
}
