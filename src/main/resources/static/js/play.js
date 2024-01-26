
var lastSelectedField;
var lastSelectedFieldIndex;
var fieldSelected = false;

var locations;
var connectionMap;


function createField(className, id, onclick) {
    var field = document.createElement("div");
    field.className = className;
    field.id = id;
    field.onclick = onclick;
    return field;
}

function createBoard(gameMode) {
    var gameboard = document.getElementById("gameboard");

    if (gameMode == 'pvp') {
        for (var i = 0; i < 28; i++) {
            var className = "subfield";
            if (i == 5 || i == 10 || i == 14 || i == 18 || i == 22 || i == 27) {
                className = "field";
            }
            var id = "field" + i;
            var field = createField(className, id, function() { handleOnClick(this.id, 'pvp'); });
            gameboard.appendChild(field);
        }
    }
    if (gameMode == 'pvf') {
        for (var i = 0; i < 28; i++) {
            var className = "subfield";
            if (i == 5 || i == 10 || i == 14 || i == 18 || i == 22 || i == 27) {
                className = "field";
            }
            var id = "field" + i;
            var field = createField(className, id, function() { handleOnClick(this.id, 'pvf'); });
            gameboard.appendChild(field);
        }
    }
    if (gameMode == 'pvs') {
        for (var i = 0; i < 28; i++) {
            var className = "subfield";
            if (i == 5 || i == 10 || i == 14 || i == 18 || i == 22 || i == 27) {
                className = "field";
            }
            var id = "field" + i;
            var field = createField(className, id, function() { handleOnClick(this.id, 'pvs'); });
            gameboard.appendChild(field);
        }
    }
    else {
        console.log("Error while creating board");
    }
}


function loadPage() {
    getBoardDataFromServer();
    fetchConnections();
}

function handleOnClick(selectedField, gameMode) {
    var idIndex = parseInt(selectedField.match(/\d+/)[0]);

    if(lastSelectedField == null) {
        lastSelectedFieldIndex = parseInt(idIndex);
        lastSelectedField = "field" + idIndex;
    }

    if(!fieldSelected) {
        selectField(selectedField, idIndex);
    } else {
        moveToField(lastSelectedFieldIndex, idIndex, gameMode);
    }

    fieldSelected = !fieldSelected;
}

function selectField(selectedField, selectedFieldIndex) {
    lastSelectedFieldIndex = parseInt(selectedFieldIndex);
    lastSelectedField = selectedField;

    document.getElementById(selectedField).style.background = 'rgb(0, 0, 65)';
}

function moveToField(from, to, gameMode) {

    document.getElementById(lastSelectedField).style.background = 'rgb(0, 4, 120)';

    var moveData = {
       from: parseInt(from),
       to: parseInt(to)
    };


    if (gameMode == 'pvp') {
        fetch("http://localhost:8080/api/game/playVsPlayer", {
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

    if (gameMode == 'pvs') {
        fetch("http://localhost:8080/api/game/playVsSpider", {
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

    if (gameMode == 'pvf') {
        fetch("http://localhost:8080/api/game/playVsFly", {
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
    else {
        console.log("Error while moving piece");
    }
}


function getBoardDataFromServer() {
    fetch("http://localhost:8080/api/game/getPositions", {
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
    var flyHTML = '<img src="/img/fly.png" alt="fly" class="piece"/>';
    var spiderHTML = '<img src="/img/spider.png" alt="spider" class="piece"/>';

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
    fetch('http://localhost:8080/api/game/getConnections')
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