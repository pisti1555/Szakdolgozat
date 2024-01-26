function openPopUp(id) {
    let window = document.getElementById(id);
    window.classList.remove("closed");
}

function closePopUp(id) {
    let window = document.getElementById(id);
    window.classList.add("closed");
}


function newGame() {
    fetch('http://localhost:8080/api/game/newGame')
        .then(response => response.json())
        .then(data => {
            console.log('Connections data:', data);
        })
        .catch(error => console.error('Error:', error));
}