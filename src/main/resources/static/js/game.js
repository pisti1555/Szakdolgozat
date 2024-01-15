
var lastSelectedField;
var lastSelectedFieldIndex;
var fieldSelected = false;

var locations;
var connectionMap;

function loadPage() {
    getBoardDataFromServer();
    fetchConnections();
}

function handleOnClick(idIndex) {
    var selectedField = "field" + idIndex;

    if(lastSelectedField == null) {
        lastSelectedFieldIndex = parseInt(idIndex);
        lastSelectedField = "field" + idIndex;
    }

    if(!fieldSelected) {
        selectField(selectedField, idIndex);
    } else {
        moveToField(lastSelectedFieldIndex, idIndex);
    }

    fieldSelected = !fieldSelected;
}

function selectField(selectedField, selectedFieldIndex) {
    lastSelectedFieldIndex = parseInt(selectedFieldIndex);
    lastSelectedField = selectedField;

    document.getElementById(selectedField).style.background = 'grey';
}

function moveToField(from, to) {
    document.getElementById(lastSelectedField).style.background = 'white';

    var moveData = {
       from: parseInt(from),
       to: parseInt(to)
    };

    fetch("http://localhost:8080/api/v1/game/makeMove", {
       method: "POST",
          headers: {
            "Content-Type": "application/json"
           },
          body: JSON.stringify(moveData)
     }).then(response => response.text())
         .then(data => {
             console.log(data);
             getBoardDataFromServer();
          }).catch(error => console.error("Error:", error));
}


function getBoardDataFromServer() {
    fetch("http://localhost:8080/api/v1/game/getPositions", {
       method: "POST",
          headers: {
            "Content-Type": "application/json"
           },
          body: JSON.stringify({})
     }).then(response => response.json())
         .then(data => {
             console.log(data);
             locations = data;
             placePieces(locations);
          }).catch(error => console.error("Error: ", error));
}

function placePieces(pieceLocations) {
    var flyHTML = '<img src="/img/flyPng.png" alt="fly" class="piece"/>';
    var spiderHTML = '<img src="/img/spiderPng.png" alt="spider" class="piece"/>';

    for (var i = 0; i <= 27; i++) {
        document.getElementById("field" + i).innerHTML = "";
      }

    var fieldIDs = [];
    for (var i = 0; i < pieceLocations.length; i++) {
        fieldIDs.push("field" + pieceLocations[i]);
      }

      document.getElementById(fieldIDs[0]).innerHTML = flyHTML;
    for (var i = 1; i < fieldIDs.length; i++) {
        document.getElementById(fieldIDs[i]).innerHTML = spiderHTML;
    }
}


function fetchConnections() {
    fetch('http://localhost:8080/api/v1/game/getConnections')
        .then(response => response.json())
        .then(data => {
            console.log('Connections data:', data);

            // Process the data as needed
            connectionMap = data;
            processConnections();
        })
        .catch(error => console.error('Error:', error));
}

function processConnections() {
    for (const [key, value] of Object.entries(connectionMap)) {
        console.log(`Kulcs: ${key}, Érték: ${value}`);
    }
}