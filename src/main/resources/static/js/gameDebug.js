
var lastSelectedField;
var lastSelectedFieldIndex;
var fieldSelected = false;

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
             console.log(data); // Itt megteheted a vÃ¡laszkezelÃ©st
          }).catch(error => console.error("Hiba tÃ¶rtÃ©nt:", error));
}