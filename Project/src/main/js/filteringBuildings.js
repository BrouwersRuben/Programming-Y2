const locationFilter = document.getElementById("locationFilter");
const locationInput = document.getElementById("location");

const tableBody = document.getElementById("tableBody");

locationFilter.addEventListener("click", locationFilterFunction);

function locationFilterFunction(){
    const location = locationInput.value;

    if (location === "" ) {
        getAllBuildings();
    } else {
        fetch(`/api/buildings/${location}/location`, {
            method: "GET"
        })
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else if (response.status === 204) {
                    alert(`There is no building with location: ${location}`)
                    return [];
                } else {
                    alert(`Received status code: ${response.status}`); // 'alert' is NOT DONE!
                }
            })
            .then(buildings =>
                processData(buildings))
            .catch(error => {
                alert(`Received error: ${error.message}`); // 'alert' is NOT DONE!
            });
    }
}

function processData(dataArray){
    tableBody.innerHTML = "";

    for (let data of dataArray) {
        tableBody.innerHTML += `
            <tr>
                <td>${data.name}</td>
                <td>${data.location}</td>
                <td><a class="btn btn-outline-dark" href="/buildings/buildingdetail?buildingID=${data.id}">Building Detail</a></td>
            </tr>
        `;
    }
}

function getAllBuildings(){
    fetch(`/api/buildings`, {
        method: "GET",
        headers : {
            "Content-Type" : "application/json"
        },
    })
        .then(response => {
            if (response.status === 200){
                return response.json();
            } else if (response.status === 204){
                alert(`There are no buildings in the database`)
                return [];
            } else {
                alert(`Received status code: ${response.status}`); // 'alert' is NOT DONE!
            }
        })
        .then(buildings =>
            processData(buildings))
        .catch(error => {
            alert(`Received error: ${error.message}`); // 'alert' is NOT DONE!
        })
}