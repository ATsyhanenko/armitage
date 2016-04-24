<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

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
        
        startPos = [$(this).attr("posX"), $(this).attr("posY")]
    	console.log(startPos)
    })
})

</script>
<div class="main">
  <div class="row">
    <div class="col-md-12">
      <div class="col-md-5" style="overflow:hidden">
        <canvas id="canvas" width="445" height="445"></canvas>
          <img src="res/img/marker.png" id="markerImg" class="hide">
        <script>
        var canvas, startPointer, endPointer, line, startPos, endPos;
        
        function clearCanvas(){
			startPointer.remove()
			endPointer.remove()
			line.remove();
		}
		function makePath(startPos, endPos){
			clearCanvas();
			
			startPointer.set({"left":startPos[0], "top":startPos[1]})
			endPointer.set({"left":endPos[0], "top":endPos[1]})
    		
    		var yFix = 12,
    			xFix = -2;
    		
    		var x1 = new Number(startPos[0])+xFix,
    			x2 = new Number(endPos[0])+xFix,
    			y1 = new Number(startPos[1])+yFix,
    			y2 = new Number(endPos[1])+yFix
    		
			line.set({"x1": x1})
			line.set({"y1": y1})
			line.set({"x2": x2})
			line.set({"y2": y2})

			canvas.add(line)
			canvas.add(startPointer)
			canvas.add(endPointer)
			
			canvas.renderAll();
		}
        
        $(document).ready(function(){
    		canvas = new fabric.Canvas('canvas');

    		
    		var imgElement = document.getElementById('markerImg');
    		startPointer = new fabric.Image(imgElement, {
    		  opacity: 0.85,
    		});
    		
    		
    		endPointer = new fabric.Image(imgElement, {
    		  opacity: 0.85
    		});
    		
    		
    		line = new fabric.Line([0, 0, 0, 0], {
    			fill: 'yellow',
    			opacity: 0.8,
    			strokeWidth: 3
    		}); 
    		
    		
    		
    		canvas.add(line);
    		canvas.add(startPointer);
    		canvas.add(endPointer);
    		
    		clearCanvas();
    	})
        </script>
      </div>
      <div class="clearfix visible-xs"></div>
      <div class="col-md-7">
        <span><b>Выберите локацию</b></span>
        <table class="table table-hover">
          <tbody>
            <c:forEach items="${locationList}" var="location">
              <tr rel="${location.locationId}" class="location" posX="${location.mapReference.posX}" posY="${location.mapReference.posY}">
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