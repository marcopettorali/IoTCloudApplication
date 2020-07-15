
function getToggleButtons() {
    var inputs = document.getElementsByTagName("input");
    var res = [];
    for(var i = 0; i<inputs.length; ++i) {
        if(inputs[i].id.startsWith("toggle")) {
            res.push(inputs[i]);
            inputs[i].actuatorID = inputs[i].id.substr(7); //7 = strlen("toggle_")
        }
    }
    return res;
}

function getThresholdButtons() {
    var buttons = document.getElementsByTagName("button");
    var res = [];
    for (var i = 0; i < buttons.length; ++i) {
        if(buttons[i].id.startsWith("th")) {
            res.push(buttons[i]);
            var splittedID = buttons[i].id.split("_");
            buttons[i].threshold = splittedID[1];
            if( splittedID[2] === "ph" || splittedID[2] === "nh3" ) {
                buttons[i].ph_nh3 = splittedID[2];
                buttons[i].actuatorID = splittedID[3] + "_" + splittedID[4] + "_" + splittedID[5] + "_" + splittedID[6];
            }
            else {
                buttons[i].actuatorID = splittedID[2] + "_" + splittedID[3] + "_" + splittedID[4] + "_" + splittedID[5];
            }
        }
    }
    return res;
}

function getPlotButtons() {
    var actuatorContainers = document.getElementsByTagName("div");
    var result = [];
    for(var i = 0; i < actuatorContainers.length; ++i) {
        if(actuatorContainers[i].id === "") {
            var button = actuatorContainers[i].getElementsByTagName("button")[0];
            if(button !== undefined) {
                button.deviceID = actuatorContainers[i].parentElement.getElementsByTagName("div")[1].id;
                result.push(button);
            }
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
    const post_par = "id="+id + "&value="+ value;
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            let resp = JSON.parse(this.responseText);
            let toggle = getToggle(resp.id);
            if(toggle != null) {
                toggle.checked = resp.response === "ON";
            }
        }
    };
    xhttp.open("POST", "./actuator", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(post_par);
}

function handleThreshold(evt) {

    const button = evt.currentTarget;
    const actuatorID = encodeURIComponent(button.actuatorID);

    var value = button.parentElement.getElementsByTagName("input")[0].value;
    value = encodeURIComponent(value);
    const threshold = encodeURIComponent(button.threshold);


    var post_params = "id=" + actuatorID + "&value=" + value + "&threshold=" + threshold;

    if(button.ph_nh3 === "ph" || button.ph_nh3 === "nh3") {
        post_params += ("&metric=" + button.ph_nh3);
    }


    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            let resp = JSON.parse(this.responseText);
            alert("The new threshold has been correctly set!");
        }
    };
    xhttp.open("POST", "./threshold", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(post_params);
}

function handlePlot(evt) {
    var title;
    const id = encodeURIComponent(evt.currentTarget.deviceID);
    var post_par = "id="+id; //+"&value="+value; value is for future improvements

    var splitted_id = id.split("_");
    var metric;
    var type;
    if(splitted_id.length < 4) {
        alert("wrong format id: " + id);
        return;
    }
    type = splitted_id[2] === "a" ? "Actuator" : "Sensor";
    metric = splitted_id[1];

    if(metric !== "ph" && metric !== "nh3") {
        title = type + " for " + metric + ": last 2 hours";
    }
    else {
        title = "Sensors for Ph and NH3: last 2 hours";
    }

    alert("Plot received! post params: " + post_par);

    const xhttp = new XMLHttpRequest();
    xhttp.metric = metric;
    xhttp.divID = id;
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let resp = JSON.parse(this.responseText);

            if (resp.outcome === "good") {
                var values = JSON.parse(resp.values);
                alert(values);
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

    evt.currentTarget.disabled = true;
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

function setThresholdListeners() {
    const buttons = getThresholdButtons();
    for(var i = 0; i<buttons.length; ++i) {
        buttons[i].addEventListener("click", handleThreshold);
    }
}

function setListeners() {
    setToggleListeners();
    setPlotListeners();
    setThresholdListeners();
}


