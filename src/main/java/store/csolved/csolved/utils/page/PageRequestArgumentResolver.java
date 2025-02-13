package store.csolved.csolved.utils.page;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class PageRequestArgumentResolver implements HandlerMethodArgumentResolver
{
    private static final String PAGE_PARAMETER_NAME = "page";

    @Override
    public boolean supportsParameter(MethodParameter parameter)
    {
        boolean hasPageInfoAnnotation = parameter.hasParameterAnnotation(PageInfo.class);
        boolean isLongType = parameter.getParameterType().equals(Long.class);
        return hasPageInfoAnnotation && isLongType;
    }

    @Override
    public Long resolveArgument(MethodParameter parameter,
                                ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory)
    {
        String pageString = webRequest.getParameter(PAGE_PARAMETER_NAME);
        return validateAndCreatePage(pageString);
    }

    private Long validateAndCreatePage(String pageString)
    {
        if (!pageString.matches("^[1-9]\\d*$")) return 1L;
        return Long.parseLong(pageString);
    }
}
