<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<script>
$(document).ready(function(){
    console.log("product list js loaded")
    
    $(".addNewPackage").click(function(){
        var locationId = ${location.locationId};
        var json = {
                locationId: locationId
        }
        console.log("addNewPackage clicked")
        
        $.ajax({
            type: "GET",
            url: "addNewPack",
            data: json,
            success: function(data) {
                $("#packageInfo").html(data).fadeIn(200)
              },
          });
    })
    
    var previousPackage;
    $(".package").click(function(){
        if(previousPackage != null){
            previousPackage.removeClass("success")
            $(".hint",previousPackage).addClass("hide")
        }
        previousPackage = $(this)
        $(this).addClass("success");
        $(".hint",this).removeClass("hide");
        
    })
    
    $(".editPack").click(function(event){
        var trElem = $(this).parents("tr");
        console.log("edit button pushed")
        console.log("edit button >> "+trElem.attr("rel"))
        
        var data = {
            packId: trElem.attr("rel")
        }
        $.ajax({
            type: "GET",
            url: "editPack",
            data: data,
            success: function(data) {
                $("#packageInfo").html(data).fadeIn(200)
              },
          });
    })
    
})
</script>

<h3>Список производимых товаров</h3>
<table class="table table-hover">

    <tbody>
      <c:forEach items="${packageList}" var="element">
        <tr class="package" rel="${element.packId}">
            <td>${element.packName}
                <div class="hint hide">
                  ${element.desc}
                  <div class="pull-right"><button class="btn btn-sm btn-success editPack">edit</button></div>
                </div>

            </td>
        </tr>
      </c:forEach>
    </tbody>

</table>
<button type="button" class="btn btn-success btn-sm addNewPackage"><span class="glyphicon glyphicon-plus"></span>Добавить</button>