package com.efun.web;

import com.efun.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * Created by kinven on 16-11-3.
 */
@Controller
public class IndexController {

    @Autowired
    AdService adService;

    @RequestMapping(value={"","/","index"})
    public String index(Model model) throws IOException {
        Map<String,Long> gameCount = adService.getTotal();
        model.addAttribute("total_clicked",gameCount.get("total_clicked"));
        model.addAttribute("total_installed",gameCount.get("total_installed"));
        model.addAttribute("total_register",gameCount.get("total_register"));
        model.addAttribute("total_user",gameCount.get("total_user"));
        return "starter";
    }
}
