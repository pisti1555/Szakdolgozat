
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

            connectionMap = data;
            processConnections();
        })
        .catch(error => console.error('Error:', error));
}

function processConnections() {
    for (const [key, value] of Object.entries(connectionMap)) {
        console.log(`Kulcs: ${key}, Érték: ${value}`);

        for (var i in value) {
            drawConnections(key, value[i]);
        }
    }
}

function drawConnections(from ,to) {
    var canvas = document.getElementById("canvas");
        var ctx = canvas.getContext("2d");

        var field1 = document.getElementById("field" + from);
        var field2 = document.getElementById("field" + to);

        var x1 = field1.offsetLeft;
        var y1 = field1.offsetTop;

        var x2 = field2.offsetLeft;
        var y2 = field2.offsetTop;

        ctx.beginPath();
        ctx.moveTo(x1, y1);
        ctx.lineTo(x2, y2);
        ctx.stroke();
}