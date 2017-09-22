<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
      google.charts.load("current", {packages:["corechart"]});
      google.charts.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['${chartHeaders[0]}', '${chartHeaders[1]}'],
          <c:forEach var="line" items="${topologyDistribution}" varStatus="loop">
          		['${fn:replace(line._id, ', NOS', '')}', ${line.total}]<c:if test="${!loop.last}">,</c:if>
          </c:forEach>
        ]);

        var options = {
          title: '${chartTitle}',
          is3D: true,
          sliceVisibilityThreshold: 0.01
        };

        var chart = new google.visualization.PieChart(document.getElementById('${chartId}'));
        chart.draw(data, options);
      }
    </script>