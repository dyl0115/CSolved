package store.csolved.csolved.domain.question.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.auth.annotation.LoginRequest;
import store.csolved.csolved.auth.annotation.LoginUser;
import store.csolved.csolved.domain.answer.dto.AnswerCreateForm;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.category.mapper.CategoryMapper;
import store.csolved.csolved.domain.comment.dto.CommentCreateForm;
import store.csolved.csolved.domain.common.Page;
import store.csolved.csolved.domain.question.QuestionsConstants;
import store.csolved.csolved.domain.question.dto.QuestionCreateForm;
import store.csolved.csolved.domain.question.dto.QuestionDto;
import store.csolved.csolved.domain.question.dto.QuestionEditForm;
import store.csolved.csolved.domain.question.service.QuestionService;
import store.csolved.csolved.domain.user.User;

import static store.csolved.csolved.domain.question.QuestionsConstants.*;

@RequiredArgsConstructor
@Controller
public class QuestionController
{
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final CategoryMapper categoryMapper;

    @LoginRequest
    @GetMapping("/questions")
    public String provideQuestions(@LoginUser User user,
                                   @RequestParam("page") Long requestPage,
                                   Model model)
    {
        Page page = Page.create(requestPage, questionService.provideAllQuestionsCount());

        model.addAttribute("user", user);
        model.addAttribute("questions", questionService.provideQuestions(page));
        model.addAttribute("page", page);

        return "questions/list";
    }

    @LoginRequest
    @GetMapping("/questions/create")
    public String provideQuestionForm(@LoginUser User user,
                                      Model model)
    {
        model.addAttribute("user", user);
        model.addAttribute("questionCreateForm", new QuestionCreateForm());
        model.addAttribute("categories", categoryMapper.findAllCategory());

        return "questions/create";
    }

    @LoginRequest
    @PostMapping("/questions/create")
    public String saveQuestion(@LoginUser User user,
                               @Valid @ModelAttribute("questionCreateForm") QuestionCreateForm createForm,
                               BindingResult result,
                               Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("user", user);
            model.addAttribute("questionCreateForm", createForm);
            model.addAttribute("categories", categoryMapper.findAllCategory());

            return "questions/create";
        }
        else
        {
            questionService.saveQuestion(createForm);

            return REDIRECT_QUESTIONS_MAIN_PAGE_URL;
        }
    }

    @LoginRequest
    @GetMapping("/questions/{questionId}")
    public String provideQuestionDetail(@LoginUser User user,
                                        @PathVariable Long questionId,
                                        Model model)
    {
        questionService.increaseView(questionId);

        model.addAttribute("user", user);
        model.addAttribute("answerCreateForm", new AnswerCreateForm());
        model.addAttribute("commentCreateForm", new CommentCreateForm());
        model.addAttribute("question", questionService.provideQuestion(questionId));
        model.addAttribute("answers", answerService.provideAllAnswersByQuestionId(questionId));

        return "questions/detail";
    }

    @LoginRequest
    @GetMapping("/questions/{questionId}/edit-form")
    public String provideQuestionEditForm(@LoginUser User user,
                                          @PathVariable Long questionId,
                                          Model model)
    {
        QuestionDto question = questionService.provideQuestion(questionId);
        QuestionEditForm questionEditForm = new QuestionEditForm(
                questionId,
                user.getId(),
                question.isAnonymous(),
                question.getCategoryId(),
                question.getTags().toString(),
                question.getTitle(),
                question.getContent());

        model.addAttribute("user", user);
        model.addAttribute("questionEditForm", questionEditForm);
        model.addAttribute("categories", categoryMapper.findAllCategory());

        return "questions/edit";
    }

    @LoginRequest
    @PutMapping("/questions/{questionId}")
    public String updateQuestion(@Valid @ModelAttribute("questionEditForm") QuestionEditForm questionEditForm,
                                 BindingResult result)
    {
        if (result.hasErrors()) return "questions/edit";

        questionService.updateQuestion(questionEditForm);

        return REDIRECT_QUESTIONS_MAIN_PAGE_URL;
    }

    @LoginRequest
    @DeleteMapping("/api/questions/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId)
    {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginRequest
    @PostMapping("/api/questions/{questionId}/likes")
    public ResponseEntity<Void> increaseLikes(@LoginUser User user,
                                              @PathVariable Long questionId)
    {
        if (questionService.hasAlreadyLiked(questionId, user.getId()))
        {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT).build();
        }

        questionService.increaseLike(questionId, user.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
