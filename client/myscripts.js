var searchSongURL = "http://localhost:8080/api/searchSong/";
var createPlaylistURL = "http://localhost:8080/api/createPlaylist"
var getAllPlaylistsURL = "http://localhost:8080/api/getAllPlaylists"
function handle_form_submission() {

    // get input
    var searchName = document.getElementById("searchName");

    axios.get(searchSongURL + searchName.value)
        .then(function (response) {

            // reset table
            let ful = document.getElementById("dataTable");
            ful.querySelector("tbody").innerHTML = "";

            console.log(response);

            //create rows
            var table = document.getElementById("dataBody");
            for (var i = 0; i < response.data.length; i++) {
                var row = table.insertRow();
                var cell = row.insertCell();
                var checkbox = document.createElement("input");
                checkbox.type = "checkbox";
                checkbox.name = "rowCheckbox";
                checkbox.value = i;
                checkbox.dataset.data = JSON.stringify(response.data[i]);
                cell.appendChild(checkbox);
                var cell0 = row.insertCell();
                cell0.innerHTML = response.data[i].artistName;
                var cell1 = row.insertCell();
                cell1.innerHTML = response.data[i].id;
                var cell2 = row.insertCell();
                cell2.innerHTML = response.data[i].length;
                var cell3 = row.insertCell();
                cell3.innerHTML = response.data[i].name;

            }
        })
        .catch(function (error) {
            // handle error
            console.log(error);
        })
        .then(function () {
            // always executed
        });
    return false; //do not submit the form

}

function getSelectedRows() {

}

function createPlaylist() {
    var playlistName = document.getElementById("playlistName").value;
    let checkboxes = document.querySelectorAll("input[name='rowCheckbox']:checked");
    let selectedRows = [];

    if(checkboxes.length < 2){
        alert("You should select at least 2 songs.")
    }

    else if(playlistName.length < 5) {
        alert("Playlist name length must be at least 5 characters")
    }

    else {
        checkboxes.forEach(checkbox => {
            selectedRows.push(JSON.parse(checkbox.dataset.data));
        });
    
        let data = {
            name: playlistName,
            songs: selectedRows,
            playlists: null
        }
    
        axios.post(createPlaylistURL, data)
            .then(function (response) {
                console.log(response.data);
            })
            .catch(function (error) {
                console.log(error);
            });
        alert("Susscussfully created playlist " + playlistName);
    }


}

function getAllPlaylists() {
    axios.get(getAllPlaylistsURL)
    .then(function (response) {
        console.log(response);
        // reset table
        let ful = document.getElementById("dataTable");
        ful.querySelector("tbody").innerHTML = "";

        //create rows
        var table = document.getElementById("dataBody");
        for (var i = 0; i < response.data.length; i++) {
            var row = table.insertRow();
            var cell = row.insertCell();
            cell.innerHTML = response.data[i].name;
            var songs = response.data[i].songs;
            var cell2 = row.insertCell();
            cell2.innerHTML = songs.length;
            var cell3 = row.insertCell();
            var length = 0;
            songs.forEach(song => {
                length += song.length;
            });
            var minutes = Math.floor(length / 60);
            var seconds = length - minutes * 60;
            cell3.innerHTML = minutes + ":" + seconds;
        }
    })
    .catch(function (error) {
        // handle error
        console.log(error);
    })
    .then(function () {
        // always executed
    });
return false; //do not submit the form
}
