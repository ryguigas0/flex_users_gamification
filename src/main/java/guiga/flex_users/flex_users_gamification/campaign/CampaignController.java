package guiga.flex_users.flex_users_gamification.campaign;

import org.springframework.web.bind.annotation.RestController;

import guiga.flex_users.flex_users_gamification.campaign.transfer.CampaignEditIn;
import guiga.flex_users.flex_users_gamification.campaign.transfer.CampaignIn;
import guiga.flex_users.flex_users_gamification.campaign.transfer.CampaignOut;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {
    @Autowired
    private CampaignService service;

    @PostMapping("/")
    public ResponseEntity<CampaignOut> createCampaign(@Valid @RequestBody CampaignIn campaignIn) {
        return new ResponseEntity<CampaignOut>(service.saveCampaign(campaignIn), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<CampaignOut>> listCampaigns() {
        return new ResponseEntity<List<CampaignOut>>(service.listCampaigns(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampaignOut> updateCampaign(
            @Valid @RequestBody CampaignEditIn campaignIn,
            @PathVariable(name = "id") @NotNull Long campaignId) {
        return new ResponseEntity<CampaignOut>(
                service.updateCampaign(campaignId, campaignIn),
                HttpStatus.CREATED);
    }

}