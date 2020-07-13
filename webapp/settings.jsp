<%--
  Created by IntelliJ IDEA.
  User: baccios
  Date: 09/07/20
  Time: 18:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="icon" href="./assets/logo/logo_small_icon_only_no_background.png">
    <link rel="stylesheet" href="css/navigationBar.css">
    <link rel="stylesheet" href="css/logo.css">
    <title>Global settings</title>
</head>
<body>
    <img id="logo" src="./assets/logo/logo.svg" alt="THE AQUARIUM">
    <h2 id="introTitle">Monitoring and control panel</h2>
    <nav id="navigationBar">
        <ul>
            <li><a href="./home">Home</a></li>
            <li><a href="./tanklist">Tanks</a></li>
            <li><a class="active" href="./settings">Global settings</a></li>
            <li><a href="./about">About</a></li>
        </ul>
    </nav>

    <h3>
        Configuration page
    </h3>

    <section id="parameters">
        <h4>Global parameters setting</h4>
        <ul>
            <li>
                <p>parameter 1: description</p>
                <p>current value: x</p>
                <label>
                    set to a new value: <input type="text" placeholder="new value">
                </label>
            </li>
            <li>
                <p>parameter 2: description</p>
                <p>current value: x</p>
                <label>
                    set to a new value: <input type="text" placeholder="new value">
                </label>
            </li>
            <li>
                <p>parameter 3: description</p>
                <p>current value: x</p>
                <label>
                    set to a new value: <input type="text" placeholder="new value">
                </label>
            </li>
        </ul>
    </section>
</body>
</html>
