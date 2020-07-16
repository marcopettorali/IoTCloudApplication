<%--
  Created by IntelliJ IDEA.
  User: baccios
  Date: 06/07/20
  Time: 17:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="java.util.*, com.aquarium.web.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="./assets/logo/logo_small_icon_only_no_background.png">
    <link rel="stylesheet" href="css/navigationBar.css">
    <link rel="stylesheet" href="css/logo.css">
    <title>Aquarium tank list</title>
</head>
<body>
    <img id="logo" src="./assets/logo/logo.svg" alt="THE AQUARIUM">
    <h2 id="introTitle">Monitoring and control panel</h2>
    <nav id="navigationBar">
        <ul>
            <li><a href="./home">Home</a></li>
            <li><a class="active" href="./tanklist">Tanks</a></li>
            <li><a href="./about.html">About</a></li>
        </ul>
    </nav>

    <header>
        <h3>
            Here is the list of all the tanks in the system. Click on one of them to handle all devices
            in that tank.
        </h3>
    </header>
    <ul>
        <c:set value='<%=(ArrayList<Tank>) request.getAttribute("tanks")%>' var="tanks"/>
        <c:forEach var="tank" items="${tanks}">
                <li>
                    <form action="tank" method="get">
                        <input type="hidden" <c:out value='value=${tank.identifier}'/> name="id">
                        <button type="submit">
                            <c:out value="${tank.name}"/>
                        </button>
                    </form>
                </li>
        </c:forEach>
    </ul>

</body>
</html>
