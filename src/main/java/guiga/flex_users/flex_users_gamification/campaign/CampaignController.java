package guiga.flex_users.flex_users_gamification.campaign;

import org.springframework.web.bind.annotation.RestController;

import guiga.flex_users.flex_users_gamification.campaign.transfer.CampaignIn;
import guiga.flex_users.flex_users_gamification.campaign.transfer.CampaignOut;
import jakarta.validation.Valid;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {
    @Autowired
    private CampaignService service;

    @PostMapping("/")
    public CampaignOut createCampaign(@Valid @RequestBody CampaignIn campaignIn) {
        return service.saveCampaign(campaignIn);
    }

    @GetMapping("/")
    public List<CampaignOut> listCampaigns() {
        return service.listCampaigns();
    }

}