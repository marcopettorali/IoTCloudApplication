
function getToggleButtons() {
    var actuatorContainers = document.getElementsByTagName("div");
    var result = [];
    for(var i = 0; i < actuatorContainers.length; ++i) {
        if(actuatorContainers[i].id !== "") {
            var toggle = actuatorContainers[i].getElementsByTagName("input")[0];
            toggle.actuatorID = actuatorContainers[i].id;
            result.push(toggle);
        }
    }
    return result;
}

function getToggle(actuatorID) {
    const actuatorDiv = document.getElementById(actuatorID);
    if(actuatorDiv == null)
        return null;
    return actuatorDiv.getElementsByTagName("input")[0];
}

function handleToggle(evt) {
    evt.preventDefault();
    const id = encodeURIComponent(evt.currentTarget.actuatorID);
    const value = evt.currentTarget.checked === true ?  "OFF" : "ON";
    const post_par = "id="+id+"&value="+value;
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            var resp = JSON.parse(this.responseText);

            //TODO: check the response
            var toggle = getToggle(resp.id);
            if(toggle != null) {
                toggle.checked = !toggle.checked;
            }
        }
    };
    xhttp.open("POST", "./actuator", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(post_par);
}

function setToggleListeners() {
    var toggles = getToggleButtons();
    for(var i = 0; i<toggles.length; ++i) {
        toggles[i].addEventListener("click", handleToggle);
    }
}