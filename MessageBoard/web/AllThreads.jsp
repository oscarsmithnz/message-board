<%-- 
    Document   : AllThreads
    Created on : 29/03/2021, 8:29:20 PM
    Author     : Oscar
    
    This page is the home page after giving a username. It contains all parent threads, allowing you to click on the id of one to access it, to see it's replies and to reply to it.
    Also allows starting a new thread on this page.
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
                    <h2>:) All conversations</h2>
                    <%-- Access request bean for the list of posts.   --%>
                    <c:forEach var = "i" items="${requestScope.PostList}">
                        <div class="item">
                            <h3>
                                <a id="thread-link" href="http://localhost:8080/MessageBoard/lookup?postid=${i.postID}">
                                    <input type='hidden' name="postid" value = "${i.postID}"/>
                                    <c:out value = "${i.time}"/> |
                                    Post ID:<c:out value = "${i.postID}"/> |
                                </a>
                                <c:out value = "${i.user}"/>
                            </h3>
                            <p>
                                <c:out value = "${i.content}"/>
                            </p>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            <div class="new-post-wrap">
                <%-- For submitting a new post.   --%>
                <form action="PostMessage" method="POST">
                    <h2 class="yep">
                        Start a conversation!(:
                    </h2>
                    <textarea name= "content" rows="8" columns="100"></textarea>
                    <button>
                        Submit Post
                    </button>
                </form>
            </div>
        </div>
        <br/>
    </body>
</html>
