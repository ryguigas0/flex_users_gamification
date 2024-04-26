console.log("IMPORTED campaign.js")

document.querySelectorAll(".select-campaign").forEach((selectBtn) => {
    selectBtn.onclick = function () {

        window.location.href = "/backoffice/campaigns/" + selectBtn.dataset.campaignId
    }
})

document.querySelectorAll(".edit-campaign").forEach((editBtn) => {
    editBtn.onclick = function () {
        let campaign = {
            name: editBtn.dataset.campaignName,
            schema: editBtn.dataset.campaignSchema,
        }

        setEditCampaignModal("Editing campaign " + editBtn.dataset.campaignId, campaign)
    }
})

document.querySelector(".add-campaign-btn").onclick = function () {
    setEditCampaignModal("Create Campaign")
}

document.querySelectorAll(".delete-campaign").forEach((rmBtn) => {
    rmBtn.onclick = function () {
        console.log("DELETING", rmBtn.dataset.campaignId)
    }
})

import { readCampaign } from "./campaign_form.js";

function setEditCampaignModal(title, prevCampaignData) {
    let modalTitle = document.querySelector("#editCampaignModalTitle")

    modalTitle.textContent = title

    readCampaign(prevCampaignData.schema)
}