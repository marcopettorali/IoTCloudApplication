<%--
  Created by IntelliJ IDEA.
  User: baccios
  Date: 06/07/20
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="com.aquarium.web.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% Tank current_t = (Tank)request.getAttribute("tank"); %>
<c:set value='<%=current_t%>' var="tank"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="./assets/logo/logo_small_icon_only_no_background.png">
    <link rel="stylesheet" href="css/navigationBar.css">
    <link rel="stylesheet" href="css/logo.css">
    <script src="scripts/plotly-latest.min.js"></script>
    <script src="scripts/plot.js"></script>
    <script src="scripts/tank.js"></script>
    <title><c:out value="${tank.name}"/></title>
</head>
<body onload="setListeners()">
    <img id="logo" src="./assets/logo/logo.svg" alt="THE AQUARIUM">
    <h2 id="introTitle">Monitoring and control panel</h2>
    <nav id="navigationBar">
        <ul>
            <li><a href="./home">Home</a></li>
            <li><a href="./tanklist">Tanks</a></li>
            <li><a href="./about.html">About</a></li>
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
        <c:set value="${false}" var="ph_nh3_done"/> <!-- used to get together PH and NH3 sensors -->

        <c:forEach var="sensor" items="${tank.sensors}">
            <c:set value="${sensor}" scope="request" var="current_s"/>
            <% Sensor current_s = (Sensor)request.getAttribute("current_s"); %>
            <c:set value='${sensor.actuators}' var="acts"/>
            <c:choose>
                <c:when test="${sensor.classDescriptor.name() eq 'OXYGEN'}">
                    <li <c:out value="id=${sensor.liID}"/>>
                        <c:out value="${sensor.classDescriptor.name()}"/>
                        <div>
                            <h4>Oxygen sensor</h4>
                            <p>
                                The sensor measures an O2 level of
                                <c:out value="${sensor.currentValue}"/>
                            </p>
                            <p>Status: <c:out value="${sensor.status}"/></p>
                            <label>
                                Here you can set resource sampling period in seconds (default 60s)
                                <input type="text" name="sample" placeholder="sampling period (s)">
                                <button <c:out value="id=${sensor.sampleButtonID}"/>>SET</button>
                            </label>
                            <button <c:out value="id=${sensor.plotButtonID}" /> type="button">Plot</button>
                        </div>
                        <div <c:out value="id=${sensor.identifier}"/> hidden>
                        </div>
                        <c:forEach var="tempAct" items="${acts}">
                            <div <c:out value="id=${tempAct.identifier}"/> >
                                <c:if test="${tempAct == null}">
                                    <p>This sensor does not have a linked actuator</p>
                                </c:if>
                                <c:if test="${tempAct != null}">
                                    <h4>Oxygenator</h4>
                                    <p>Status: <c:out value="${tempAct.status}"/></p>
                                    <label> Toggle:
                                    <c:choose>
                                        <c:when test="${tempAct.currentValue eq 0}">
                                            <%-- OFF --%>
                                            <input <c:out value="id=${tempAct.toggleID}"/> type="checkbox" class="O2Off">
                                        </c:when>
                                        <c:otherwise> <%-- ON --%>
                                            <input <c:out value="id=${tempAct.toggleID}"/> class="O2On" type="checkbox" checked>
                                        </c:otherwise>
                                    </c:choose>
                                    </label>
                                    <div <c:out value="id=${tempAct.thresholdID}"/>>
                                        <p>
                                            The oxygenator is automatically enabled when oxygen level is not in an interval
                                            between a low and a high threshold. Here you can set these values to override
                                            default ones.
                                        </p>
                                        <label>
                                            <input type="text" name="l_t" placeholder="low threshold">
                                            <button <c:out value="id=${tempAct.lowThresholdButtonID}"/>>SET</button>
                                        </label>
                                        <label>
                                            <input type="text" name="h_t" placeholder="high threshold">
                                            <button <c:out value="id=${tempAct.highThresholdButtonID}"/>>SET</button>
                                        </label>
                                    </div>
                                </c:if>
                            </div>
                        </c:forEach>
                    </li>
                </c:when>

                <c:when test="${sensor.classDescriptor.name() eq 'TEMPERATURE'}">
                    <li <c:out value="id=${sensor.liID}"/>>
                        <c:out value="${sensor.classDescriptor.name()}"/>
                        <div>
                            <h4>Thermometer</h4>
                            <p>Temperature: <c:out value="${sensor.currentValue}"/>°C</p>
                            <p>Status: <c:out value="${sensor.status}"/></p>
                            <label>
                                Here you can set resource sampling period in seconds (default 60s)
                                <input type="text" name="sample" placeholder="sampling period (s)">
                                <button <c:out value="id=${sensor.sampleButtonID}"/>>SET</button>
                            </label>
                            <button <c:out value="id=${sensor.plotButtonID}" /> type="button">Plot</button>
                        </div>
                        <div <c:out value="id=${sensor.identifier}"/> hidden>
                        </div>
                        <c:forEach var="tempAct" items="${acts}">
                            <div <c:out value="id=${tempAct.identifier}"/>>
                                <c:if test="${tempAct == null}">
                                    <p>This sensor does not have a linked actuator</p>
                                </c:if>
                                <c:if test="${tempAct != null}">
                                    <h4>Thermoregulator</h4>
                                    <p>Status: <c:out value="${tempAct.status}"/></p>
                                    <p>
                                        The desired temperature is set to 22°C by a global parameter.
                                        When active, this device will bring the temperature closer to the desired value.
                                    </p>
                                    <label> Toggle:
                                        <c:choose>
                                            <c:when test="${tempAct.currentValue eq 0}"> <%-- OFF --%>
                                                <input <c:out value="id=${tempAct.toggleID}"/> type="checkbox" class="ThermoOff">
                                            </c:when>
                                            <c:otherwise> <%-- ON --%>
                                                <input <c:out value="id=${tempAct.toggleID}"/> class="ThermoOn" type="checkbox" checked>
                                            </c:otherwise>
                                        </c:choose>
                                    </label>
                                    <div <c:out value="id=${tempAct.thresholdID}"/>>
                                        <p>
                                            Thermo-regulator is automatically enabled when temperature is too hot
                                            or too cold. Set the thresholds if you want to override default values.
                                        </p>
                                        <label>
                                            <input type="text" name="l_t" placeholder="low threshold">
                                            <button <c:out value="id=${tempAct.lowThresholdButtonID}"/>>SET</button>
                                        </label>
                                        <label>
                                            <input type="text" name="h_t" placeholder="high threshold">
                                            <button <c:out value="id=${tempAct.highThresholdButtonID}"/>>SET</button>
                                        </label>
                                    </div>
                                </c:if>
                            </div>
                        </c:forEach>
                    </li>
                </c:when>

                <c:when test="${sensor.classDescriptor.name() eq 'LIGHT_INTENSITY'}">
                    <li <c:out value="id=${sensor.liID}"/>>
                        <c:out value="${sensor.classDescriptor.name()}"/>
                        <div>
                            <h4>Light intensity</h4>
                            <p>Light strength: <c:out value="${sensor.currentValue}"/> lux</p>
                            <p>Status: <c:out value="${sensor.status}"/></p>
                            <label>
                                Here you can set resource sampling period in seconds (default 60s)
                                <input type="text" name="sample" placeholder="sampling period (s)">
                                <button <c:out value="id=${sensor.sampleButtonID}"/>>SET</button>
                            </label>
                            <button <c:out value="id=${sensor.plotButtonID}" /> type="button">Plot</button>
                        </div>
                        <div <c:out value="id=${sensor.identifier}"/> hidden>
                        </div>
                        <c:forEach var="tempAct" items="${acts}">
                            <div <c:out value="id=${tempAct.identifier}"/>>
                                <c:if test="${tempAct == null}">
                                    <p>This sensor does not have a linked actuator</p>
                                </c:if>
                                <c:if test="${tempAct != null}">
                                    <h4>Artificial lights</h4>
                                    <p>Status: <c:out value="${tempAct.status}"/></p>
                                    <label> Toggle:
                                        <c:choose>
                                            <c:when test="${tempAct.currentValue eq 0}"> <%-- OFF --%>
                                                <input <c:out value="id=${tempAct.toggleID}"/> type="checkbox" class="lightOff">
                                            </c:when>
                                            <c:otherwise> <%-- ON --%>
                                                <input <c:out value="id=${tempAct.toggleID}"/> class="lightOn" type="checkbox" checked>
                                            </c:otherwise>
                                        </c:choose>
                                    </label>
                                    <div <c:out value="id=${tempAct.thresholdID}"/>>
                                        <p>
                                            Each light has three levels of intensity: OFF, MEDIUM, MAX. Medium intensity
                                            triggers when tank luminosity is in a certain range. Higher luminosity means
                                            that lights are off, while lower means that lights are at max power. This
                                            range can be set for each independent light set in the tank.
                                        </p>
                                        <label>
                                            <input type="text" name="l_t" placeholder="MEDIUM<->MAX threshold">
                                            <button <c:out value="id=${tempAct.lowThresholdButtonID}"/>>SET</button>
                                        </label>
                                        <label>
                                            <input type="text" name="h_t" placeholder="OFF<->MEDIUM threshold">
                                            <button <c:out value="id=${tempAct.highThresholdButtonID}"/>>SET</button>
                                        </label>
                                    </div>
                                </c:if>
                            </div>
                        </c:forEach>
                    </li>
                </c:when>

                <c:when test="${(sensor.classDescriptor.name() eq 'PH') or (sensor.classDescriptor.name() eq 'NH3')}">
                    <c:if test="${ph_nh3_done eq false}">
                        <li <c:out value="id=${sensor.liID}"/>>
                            <c:out value="PH/NH3"/>
                            <c:set value="<%=current_s.getLinkedSensor(current_t.getSensors())%>" var="linked"/>
                            <div>
                                <c:choose>
                                    <c:when test="${sensor.classDescriptor.name() eq 'PH'}">
                                        <h4>Acidity and Ammonium</h4>
                                        <p>Ph: <c:out value="${sensor.currentValue}"/></p>
                                        <p>NH4+/NH3: <c:out value="${linked.currentValue} mg/L"/></p>
                                        <p>Ph sensor status: <c:out value="${sensor.status}"/></p>
                                        <p>NH4+/NH3 sensor status: <c:out value="${linked.status}"/></p>
                                        <label>
                                            Here you can set PH and NH3 sampling period in seconds (default 60s)
                                            <input type="text" name="sample" placeholder="sampling period (s)">
                                            <button <c:out value="id=${sensor.sampleButtonID}"/>>SET</button>
                                        </label>
                                        <button <c:out value="id=${sensor.plotButtonID}" /> type="button">Plot</button>
                                    </c:when>
                                    <c:otherwise>
                                        <h4>Acidity and Ammonium</h4>
                                        <p>Ph: <c:out value="${linked.currentValue}"/></p>
                                        <p>NH4+/NH3: <c:out value="${sensor.currentValue} mg/L"/></p>
                                        <p>Ph sensor status: <c:out value="${linked.status}"/></p>
                                        <p>NH4+/NH3 sensor status: <c:out value="${sensor.status}"/></p>
                                        <label>
                                            Here you can set PH and NH3 sampling period in seconds (default 60s)
                                            <input type="text" name="sample" placeholder="sampling period (s)">
                                            <button <c:out value="id=${sensor.sampleButtonID}"/>>SET</button>
                                        </label>
                                        <button <c:out value="id=${sensor.plotButtonID}" /> type="button">Plot</button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div <c:out value="id=${sensor.identifier}"/> hidden>
                            </div>
                            <c:forEach var="tempAct" items="${acts}">
                                <div <c:out value="id=${tempAct.identifier}"/>>
                                    <c:if test="${tempAct == null}">
                                        <p>This sensor does not have a linked actuator</p>
                                    </c:if>
                                    <c:if test="${tempAct != null}">
                                        <h4>Water valve</h4>
                                        <p>Status: <c:out value="${tempAct.status}"/></p>
                                        <label> Toggle:
                                            <c:choose>
                                                <c:when test="${tempAct.currentValue eq 0}"> <%-- OFF --%>
                                                    <input <c:out value="id=${tempAct.toggleID}"/> type="checkbox" class="valveOff">
                                                </c:when>
                                                <c:otherwise> <%-- ON --%>
                                                    <input <c:out value="id=${tempAct.toggleID}"/> class="valveOn" type="checkbox" checked>
                                                </c:otherwise>
                                            </c:choose>
                                        </label>
                                        <div <c:out value="id=${tempAct.thresholdID}"/>>
                                            <p>
                                                Ammonium level is dangerous when Ph level is high, because in this case
                                                it turns into poisonous ammonia. Here you can set the range of acceptable
                                                levels for both Ph and Nh3/NH4+. Water valves will change part of the
                                                water when levels become unacceptable.
                                            </p>
                                            <label>
                                                Ph low threshold:
                                                <input type="text" name="l_t" placeholder="between 0 and 14">
                                                <button <c:out value="id=${tempAct.lowThresholdPHButtonID}"/>>SET</button>
                                            </label>
                                            <label>
                                                Ph high threshold:
                                                <input type="text" name="h_t" placeholder="between 0 and 14">
                                                <button <c:out value="id=${tempAct.highThresholdPHButtonID}"/>>SET</button>
                                            </label>
                                            <label>
                                                NH3 low threshold:
                                                <input type="text" name="l_t" placeholder="(mg/L)">
                                                <button <c:out value="id=${tempAct.lowThresholdNH3ButtonID}"/>>SET</button>
                                            </label>
                                            <label>
                                                NH3 high threshold:
                                                <input type="text" name="h_t" placeholder="(mg/L))">
                                                <button <c:out value="id=${tempAct.highThresholdNH3ButtonID}"/>>SET</button>
                                            </label>
                                        </div>
                                    </c:if>
                                </div>
                            </c:forEach>
                        </li>
                    </c:if>
                    <c:set value="${true}" var="ph_nh3_done"/>
                </c:when>
            </c:choose>
        </c:forEach>
    </ul>

</body>
</html>
