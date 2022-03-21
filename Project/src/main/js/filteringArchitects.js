const numberFilter = document.getElementById("numberFilter");
const nameFilter = document.getElementById("nameFilter");

const numberInput = document.getElementById("numberOfEmployees");
const nameInput = document.getElementById("nameCompany");

const tableBody = document.getElementById("tableBody");

const validator = require('validator');

numberFilter.addEventListener("click", numberFilterFunction);

function nameFilterFunction() {
    const name = nameInput.value;

    if (!validator.isByteLength(name, {min: 0, max: 30})){
        alert("You entered a name longer than 30 characters")
    } else {
        if (name === ""){
            getAllArchitects();
        } else {
            fetch(`/api/architects?name=${name}`, {
                method: "GET",
                headers : {
                    "Content-Type" : "application/json"
                },
            })
                .then(response => {
                    if (response.status === 200){
                        return response.json();
                    } else if (response.status === 204){
                        alert(`There is no architect firm by the name ${name}`)
                        //TODO: I want to stop everything if this happens and not display everything, currently it displays an error message
                        return null;
                    } else {
                        alert(`Received response code: ${response.status}`);
                    }
                })
                .then(architect => {
                    processData(architect)
                })
                .catch(error => {
                    alert(`Received error: ${error.message}`)
                });
        }
    }
}

nameFilter.addEventListener("click", nameFilterFunction);

function numberFilterFunction(){
    const number = numberInput.value;

    if(!validator.isNumeric(number)) {
        alert("You did not enter a number");
    } else {
        if (number === ""){
            getAllArchitects();
        } else {
            fetch(`/api/architects?numbE=${number}`, {
                method: "GET",
                headers : {
                    "Content-Type" : "application/json"
                },
            })
                .then(response => {
                    if (response.status === 200){
                        return response.json();
                    } else if (response.status === 204){
                        alert(`There are no architect firms with more than ${number} employees`)
                        return [];
                    } else {
                        alert(`Received status code: ${response.status}`); // 'alert' is NOT DONE!
                    }
                })
                .then(architects => {
                    processData(architects);
                })
                .catch(error => {
                    alert(`Received error: ${error.message}`); // 'alert' is NOT DONE!
                })
        }
    }
}

function processData(dataArray){
    tableBody.innerHTML="";
    for (let data of dataArray) {
        tableBody.innerHTML += `
            <tr>
                <td>${data.nameCompany}</td>
                <td>${data.establishmentDate}</td>
                <td><a class="btn btn-outline-dark" href="/architects/architectdetail?architectID=${data.id}">Architect Detail</a></td>
            </tr>
        `;
    }
}

function getAllArchitects(){
    fetch(`/api/architects/`, {
        method: "GET",
        headers : {
            "Content-Type" : "application/json"
        }
    })
        .then(response => {
            if (response.status === 200){
                return response.json();
            } else if (response.status === 204){
                alert(`There are no architect firms`)
                return [];
            } else {
                alert(`Received status code: ${response.status}`); // 'alert' is NOT DONE!
            }
        })
        .then(architects => {
            processData(architects);
        })
        .catch(error => {
            alert(`Received error: ${error.message}`); // 'alert' is NOT DONE!
        })
}
