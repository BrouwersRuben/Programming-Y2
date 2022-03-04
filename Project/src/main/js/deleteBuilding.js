const deleteButton = document.getElementById("deleteButton");

deleteButton.addEventListener("click", deleteBuilding)

function deleteBuilding(){
    /**
     * @type {HTMLButtonElement}
     */

    const clickedButton = event.target;
    const hiddenInput = clickedButton.previousElementSibling;
    const buildingID = parseInt(hiddenInput.value);

    console.log(buildingID);

    fetch(`/api/buildings/${buildingID}`, {
        method: "DELETE",
        headers : {
            "Content-Type" : "application/json"
        }
    })
        .then(response => {
            if (response.status === 200) {
                window.location.href = '/buildings';
            } else {
                throw new Error(`Unsupported status code: ${response.status}`)
            }
        });
}
