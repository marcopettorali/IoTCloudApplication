/*
This files contains the JavaScript code to build scatter plots
 */

function plot(title, values, divID) {
    var trace = {
        y: values,
        mode: 'lines+markers',
        marker: { size: 12 },
    }

    var data = [trace];

    var layout = {
        title: title
    };

    Plotly.newPlot(divID, data, layout);

}

function plotPH_NH3(title, values_1, values_2, divID) {
    var trace_1 = {
        y: values_1,
        mode: 'lines+markers',
        name: 'PH values',
        marker: { size: 12 }
    }

    var trace_2 = {
        y: values_2,
        mode: 'lines+markers',
        name: 'NH4+/NH3 values',
        marker: { size: 12 }
    }

    var data = [trace_1, trace_2];

    var layout = {
        title: title
    };

    Plotly.newPlot(divID, data, layout);
}