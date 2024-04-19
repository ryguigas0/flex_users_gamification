package guiga.flex_users.flex_users_gamification.campaign;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import guiga.flex_users.flex_users_gamification.campaign.exceptions.InvalidPlayerSchemaType;
import guiga.flex_users.flex_users_gamification.campaign.exceptions.InvalidUpdatePlayerSchemaOperation;
import guiga.flex_users.flex_users_gamification.campaign.transfer.CampaignEditIn;
import guiga.flex_users.flex_users_gamification.campaign.transfer.CampaignIn;
import guiga.flex_users.flex_users_gamification.campaign.transfer.CampaignOut;
import guiga.flex_users.flex_users_gamification.campaign.transfer.CampaignParser;
import guiga.flex_users.flex_users_gamification.players.PlayerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository repo;

    // Circular dependency workaround (https://www.baeldung.com/circular-dependencies-in-spring#3-use-setterfield-injection)
    private PlayerService playerService;

    @Autowired
    public void setPlayerService(@Lazy PlayerService ps) {
        this.playerService = ps;
    }

    public CampaignOut saveCampaign(@Valid CampaignIn campaignIn) {
        return CampaignParser.from(repo.save(CampaignParser.from(campaignIn)));
    }

    public List<CampaignOut> listCampaigns() {
        List<CampaignOut> output = new ArrayList<CampaignOut>();

        repo.findAll().forEach(cm -> output.add(CampaignParser.from(cm)));
        ;

        return output;
    }

    public CampaignOut getCampaign(Long id) {
        return CampaignParser.from(findCampaign(id));
    }

    @Transactional
    public CampaignOut updateCampaign(@NotBlank Long campaignId, @Valid CampaignEditIn campaignIn) {
        CampaignModel campaignModel = findCampaign(campaignId);

        Map<String, String> playerDocumentSchema = campaignModel.getPlayerSchema();
        if (campaignIn.getDeleteAttributes().size() != 0) {
            for (String deleteKey : campaignIn.getDeleteAttributes()) {
                if (!playerDocumentSchema.containsKey(deleteKey)) {
                    throw new InvalidUpdatePlayerSchemaOperation(
                            "'" + deleteKey + "' dos not exists in current schema!");
                }

                playerDocumentSchema.remove(deleteKey);

                playerService.removeKey(deleteKey, campaignModel.getId());
            }
        }

        if (campaignIn.getNewAttributes().size() != 0) {
            for (Map.Entry<String, String> newAttrEntry : campaignIn.getNewAttributes().entrySet()) {
                if (!PlayerSchemaTypes.isAvaliableType(newAttrEntry.getValue())) {
                    throw new InvalidPlayerSchemaType("'" + newAttrEntry.getValue() + "' is not a valid type!");
                }

                playerDocumentSchema.put(newAttrEntry.getKey(), newAttrEntry.getValue());

                playerService.addKey(newAttrEntry.getKey(), newAttrEntry.getValue(), campaignModel.getId());
            }

        }

        if (campaignIn.getName() != null && !campaignIn.getName().isEmpty()) {
            campaignModel.setName(campaignIn.getName());
        }

        repo.save(campaignModel);
        return CampaignParser.from(campaignModel);
    }

    private CampaignModel findCampaign(Long id) {
        Optional<CampaignModel> maybeModel = repo.findById(id);

        if (!maybeModel.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Campaign not found!");
        }

        return maybeModel.get();
    }

    public void setRepo(CampaignRepository repo) {
        this.repo = repo;
    }
}
