<%@ page contentType="text/html;charset=UTF-8" import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="./assets/logo/logo_small_icon_only_no_background.png">
    <link rel="stylesheet" href="css/navigationBar.css">
    <link rel="stylesheet" href="css/logo.css">
    <link rel="stylesheet" href="css/global.css">
    <link rel="stylesheet" href="css/home.css">
    <title>Aquarium Control Panel</title>
</head>
<body>
    <img id="logo" src="./assets/logo/logo_white_large.png" alt="THE AQUARIUM">
    <h2 id="introTitle">Monitoring and control panel</h2>
    <nav id="navigationBar">
        <ul>
            <li><a class="active" href="./home">Home</a></li>
            <li><a href="./tanklist">Tanks</a></li>
            <li><a href="./about.html">About</a></li>
        </ul>
    </nav>

    <header>

        <h3>
            Welcome to <img id="titleLogo" src="./assets/logo/logo_white_large.png" alt="THE AQUARIUM"> home page
        </h3>

        <p>
            From here you can have a look to the overall status of the aquarium. Eventual alerts will be visible here
            in <a href="#warnings">Warnings</a> section.
        </p>

    </header>

    <main>
        <section id="overall">
            <h4>
                Overall status
            </h4>
            <p>
                The Aquarium is a smart environment where tanks are constantly monitored to ensure fishes real habitat
                conditions.
            </p>
            <ul>
                <li>
                    In this moment we are monitoring the status of <c:out value="${requestScope['tanks']}"/> tanks.
                    Even tanks' artificial illumination is smart monitored and controlled.
                </li>
                <li>
                    there are <c:out value="${requestScope['oxygenSensors']}"/> active oxygen sensors and
                    <c:out value="${requestScope['oxygenActuators']}"/> oxygenators to guarantee correct oxygen levels in our tanks.
                </li>
                <li>
                    Temperature is constantly monitored as well thanks to <c:out value="${requestScope['tempSensors']}"/>
                    specific thermometers. If it's too hot or too cold, our <c:out value="${requestScope['thermoregulators']}"/>
                    thermo-regulators will correct it.
                </li>
                <li>
                    A wrong ph value could be lethal for our fishes. Moreover, ammonium level must be constantly checked
                    to prevent the formation of poisonous Ammonia. To do so, we have
                    <c:out value="${requestScope['phSensors']}"/> sensors for ammonium and
                    <c:out value="${requestScope['waterValves']}"/> water valves to change part of tanks water in case
                    of urgent need.
                </li>
            </ul>
        </section>
        <section id="warnings">
            <h4>
                Warnings
            </h4>
            <table>
                <thead>
                    <tr>
                        <th>
                            Room
                        </th>
                        <th>
                            Warning message
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${requestScope['warnings'] != null}">
                        <c:set value='<%=(HashMap<String, String>) request.getAttribute("warnings")%>' var="warnings"/>
                        <c:forEach var="entry" items="${warnings}">
                            <tr>
                                <td><c:out value="${entry.key}"/></td>
                                <td><c:out value="${entry.value}"/></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${requestScope['warnings'] == null}">
                        <tr>
                            <td>Good! There are no warnings at the moment.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </section>
    </main>


</body>
</html>