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
        method: "DELETE"
    })
        .then(response => {
            if (response.status === 200) {
                window.location.href = '/buildings';
            } else {
                throw new Error(`Unsupported status code: ${response.status}`)
            }
        });
}

/*
/!**
 * @type {Response}
 *!/
const response = await fetch(`/api/buildings/1`, {
    method: "DELETE"
});

if (response === 204) {
    Delete the answer from the UI using JS
} else {
    throw new Error(`Unsupported status code: ${response.status}`)
}
*/
