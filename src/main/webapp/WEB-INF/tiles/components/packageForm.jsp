<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
$(document).ready(function(){
    $("#addNewPack").submit(function(event){
        var formData = $(this).serialize();
        var action = "${formAction}";
        var url = (action == "create") ? "addNewPack" : "editPack";
        console.log("action: "+url)
        $.ajax({
            type: "POST",
            url: url,
            data: formData,
            success: function() {      
                 var json = {
                    locationId: locationId
                }
                $.ajax({
                    type: "GET",
                    url: "getPackList",
                    data: json,
                    success: function(data) {
                        console.log("success")
                        packageList.css("min-height",packageList.css("height"))
                        packageList.fadeOut(50).html(data).fadeIn(200)
                        $("#packageInfo").fadeOut(50).html("")
                      },
                  });
                
    			
            },
            error: function(xhr, status, error) {
            	console.log("error: "+xhr.responseText);
            }
          });
        event.preventDefault();        
        
    })
    
})
</script>

<c:if test="${formAction eq 'create'}">
<h3>Добавление нового товара</h3>
</c:if>

<c:if test="${formAction eq 'edit'}">
<h3>Редактирование товара</h3>
</c:if>

<form:form commandName="tradingPackDto" id="addNewPack">
   <input type="hidden" name="locationId" value="${locationId}"/>
   <form:input type="hidden" path="packId"/>
   <div class="input-group">
      <span class="input-group-addon">Название товара: </span>
      <form:input path="title" type="text" length="100" name="name" class="form-control"/> 
   </div>
   <div class="form-group">
      <form:textarea path="desc" class="form-control" rows="3" placeHolder="Описание"/>
   </div>
   <input type="submit" class="btn btn-success btn-sm" value="Подтвердить"/>
</form:form>