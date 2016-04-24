<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

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
<h3><spring:message code="price.form.addform"/></h3>
</c:if>
<c:if test='${formAction eq "edit"}'>
<h3><spring:message code="price.form.editform"/></h3>
</c:if>
<form:form commandName="sellingPrice" id="priceForm"
  class="form-horizontal">
  <div class="form-group">
    <label for="packPrice" class="col-sm-2 control-label"><spring:message code="price.form.price"/></label>
    <div class="col-sm-10">
      <form:input path="price" type="number" step="0.01"
        class="form-control" min="0.00" />
    </div>
  </div>

  <div class="form-group">
    <label for="priceLock" class="col-sm-2 control-label"><spring:message code="price.form.location"/></label>
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
    <input type="submit" class="btn btn-success editPack"
      value='<spring:message code="price.form.confirm"/>' />
  </div>
</form:form>

