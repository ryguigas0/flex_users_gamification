package guiga.flex_users.flex_users_gamification.campaign;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import guiga.flex_users.flex_users_gamification.campaign.transfer.CampaignIn;
import guiga.flex_users.flex_users_gamification.campaign.transfer.CampaignOut;
import guiga.flex_users.flex_users_gamification.campaign.transfer.CampaignParser;
import jakarta.validation.Valid;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository repo;

    public CampaignOut saveCampaign(@Valid CampaignIn campaignIn) {
        return CampaignParser.from(repo.save(CampaignParser.from(campaignIn)));
    }

    public List<CampaignOut> listCampaigns() {
        List<CampaignOut> output = new ArrayList<CampaignOut>();

        repo.findAll().forEach(cm -> output.add(CampaignParser.from(cm)));
        ;

        return output;
    }

    public CampaignOut getCampaign(Long id){
        Optional<CampaignModel> maybeModel = repo.findById(id);

        if (!maybeModel.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Campaign not found!");
        }

        return CampaignParser.from(maybeModel.get());
    }
}
