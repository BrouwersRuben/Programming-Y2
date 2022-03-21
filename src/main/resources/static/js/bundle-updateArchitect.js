/******/ (() => { // webpackBootstrap
var __webpack_exports__ = {};
/*!****************************************!*\
  !*** ./src/main/js/updateArchitect.js ***!
  \****************************************/
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

    const cookie = document.cookie.split('; ').map(entry => entry.split('=')).find(entry => entry[0]==="XSRF-TOKEN")

    fetch(`/api/architects/${entityId}`, {
        method : "PUT",
        headers : {
            "Content-Type": "application/json",
            "X-XSRF-TOKEN" : cookie[1]
        },
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
/******/ })()
;
//# sourceMappingURL=bundle-updateArchitect.js.map