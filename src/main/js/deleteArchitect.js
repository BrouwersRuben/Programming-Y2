const deleteButton = document.getElementById("deleteButton");

deleteButton?.addEventListener("click", deleteArchitect)

function deleteArchitect(){
    /**
     * @type {HTMLButtonElement}
     */

    const clickedButton = event.target;
    const hiddenInput = clickedButton.previousElementSibling;
    const architectID = parseInt(hiddenInput.value);

    const cookie = document.cookie.split('; ').map(entry => entry.split('=')).find(entry => entry[0]==="XSRF-TOKEN")

    fetch(`/api/architects/${architectID}`, {
        method: "DELETE",
        headers : {
            "Content-Type" : "application/json",
            "X-XSRF-TOKEN" : cookie[1]
        }
    })
        .then(response => {
            if (response.status === 200) {
                window.location.href = '/architects';
            } else {
                throw new Error(`Unsupported status code: ${response.status}`)
            }
        });
}