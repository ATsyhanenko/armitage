<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script>
    $(document).ready(function() {
        console.log("price form")
        var priceForm = $("#priceForm")
        var action = "${formAction}";
        var url = (action == "create") ? "addNewPrice" : "editPrice";
        
        priceForm.submit(function(event) {
            console.log("price form submit")
            var formData = $(this).serialize();
            console.log(">> " + formData)
            event.preventDefault();
            $.ajax({
                type : "POST",
                url : url,
                data : formData,
                success : function(data) {
                    //$("#packageInfo").html("OK").fadeIn(200)
                    
                    var data = {
                            packId: $(".package.success").attr("rel")
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
                },
            });
        })

       
    })
</script>
<c:if test='${formAction eq "create"}'>
<h3>Добавление новой точки сбыта</h3>
</c:if>
<c:if test='${formAction eq "edit"}'>
<h3>Редактирование точки сбыта</h3>
</c:if>
<form:form commandName="sellingPrice" id="priceForm"
  class="form-horizontal">
  <div class="form-group">
    <label for="packPrice" class="col-sm-2 control-label">Цена</label>
    <div class="col-sm-10">
      <form:input path="price" type="number" step="0.01"
        class="form-control" min="0.00" />
    </div>
  </div>

  <div class="form-group">
    <label for="priceLock" class="col-sm-2 control-label">Локация</label>
    <div class="col-sm-10">
      <select name="location" class="form-control" id="priceLock">
        <c:forEach items="${merchantList}" var="element">
          <c:choose>
            <c:when
              test="${element.locationId eq sellingPrice.location}">
              <option selected value="${element.locationId}">${element.locationName}</option>
            </c:when>
            <c:otherwise>
              <option value="${element.locationId}">${element.locationName}</option>
            </c:otherwise>
          </c:choose>
        </c:forEach>
      </select>
    </div>
  </div>

  <div class="pull-right">
    <form:input type="hidden" path="pack" />
    <form:input type="hidden" path="priceId" />
    <c:if test='${formAction eq "create"}'>
    <input type="submit" class="btn btn-success editPack"
      value="Добавить" />
    </c:if>
    <c:if test='${formAction eq "edit"}'>
    <input type="submit" class="btn btn-success editPack"
      value="Сохранить" />
    </c:if>
    
  </div>

</form:form>

