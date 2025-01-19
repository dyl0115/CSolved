package store.csolved.csolved.domain.question.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import store.csolved.csolved.domain.question.Question;
import store.csolved.csolved.domain.question.service.dto.record.QuestionDetailRecord;

import java.util.List;

@Mapper
public interface QuestionMapper
{
    void insert(Question question);

    void update(@Param("questionId") Long QuestionId, @Param("question") Question question);

    //질문글들 개수 조회
    Long countQuestions(String filterType,
                        Long filterValue,
                        String searchType,
                        String searchKeyword);

    // 질문글들 조회
    List<QuestionDetailRecord> getQuestions(Long offset,
                                            Long size,
                                            String sortType,
                                            String filterType,
                                            Long filterValue,
                                            String searchType,
                                            String searchKeyword);

    // 질문글 조회
    QuestionDetailRecord getQuestionDetail(Long questionId);

    // 논리적으로 게시글을 삭제
    void softDelete(Long questionId);

    // 질문-좋아요 테이블에 저장된 유저인지 체크
    boolean hasUserLiked(Long questionId, Long authorId);

    // 질문 테이블의 Likes 1증가
    void incrementLikes(Long questionId);

    // 질문-좋아요 테이블에 questionId, userId 저장 (중복 좋아요 방지)
    void addUserLike(Long questionId, Long authorId);

    // 질문 테이블의 Views 1증가
    void increaseView(Long questionId);

}
