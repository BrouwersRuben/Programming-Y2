const deleteButton = document.getElementById("deleteButton");

deleteButton.addEventListener("click", deleteArchitect)

function deleteArchitect(){
    /**
     * @type {HTMLButtonElement}
     */

    const clickedButton = event.target;
    const hiddenInput = clickedButton.previousElementSibling;
    const architectID = parseInt(hiddenInput.value);

    console.log(architectID);

    fetch(`/api/architects/${architectID}`, {
        method: "DELETE"
    })
        .then(response => {
            if (response.status === 200) {
                window.location.href = '/architects';
            } else {
                throw new Error(`Unsupported status code: ${response.status}`)
            }
        });
}