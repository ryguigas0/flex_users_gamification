package guiga.flex_users.flex_users_gamification.campaign;

import io.pebbletemplates.pebble.PebbleEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/campaigns")
public class CampaignBrowserController {
    @Autowired
    private CampaignService service;

    @Autowired
    PebbleEngine engine = new PebbleEngine.Builder().build();

    @GetMapping("/")
    public String listCampaign(Model model) {
        model.addAttribute("campaigns", service.listCampaigns());
        return "campaigns";
    }

}