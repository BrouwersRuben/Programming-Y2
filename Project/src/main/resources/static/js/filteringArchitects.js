const numberFilter = document.getElementById("numberFilter");

const numberInput = document.getElementById("numberOfEmployees");

const tableBody = document.getElementById("tableBody");

numberFilter.addEventListener("click", numberFilterFunction);

//TODO: When entered nothing, the form gives this as output "", this is not int and gives error

function numberFilterFunction(){
    const number = numberInput.value;

    if (number === null){
        return;
    }

    fetch(`/api/architects/numberEmployees?amount=${number}`, {
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
        .then(architects => {
            processData(architects);
        })
        .catch(error => {
            // TODO: proper error handling!
            alert(`Received error: ${error.message}`); // 'alert' is NOT DONE!
        })
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

