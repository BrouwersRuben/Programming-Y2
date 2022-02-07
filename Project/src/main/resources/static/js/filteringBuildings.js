const locationFilter = document.getElementById("locationFilter");
const locationInput = document.getElementById("location");

const tableBody = document.getElementById("tableBody");

locationFilter.addEventListener("click", locationFilterFunction);

function locationFilterFunction(){
    const location = locationInput.value;

    console.log(location);

    if (location === null ) {
        return;
    }

    fetch(`/api/buildings/location?name=${location}`, {
        method: "GET"
    })
        .then(response => {
                if (response.status === 200){
                    return response.json();
                } else if (response.status === 204){
                    processData([]);
                } else {
                    //TODO: Proper error handling
                    alert(`Received status code: ${response.status}`); // 'alert' is NOT DONE!
                }
            })
        .then(buildings =>
            processData(buildings))
        .catch(error => {
            // TODO: proper error handling!
            alert(`Received error: ${error.message}`); // 'alert' is NOT DONE!
        });
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