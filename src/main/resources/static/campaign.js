console.log("IMPORTED campaign.js")

document.querySelectorAll(".select-campaign").forEach((selectBtn) => {
    selectBtn.onclick = function () {

        window.location.href = "/backoffice/campaigns/" + selectBtn.dataset.campaignId
    }
})

document.querySelectorAll(".edit-campaign").forEach((editBtn) => {
    editBtn.onclick = function () {
        setEditCampaignModal("Editing campaign " + editBtn.dataset.campaignId)
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

function setEditCampaignModal(title, prevCampaignData) {
    const modal = new bootstrap.Modal(document.getElementById('editCampaignModal'))

    console.log(modal)

    document.querySelector("#editCampaignModal > div > div > div.modal-header > h5").textContent = title
}