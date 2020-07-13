<%--
  Created by IntelliJ IDEA.
  User: baccios
  Date: 06/07/20
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="com.aquarium.web.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value='<%=(Tank)request.getAttribute("tank")%>' var="tank"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="./assets/logo/logo_small_icon_only_no_background.png">
    <link rel="stylesheet" href="css/navigationBar.css">
    <link rel="stylesheet" href="css/logo.css">
    <script src="scripts/tank.js"></script>
    <title><c:out value="${tank.name}"/></title>
</head>
<body onload="setToggleListeners()">
    <img id="logo" src="./assets/logo/logo.svg" alt="THE AQUARIUM">
    <h2 id="introTitle">Monitoring and control panel</h2>
    <nav id="navigationBar">
        <ul>
            <li><a href="./home">Home</a></li>
            <li><a href="./tanklist">Tanks</a></li>
            <li><a href="./settings">Global settings</a></li>
            <li><a href="./about">About</a></li>
        </ul>
    </nav>

    <header>
        <h3>
            <c:out value="${tank.name}"/>
        </h3>
    </header>

    <p>
        This tank unique identifier is <c:out value="${tank.identifier}"/>. In this page you can see the status
        of all sensors in the tank and you can enable/disable its actuators.
    </p>

    <ul>
        <c:forEach var="sensor" items="${tank.sensors}">
            <li <c:out value="id=${sensor.identifier}"/>>
                <c:set value='${sensor.actuator}' var="tempAct"/>
                <c:choose>
                    <c:when test="${sensor.classDescriptor eq 'OXYGEN'}">
                        <div>
                            <h4>Oxygen sensor</h4>
                            <p>The sensor measures an O2 level of <c:out value="${sensor.currentValue}"/></p>
                            <p>Status: <c:out value="${sensor.status}"/></p>
                            <button type="button">Plot</button>
                        </div>
                        <div <c:out value="id=${tempAct.identifier}"/> >
                            <c:if test="${tempAct == null}">
                                <p>This sensor does not have a linked actuator</p>
                            </c:if>
                            <c:if test="${tempAct != null}">
                                <h4>Oxygenator</h4>
                                <p>Status: <c:out value="${tempAct.statusDescription}"/></p>
                                <label> Toggle:
                                <c:choose>
                                    <c:when test="${tempAct.status eq 0}"> <%-- OFF --%>
                                        <input type="checkbox" class="O2Off">
                                    </c:when>
                                    <c:otherwise> <%-- ON --%>
                                        <input class="O2On" type="checkbox" checked>
                                    </c:otherwise>
                                </c:choose>
                                </label>
                            </c:if>
                        </div>
                    </c:when>

                    <c:when test="${sensor.classDescriptor eq 'TEMPERATURE'}">
                        <div>
                            <h4>Thermometer</h4>
                            <p>Temperature: <c:out value="${sensor.currentValue}"/>°C</p>
                            <p>Status: <c:out value="${sensor.status}"/></p>
                            <button type="button">Plot</button>
                        </div>
                        <div <c:out value="id=${tempAct.identifier}"/>>
                            <c:if test="${tempAct == null}">
                                <p>This sensor does not have a linked actuator</p>
                            </c:if>
                            <c:if test="${tempAct != null}">
                                <h4>Thermoregulator</h4>
                                <p>Status: <c:out value="${tempAct.statusDescription}"/></p>
                                <p>
                                    The desired temperature is set to 22°C by a global parameter.
                                    When active, this device will bring the temperature closer to the desired value.
                                </p>
                                <label> Toggle:
                                    <c:choose>
                                        <c:when test="${tempAct.status eq 0}"> <%-- OFF --%>
                                            <input type="checkbox" class="ThermoOff">
                                        </c:when>
                                        <c:otherwise> <%-- ON --%>
                                            <input class="ThermoOn" type="checkbox" checked>
                                        </c:otherwise>
                                    </c:choose>
                                </label>
                            </c:if>
                        </div>
                    </c:when>

                    <c:when test="${sensor.classDescriptor eq 'LIGHT_INTENSITY'}">
                        <div>
                            <h4>Light intensity</h4>
                            <p>Light strength: <c:out value="${sensor.currentValue}"/> lux</p>
                            <p>Status: <c:out value="${sensor.status}"/></p>
                            <button type="button">Plot</button>
                        </div>
                        <div <c:out value="id=${tempAct.identifier}"/>>
                            <c:if test="${tempAct == null}">
                                <p>This sensor does not have a linked actuator</p>
                            </c:if>
                            <c:if test="${tempAct != null}">
                                <h4>Artificial lights</h4>
                                <p>Status: <c:out value="${tempAct.statusDescription}"/></p>
                                <label> Toggle:
                                    <c:choose>
                                        <c:when test="${tempAct.status eq 0}"> <%-- OFF --%>
                                            <input type="checkbox" class="lightOff">
                                        </c:when>
                                        <c:otherwise> <%-- ON --%>
                                            <input class="lightOn" type="checkbox" checked>
                                        </c:otherwise>
                                    </c:choose>
                                </label>
                            </c:if>
                        </div>
                    </c:when>

                    <c:when test="${sensor.classDescriptor eq 'PH_NH3'}">
                        <div>
                            <h4>Acidity and Ammonium</h4>
                            <p>Ph: <c:out value="${sensor.currentValue}"/></p>
                            <p>NH4+/NH3: <c:out value="${sensor.currentValue2} mg/L"/></p>
                            <p>Status: <c:out value="${sensor.status}"/></p>
                            <button type="button">Plot</button>
                        </div>
                        <div <c:out value="id=${tempAct.identifier}"/>>
                            <c:if test="${tempAct == null}">
                                <p>This sensor does not have a linked actuator</p>
                            </c:if>
                            <c:if test="${tempAct != null}">
                                <h4>Water valve</h4>
                                <p>Status: <c:out value="${tempAct.statusDescription}"/></p>
                                <label> Toggle:
                                    <c:choose>
                                        <c:when test="${tempAct.status eq 0}"> <%-- OFF --%>
                                            <input type="checkbox" class="valveOff">
                                        </c:when>
                                        <c:otherwise> <%-- ON --%>
                                            <input class="valveOn" type="checkbox" checked>
                                        </c:otherwise>
                                    </c:choose>
                                </label>
                            </c:if>
                        </div>
                    </c:when>

                </c:choose>
            </li>
        </c:forEach>
    </ul>


</body>
</html>
