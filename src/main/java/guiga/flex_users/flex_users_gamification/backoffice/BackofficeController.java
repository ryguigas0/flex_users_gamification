package guiga.flex_users.flex_users_gamification.backoffice;

import io.pebbletemplates.pebble.PebbleEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import guiga.flex_users.flex_users_gamification.campaign.CampaignService;
import guiga.flex_users.flex_users_gamification.players.PlayerService;
import guiga.flex_users.flex_users_gamification.players.transfer.PlayerListIn;

@Controller
@RequestMapping("/backoffice")
public class BackofficeController {
    @Autowired
    private CampaignService campaignService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    PebbleEngine engine = new PebbleEngine.Builder().build();

    @GetMapping("/campaigns")
    public String listCampaign(Model model) {
        model.addAttribute("campaigns", campaignService.listCampaigns());
        return "campaigns";
    }

    @GetMapping("/campaigns/{id}")
    public String listPlayers(@PathVariable(name = "id", required = true) Long campaingId, Model model) {
        model.addAttribute("players", playerService.listPlayers(new PlayerListIn(campaingId, null)));

        return "players";
    }

}