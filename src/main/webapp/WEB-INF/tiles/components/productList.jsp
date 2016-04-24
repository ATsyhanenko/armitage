<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script>
$(document).ready(function(){
    
    $(".addNewPackage").click(function(){
        var locationId = ${location.locationId};
        var data = {
                locationId: locationId
        }
        
        $.ajax({
            type: "GET",
            url: "addNewPack",
            data: data,
            success: function(data) {
                $("#packageInfo").html(data).fadeIn(200)
              },
          });
    })
    
    var previousPackage;
    $(".package").click(function(event){
        if(previousPackage != null){
            previousPackage.removeClass("success")
            $(".hint",previousPackage).addClass("hide")
        }
        previousPackage = $(this)
        $(this).addClass("success");
        $(".hint",this).removeClass("hide");
        
        var data = {
                packId: $(this).attr("rel")
        }
        event.stopPropagation();
        $.ajax({
            type: "GET",
            url: "getPrices",
            data: data,
            success: function(data) {
                $("#packageInfo").html(data).fadeIn(200)

                
              },
          });
        
    })
    
    $(".editPack").click(function(event){
        var trElem = $(this).parents("tr");

        var data = {
            packId: trElem.attr("rel")
        }
        event.stopPropagation();
        $.ajax({
            type: "GET",
            url: "editPack",
            data: data,
            success: function(data,event) {
                $("#packageInfo").html(data).fadeIn(200)
              },
          });
       
       
    })
    
    $(".deletePack").click(function(event){
            var ev = event;
            bootbox.confirm('<spring:message code="pack.modaldelete"/>', function(result){
                if (result) {
                  var data = {
                          packId: $(".deletePack").parents("tr").attr("rel")
                  }
                  console.log("data: "+$(".deletePack").parents("tr").attr("rel"))
                  ev.stopPropagation();
                  $.ajax({
                      url:"deletePack",
                      type:"GET",
                      data: data,
                      success: function(){
                          $("#packageInfo").fadeOut(50).html("")
                          var data = {
                             locationId: $(".location.success").attr("rel")
                          }
                          $.ajax({
                              type: "GET",
                              url: "getPackList",
                              data: data,
                              success: function(data) {
                                  $("#productTable").html(data).fadeIn(200)
  
                                  
                                },
                            });
                      }
                      
                  })
                }
            })
    })
    
})
</script>

<h3><spring:message code="pack.header"/>
  <sec:authorize access="hasRole('ADMIN')">
    <button type="button" class="btn btn-success btn-xs addNewPackage"><span class="glyphicon glyphicon-plus-sign"></span></button>
  </sec:authorize>
</h3>
<table class="table table-hover">

    <tbody>
      <c:forEach items="${packageList}" var="element">
        <tr class="package" rel="${element.packId}">
            <td>${element.packName}
                <div class="hint hide">
                  ${element.desc}
                    <sec:authorize access="hasRole('ADMIN')">
                               <div class="pull-right">
                                  <button class="btn btn-sm btn-success editPack"><span class='glyphicon glyphicon-edit' title='<spring:message code="pack.edit"/>'></span></button>
                                  
                                  <button class="btn btn-sm btn-danger deletePack"><span class='glyphicon glyphicon-remove-sign' title='<spring:message code="pack.delete"/>'></span></button>
                                </div>
        
                      
                    </sec:authorize>
                </div>

            </td>
        </tr>
      </c:forEach>
    </tbody>

</table>