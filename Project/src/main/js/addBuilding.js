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

    for(let i=0; i < checkboxes.length; i++ ){
        if (checkboxes[i].checked === true) { //This does not...
            architectIDs.push(parseInt(checkboxes[i].value));
        }
    }

    const typeValue = typeInput.value;

    if (nameValue === null || locationValue === null || heightValue === null || typeValue === null){
        return;
    }

    const cookie = document.cookie.split(';').map(entry => entry.split('=')).find(entry => entry[0]==="XSRF-TOKEN")

    fetch(`/api/buildings`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-XSRF-TOKEN" : cookie[1]
        },
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