package store.csolved.csolved.domain.community.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.answer.controller.form.AnswerCreateForm;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.utils.login.LoginRequest;
import store.csolved.csolved.domain.comment.controller.form.CommentCreateForm;
import store.csolved.csolved.domain.community.controller.form.CommunityCreateUpdateForm;
import store.csolved.csolved.domain.community.controller.view_model.CommunityCreateUpdateVM;
import store.csolved.csolved.domain.community.controller.view_model.CommunityDetailVM;
import store.csolved.csolved.domain.community.controller.view_model.CommunityListVM;
import store.csolved.csolved.domain.community.service.CommunityFacade;
import store.csolved.csolved.utils.filter.FilterInfo;
import store.csolved.csolved.utils.filter.Filtering;
import store.csolved.csolved.utils.login.LoginUser;
import store.csolved.csolved.utils.page.PageInfo;
import store.csolved.csolved.utils.search.SearchInfo;
import store.csolved.csolved.utils.search.Searching;
import store.csolved.csolved.utils.sort.SortInfo;
import store.csolved.csolved.utils.sort.Sorting;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CommunityController
{
    public final static String VIEWS_COMMUNITY_CREATE_FORM = "/views/community/create";
    public final static String VIEWS_COMMUNITY_UPDATE_FORM = "/views/community/update";
    public final static String VIEWS_COMMUNITY_LIST = "/views/community/list";
    public final static String VIEWS_COMMUNITY_DETAIL = "/views/community/detail";

    private final CommunityFacade communityFacade;

    @LoginRequest
    @GetMapping("/communities")
    public String getCommunityPosts(@PageInfo Long page,
                                    @SortInfo Sorting sort,
                                    @FilterInfo Filtering filter,
                                    @SearchInfo Searching search,
                                    Model model)
    {
        CommunityListVM viewModel = communityFacade.getCommunityPosts(page, sort, filter, search);
        model.addAttribute("communityPostListViewModel", viewModel);
        return VIEWS_COMMUNITY_LIST;
    }

    @LoginRequest
    @GetMapping("/community/{postId}")
    public String getCommunityPost(@LoginUser User user,
                                   @PathVariable Long postId,
                                   Model model)
    {
        CommunityDetailVM communityPost = communityFacade.getCommunityPost(user.getId(), postId);
        model.addAttribute("communityPostDetails", communityPost);
        model.addAttribute("answerCreateForm", AnswerCreateForm.empty());
        model.addAttribute("commentCreateForm", CommentCreateForm.empty());
        return VIEWS_COMMUNITY_DETAIL;
    }

    @LoginRequest
    @GetMapping("/community/createForm")
    public String initCreate(Model model)
    {
        CommunityCreateUpdateVM viewModel = communityFacade.initCreate();
        model.addAttribute("createVM", viewModel);
        model.addAttribute("createForm", CommunityCreateUpdateForm.empty());
        return VIEWS_COMMUNITY_CREATE_FORM;
    }

    @LoginRequest
    @PostMapping("/community")
    public String processCreate(@Valid @ModelAttribute("createForm") CommunityCreateUpdateForm form,
                                BindingResult result,
                                Model model)
    {
        if (result.hasErrors())
        {
            CommunityCreateUpdateVM viewModel = communityFacade.initCreate();
            model.addAttribute("createVM", viewModel);
            return VIEWS_COMMUNITY_CREATE_FORM;
        }
        else
        {
            communityFacade.save(form);
            return "redirect:/communities?page=1";
        }
    }

    @LoginRequest
    @GetMapping("/community/{postId}/updateForm")
    public String initUpdate(@PathVariable Long postId,
                             Model model)
    {
        CommunityCreateUpdateVM viewModel = communityFacade.initUpdate();
        model.addAttribute("updateVM", viewModel);
        CommunityCreateUpdateForm form = communityFacade.initUpdateForm(postId);
        model.addAttribute("updateForm", form);
        return VIEWS_COMMUNITY_UPDATE_FORM;
    }

    @LoginRequest
    @PutMapping("/community/{postId}")
    public String processUpdate(@PathVariable("postId") Long postId,
                                @Valid @ModelAttribute("updateForm") CommunityCreateUpdateForm form,
                                BindingResult result,
                                Model model)
    {
        if (result.hasErrors())
        {
            CommunityCreateUpdateVM viewModel = communityFacade.initUpdate();
            model.addAttribute("updateVM", viewModel);
            return VIEWS_COMMUNITY_UPDATE_FORM;
        }

        communityFacade.update(postId, form);
        return "redirect:/communities?page=1";
    }
}
