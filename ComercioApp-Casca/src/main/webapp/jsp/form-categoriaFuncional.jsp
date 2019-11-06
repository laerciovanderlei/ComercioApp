<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <link rel="stylesheet" href="../css/bootstrap.min.css">   		
        <script src="../js/bootstrap.min.js"></script>     
    </head>
    <body>
        <%@include file="menu.jspf" %>
        <div class="container">
            <form action="/categoriaFuncional" method="post"  role="form" data-toggle="validator" >
                <c:if test ="${empty action}">                        	
                    <c:set var="action" value="add"/>
                </c:if>
                <input type="hidden" id="action" name="action" value="${action}">
                <input type="hidden" id="id" name="id" value="${obj.id}">
                <h2>Categoria Funcional</h2>
                <div class="form-group col-xs-4">
                    <label for="descricao" class="control-label col-xs-4">Descri��o:</label>
                    <input type="text" name="descricao" id="descricao" class="form-control" value="${obj.descricao}" required="true"/>                                   

                    <br></br>
                    <button type="submit" class="btn btn-primary  btn-md">Gravar</button> 
                </div>                                                      
            </form>
        </div>
    </body>
</html>