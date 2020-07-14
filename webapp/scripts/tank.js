
function getToggleButtons() {
    var actuatorContainers = document.getElementsByTagName("div");
    var result = [];
    for(var i = 0; i < actuatorContainers.length; ++i) {
        if(actuatorContainers[i].id !== "") {
            var toggle = actuatorContainers[i].getElementsByTagName("input")[0];
            if (toggle !== undefined) {
                toggle.actuatorID = actuatorContainers[i].id;
                result.push(toggle);
            }
        }
    }
    return result;
}

function getPlotButtons() {
    var actuatorContainers = document.getElementsByTagName("div");
    var result = [];
    for(var i = 0; i < actuatorContainers.length; ++i) {
        if(actuatorContainers[i].id === "") {
            var button = actuatorContainers[i].getElementsByTagName("button")[0];
            if(button !== undefined) {
                button.deviceID = actuatorContainers[i].id;
                if (button.deviceID.includes("ph")) {
                    var nh = getButtonBySubstr("nh3");
                    button.linkedID = nh.deviceID;
                }
                if (button.deviceID.includes("nh3")) {
                    var ph = getButtonBySubstr("ph");
                    button.linkedID = ph.deviceID;
                }
                result.push(button);
            }
        }
    }
    return result;
}

function getButtonBySubstr(s) {
    var actuatorContainers = document.getElementsByTagName("div");
    for(var cont in actuatorContainers) {
        if (cont.id.includes(s)) {
            return actuatorContainers[i].getElementsByTagName("button")[0];
        }
    }
    return null;
}

function getToggle(actuatorID) {
    const actuatorDiv = document.getElementById(actuatorID);
    if(actuatorDiv == null)
        return null;
    return actuatorDiv.getElementsByTagName("input")[0];
}

function handleToggle(evt) {
    evt.preventDefault();
    alert("Toggle received!");
    const id = encodeURIComponent(evt.currentTarget.actuatorID);
    const value = evt.currentTarget.checked === true ?  "OFF" : "ON";
    const post_par = "id="+id + "&value="+ value;
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            let resp = JSON.parse(this.responseText);

            let toggle = getToggle(resp.id);
            if(toggle != null) {
                toggle.checked = resp.response === "1";
            }
        }
    };
    xhttp.open("POST", "./actuator", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(post_par);
}

function handlePlot(evt) {
    var title;
    alert("Plot received!");
    const id = encodeURIComponent(evt.currentTarget.deviceID);
    var post_par = "id="+id; //+"&value="+value; value is for future improvements

    var splitted_id = id.split("_");
    var metric;
    var type;
    if(splitted_id.length < 4) {
        return;
    }
    metric = splitted_id[1] === "a" ? "Actuator" : "Sensor";
    type = splitted_id[2];

    if(metric !== "ph" && metric !== "nh3") {
        title = metric + " for " + type + ": last 2 hours";
    }
    else {
        title = "Sensors for Ph and NH3: last 2 hours";
        post_par += "&id2=" + evt.currentTarget.linkedID;
    }

    const xhttp = new XMLHttpRequest();
    xhttp.metric = metric;
    xhttp.divID = id;
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let resp = JSON.parse(this.responseText);

            if (resp.outcome === "good") {
                var values = resp.values;
                if(this.metric === "ph") {
                    var valuesNH3 = resp.values_linked;
                    plotPH_NH3(title, values, valuesNH3, this.divID);
                }
                else if(this.metric === "nh3") {
                    var valuesPH = resp.values_linked;
                    plotPH_NH3(title, valuesPH, values, this.divID);
                }
                else {
                    plot(title, values, this.divID);
                }
                document.getElementById(this.divID).hidden = false;
            }

        }
    };
    xhttp.open("POST", "./plot", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(post_par);


}


function setToggleListeners() {
    const toggles = getToggleButtons();
    for(var i = 0; i<toggles.length; ++i) {
        toggles[i].addEventListener("click", handleToggle);
    }
}

function setPlotListeners() {
    const buttons = getPlotButtons();
    for(var i = 0; i<buttons.length; ++i) {
        buttons[i].addEventListener("click", handlePlot);
    }
}

function setListeners() {
    setToggleListeners();
    setPlotListeners();
}


