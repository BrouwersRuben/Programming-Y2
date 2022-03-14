const nameInput = document.getElementById("name");
const locationInput = document.getElementById("location");
const heightInput = document.getElementById("height");
const architectInput = document.getElementById("architectIDs");
const typeInput = document.getElementById("type");
const button = document.getElementById("submitButton");

button.addEventListener("click", addBuilding);

function addBuilding(){
    const nameValue = nameInput.value;
    const locationValue = locationInput.value;
    const heightValue = heightInput.value;
    let architectIDs = [];

    const checkboxes = document.querySelectorAll('input[type=checkbox]');

    const csrfToken = document.querySelector("meta[name=_csrf]").content;
    const csrfHeader = document.querySelector("meta[name=_csrf_header]").content;

    const headers = {
        "Content-Type": "application/json"
    };
    headers[csrfHeader] = csrfToken;

    console.log(headers)

    for(let i=0; i < checkboxes.length; i++ ){
        if (checkboxes[i].checked === true) { //This does not...
            architectIDs.push(parseInt(checkboxes[i].value));
        }
    }

    const typeValue = typeInput.value;

    if (nameValue === null || locationValue === null || heightValue === null || typeValue === null){
        return;
    }

    fetch(`/api/buildings`, {
        method: "POST",
        headers,
        body: JSON.stringify({
            name : nameValue,
            location : locationValue,
            height : heightValue,
            architectsIDs : architectIDs,
            type : typeValue.toUpperCase()
        })
    })
        .then(response => {
            if (response.status === 201) {
                nameValue.value="";
                locationValue.value="";
                heightValue.value="";
                typeValue.value="";
                window.location.href = '/buildings';
            } else {
                //TODO: Error handling
                alert(`Received status code: ${response.status}`);
            }
        })
        .catch(error => alert(error.message));
}