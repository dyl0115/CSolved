package store.csolved.csolved.domain;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/test")
@Controller
public class TestController
{
    @GetMapping
    public String testView(Model model)
    {
        return "common/footer";
    }
}
