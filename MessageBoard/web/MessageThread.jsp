<%-- 
    Document   : MessageThread
    Created on : 29/03/2021, 8:29:20 PM
    Author     : Oscar

    This page is the view of a thread, with parent post and replies if they exist. Allows posting a reply, and a link exists to return to the home of all parent posts.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <link rel="stylesheet" type="text/css" href="css/styles.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/hack-font/3.3.0/web/hack.min.css" integrity="sha512-XgCw4Srl8lC1ECwcaHwAU0WnxQwHkqmInzg9wJLtGB7DRuMaXPuK2k9WJ2AwRDGdrgK9eJpZl2hUlLi2WQssmw==" crossorigin="anonymous" /> <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thread Page</title>
    </head>
    <body>
        <div class="wrapper">
            <c:if test="${not empty requestScope.PostList}">
                <div class="item-wrap">
                    <%-- Access request bean for post list. --%>
                    <a href="http://localhost:8080/MessageBoard/lookup">
                        <h2 id="return-to-index">:) All conversations</h2>
                    </a>
                    <c:forEach var = "i" items="${requestScope.PostList}">
                        <div class="item">
                            <h3>
                                <c:out value = "${i.time}"/> | 
                                Post ID:<c:out value = "${i.postID}"/>
                            </h3>
                            <p>
                                <c:out value = "${i.content}"/>
                            </p>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            <div class="new-post-wrap">
                <%-- Submit a new reply in this thread. --%>
                <form action="PostMessage" method="POST">
                    <h2 class="yep">
                        Join the conversation!(:
                    </h2>
                    <textarea name="content" rows="8" columns="100"></textarea>
                    <button>
                        Submit Post
                    </button>
                </form>
            </div>
        </div>
        <br/>
    </body>
</html>
