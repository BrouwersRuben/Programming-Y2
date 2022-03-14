const updateButton = document.getElementById("updateButton");
const updateValue = document.getElementById("updateValue");
const entityID = document.getElementById("id")
const displayValue = document.getElementById("numbE");

updateButton.addEventListener("click", updateNumberOfEmployees)

function updateNumberOfEmployees(){
    const value = updateValue.value;
    const entityId = entityID.value;

    if (value === null){
        return;
    }

    const csrfToken = document.querySelector("meta[name=_csrf]").content;
    const csrfHeader = document.querySelector("meta[name=_csrf_header]").content;

    const headers = {
        "Content-Type": "application/json"
    };
    headers[csrfHeader] = csrfToken;

    console.log(headers)

    fetch(`/api/architects/${entityId}`, {
        method : "PUT",
        headers,
        body : JSON.stringify({
            id : entityId,
            numberOfEmployees : value
        }),
        redirect: "manual"
    })
        .then(response => {
            if (response.status === 204 || response.status === 200){
                displayValue.innerHTML=value;
                updateValue.value="";
            } else {
                alert(`Received status code: ${response.status}`);
            }
        })
        .catch(error => {
            alert(error.message)
        });
}