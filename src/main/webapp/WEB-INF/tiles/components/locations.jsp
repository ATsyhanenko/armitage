<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<script type="text/javascript">
var locationId;
var packageList
$(document).ready(function(){
    console.log("ready");
    packageList = $("#productTable");
    
    var previouslySelected;

    $(".location").click(function(){
        console.log("location#"+$(this).attr("rel")+" is clicked")
        if(previouslySelected != null){
            previouslySelected.removeClass("success")
        }
        previouslySelected = $(this)
        $(this).addClass("success")
        locationId = $(this).attr("rel");
        var json = {
            locationId: $(this).attr("rel")
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
    })
})

</script>
<div class="main">
  <div class="row">
    <div class="col-md-12">
      <div class="col-md-5">
        <img src="res/img/map.jpg" style="width: 100%; height: 100%" />
      </div>
      <div class="clearfix visible-xs"></div>
      <div class="col-md-7">
        <span><b>Выберите локацию</b></span>
        <table class="table table-hover">
          <tbody>
            <c:forEach items="${locationList}" var="location">
              <tr rel="${location.locationId}" class="location">
                <td>${location.locationName}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <div class="col-md-5" id="productTable"></div>
    <div class="col-md-7" id="packageInfo"></div>
  </div>
</div>