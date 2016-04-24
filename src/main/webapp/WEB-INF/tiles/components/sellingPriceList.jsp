<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>
    $(document).ready(function() {
        $(".addNewPrice").click(function() {

            var packId = $(".package.success").attr("rel")
            var data = {
                packId : packId
            }
            $.ajax({
                type : "GET",
                url : "addNewPrice",
                data : data,
                success : function(data) {
                    $("#packageInfo").html(data).fadeIn(200)
                },
            });
        })

        var previousPrice;
        $(".price").click(function(event) {
            var elem = $(this)
            var data = {
                priceId : $(this).attr("rel")
            }
            var wasLoaded = $('.priceDetails').attr('cached')

            if(wasLoaded == undefined){
            $.ajax({
                type : "GET",
                url : "priceDetail",
                data : data,
                success : function(data) {
                    $(".priceDetails", elem).html(data)
                    $(".priceDetails",this).attr('cached','1')
                },
                error : function() {
                    console.log("error!")
                },
            })
       		}

            if (previousPrice != null) {
                previousPrice.removeClass("success")
                $(".priceDetails", previousPrice).addClass("hide")
            }
            previousPrice = $(this)
            $(this).addClass("success");
            $(".priceDetails", this).removeClass("hide");

            var data = {
                packId : $(this).attr("rel")
            }
            event.stopPropagation();
            
            endPos = [$(this).attr("posX"), $(this).attr("posY")]
        	console.log(endPos)
        	
            makePath(startPos, endPos)
        })

        $(".editPrice").click(function(event) {
            event.stopPropagation();
            var id = $(this).parents(".price").attr("rel")
            console.log("price id: " + id)
            var formData = {
                priceId : id
            };

            $.ajax({
                type : "GET",
                url : "editPrice",
                data : formData,
                success : function(data) {
                    $("#packageInfo").html(data)
                }
            })
        })
        
        $(".deletePrice").click(function(event){
            var ev = event;
            bootbox.confirm('<spring:message code="price.modaldelete"/>', function(result){
                if (result) {
                  var data = {
                          priceId: $(".deletePrice").parents("tr").attr("rel")
                  }
                  console.log("data: "+$(".deletePrice").parents("tr").attr("rel"))
                  ev.stopPropagation();
                  $.ajax({
                      url:"deletePrice",
                      type:"GET",
                      data: data,
                      success: function(){
                          var data = {
                                  packId: $(".package.success").attr("rel")
                          }
                          $.ajax({
                              type: "GET",
                              url: "getPrices",
                              data: data,
                              success: function(data) {
                                  $("#packageInfo").html(data).fadeIn(200)
  
                                  
                                },
                            });
                      }
                      
                  })
                }
            })
        })
    });
</script>


<h3>
  <spring:message code="price.header"/>
  <sec:authorize access="hasRole('ADMIN')">
  <button type="button" class="btn btn-success btn-xs addNewPrice">
    <span class="glyphicon glyphicon-plus-sign"></span>
  </button>
  </sec:authorize>
</h3>
<table class="table table-hover">
  <thead>
    <tr>
      <td><spring:message code="price.location"/></td>
      <td><spring:message code="price.baseprice"/></td>
    </tr>
  </thead>
  <tbody>
    <c:forEach items="${priceList}" var="element">
      <tr class="price" rel="${element.priceId}" posX="${element.location.mapReference.posX}" posY="${element.location.mapReference.posY}">
        <td>${element.location.locationName}
        </td>
        <td>${element.packPrice}
                  <div class="hide priceDetails" style="min-height:383px"></div>
        </td>
        <td style="width:70px;">
        <sec:authorize access="hasRole('ADMIN')">
          <button class="btn btn-xs btn-success editPrice">
            <span class='glyphicon glyphicon-edit' title='<spring:message code="price.edit"/>'></span>
          </button>
          
          <button class="btn btn-xs btn-danger deletePrice">
            <span class='glyphicon glyphicon-remove-sign' title='<spring:message code="price.delete"/>'></span>
          </button>
        </sec:authorize>
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>

