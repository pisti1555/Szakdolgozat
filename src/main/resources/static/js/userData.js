function handleUserData() {
    
}

function login() {
    var username = document.querySelector('#usernameInput').value;
    var pw = document.querySelector('#passwordInput').value;

    const loginParams = new URLSearchParams();
        loginParams.append('username', username);
        loginParams.append('password', pw);

        fetch("http://localhost:8080/api/player/login", {
            method: "POST",
               headers: {
                 "Content-Type": "application/x-www-form-urlencoded"
                },
               body: loginParams.toString()
          }).then(response => response.text())
              .then(data => {
                 getUser();
               }).catch(error => console.error("Error:", error));
}

function getUser() {
    fetch('http://localhost:8080/api/player/getPlayerData')
        .then(response => response.json())
        .then(data => {
            console.log(data);
            document.getElementById("playerNameLabel").textContent = data.username;
        })
        .catch(error => console.error('Error:', error));
}