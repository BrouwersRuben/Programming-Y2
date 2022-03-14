const deleteButton = document.getElementById("deleteButton");

deleteButton.addEventListener("click", deleteBuilding)

function deleteBuilding(){
    /**
     * @type {HTMLButtonElement}
     */

    const clickedButton = event.target;
    const hiddenInput = clickedButton.previousElementSibling;
    const buildingID = parseInt(hiddenInput.value);

    document.cookie.split(';').map(entry => entry.split('=')).find(entry => entry[0]==="XSRF-TOKEN")

    const cookie = document.cookie.split('=')

    const headers = {
        "Content-Type": "application/json"
    };
    headers[""] = cookie[1];

    console.log(headers)
    console.log(buildingID);

    fetch(`/api/buildings/${buildingID}`, {
        method: "DELETE",
        headers
    })
        .then(response => {
            if (response.status === 200) {
                window.location.href = '/buildings';
            } else {
                throw new Error(`Unsupported status code: ${response.status}`)
            }
        });
}
