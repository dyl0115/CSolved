package store.csolved.csolved.domain.post.controller.code_review;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.answer.controller.dto.AnswerCreateForm;
import store.csolved.csolved.domain.auth.etc.annotation.LoginRequest;
import store.csolved.csolved.domain.post.controller.code_review.dto.form.CodeReviewCreateUpdateForm;
import store.csolved.csolved.domain.post.controller.code_review.dto.view_model.CodeReviewCreateUpdateVM;
import store.csolved.csolved.domain.post.controller.code_review.dto.view_model.CodeReviewListVM;
import store.csolved.csolved.domain.post.facade.code_review.CodeReviewFacade;
import store.csolved.csolved.domain.search.filter.FilterInfo;
import store.csolved.csolved.domain.search.filter.Filtering;
import store.csolved.csolved.domain.search.page.PageInfo;
import store.csolved.csolved.domain.search.search.SearchInfo;
import store.csolved.csolved.domain.search.search.Searching;
import store.csolved.csolved.domain.search.sort.SortInfo;
import store.csolved.csolved.domain.search.sort.Sorting;

@RequiredArgsConstructor
@Controller
public class CodeReviewController
{
    public final static String VIEWS_CODE_REVIEW_CREATE_FORM = "views/domain/code_review/create";
    public final static String VIEWS_CODE_REVIEW_LIST = "views/domain/code_review/list";
    public final static String VIEWS_CODE_REVIEW_DETAIL = "views/domain/code_review/detail";
    public final static String VIEWS_CODE_REVIEW_UPDATE_FORM = "views/domain/code_review/update";

    private final CodeReviewFacade codeReviewFacade;

    @LoginRequest
    @GetMapping("/code-review/create")
    public String initCreate(Model model)
    {
        CodeReviewCreateUpdateVM viewModel = codeReviewFacade.initCreateUpdate();
        CodeReviewCreateUpdateForm form = codeReviewFacade.initCreate();
        model.addAttribute("createVM", viewModel);
        model.addAttribute("createForm", form);
        return VIEWS_CODE_REVIEW_CREATE_FORM;
    }

    @LoginRequest
    @PostMapping("/code-review/create")
    public String processCreate(@Valid @ModelAttribute("createForm") CodeReviewCreateUpdateForm form,
                                BindingResult result,
                                Model model)
    {
        if (result.hasErrors())
        {
            CodeReviewCreateUpdateVM viewModel = codeReviewFacade.initCreateUpdate();
            model.addAttribute("createVM", viewModel);
            return VIEWS_CODE_REVIEW_CREATE_FORM;
        }

        codeReviewFacade.save(form);
        return "redirect:/code-review?page=1";
    }

    @LoginRequest
    @GetMapping("/code-review")
    public String getCodeReviews(@PageInfo Long page,
                                 @SortInfo Sorting sort,
                                 @FilterInfo Filtering filter,
                                 @SearchInfo Searching search,
                                 Model model)

    {
        CodeReviewListVM viewModel = codeReviewFacade.getCodeReviews(page, sort, filter, search);
        model.addAttribute("codeReviewListViewModel", viewModel);
        return VIEWS_CODE_REVIEW_LIST;
    }

    @LoginRequest
    @GetMapping("/code-review/{postId}")
    public String getCodeReview(@PathVariable Long postId,
                                Model model)
    {
        model.addAttribute("codeReviewDetails", codeReviewFacade.getCodeReview(postId));
        model.addAttribute("answerCreateForm", AnswerCreateForm.empty());
        model.addAttribute("commentCreateForm", AnswerCreateForm.empty());
        return VIEWS_CODE_REVIEW_DETAIL;
    }

    @LoginRequest
    @GetMapping("/code-review/{postId}/update")
    public String initUpdate(@PathVariable Long postId,
                             Model model)
    {
        CodeReviewCreateUpdateVM viewModel = codeReviewFacade.initCreateUpdate();
        model.addAttribute("updateVM", viewModel);
        CodeReviewCreateUpdateForm form = codeReviewFacade.initUpdateForm(postId);
        model.addAttribute("updateForm", form);
        return VIEWS_CODE_REVIEW_UPDATE_FORM;
    }

    @LoginRequest
    @PutMapping("/code-review/{postId}/update")
    public String processUpdate(@PathVariable("postId") Long postId,
                                @Valid @ModelAttribute("updateForm") CodeReviewCreateUpdateForm form,
                                BindingResult result,
                                Model model)
    {
        if (result.hasErrors())
        {
            CodeReviewCreateUpdateVM viewModel = codeReviewFacade.initCreateUpdate();
            model.addAttribute("updateVM", viewModel);
            return VIEWS_CODE_REVIEW_UPDATE_FORM;
        }

        codeReviewFacade.update(postId, form);
        return "redirect:/code-review?page=1";
    }
}
