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
    const typeValue = typeInput.value;

    if (nameValue === null || locationValue === null || heightValue === null || typeValue === null){
        return;
    }

    console.log("name: " + nameValue);
    console.log("location: " + locationValue);
    console.log("height: " + heightValue);
    console.log("Architect ID's (hard coded): " + [1, 3]);
    console.log("type: " + typeValue.toUpperCase());

    fetch(`/api/buildings`, {
        method: "POST",
        headers : {
            "Content-Type" : "application/json"
        },
        body: JSON.stringify({
            name : nameValue,
            location : locationValue,
            height : heightValue,
            architectsIDs : [1, 3],
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