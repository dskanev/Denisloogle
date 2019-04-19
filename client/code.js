$(document).ready(function() {
  var baseUrl = "http://localhost:8080";

  $("#urllist").hide();

  $("#searchbutton").click(function() {
    console.log("Sending request to server.");

    $.ajax({
      method: "GET",
      url: baseUrl + "/search",
      data: { query: $("#searchbox").val() }
    })
      .success(function(data) {
        console.log("Received response " + data);
        var buffer = "<ul>\n";

        if (data.resultList.length == 0) {
          $("#responsesize").html(
            "<p>No results found in " +
              data.queryTime / 1000000000 +
              " seconds</p>"
          );
        } else {
          $("#responsesize").html(
            "<p>" +
              "'" +
              $("#searchbox").val() +
              "'" +
              " was found on " +
              data.resultList.length +
              " websites in " +
              data.queryTime / 1000000000 +
              " seconds</p>"
          );
        }

        $.each(data.resultList, function(index, value) {
          buffer +=
            '<li><a href="' +
            value.url +
            '">' +
            value.title +
            "</a>" +
            "<br>" +
            value.shortDescription +
            "</li>\n" +
            "<br>";
        });

        buffer += "</ul>";

        // Show the url list if websites are available
        if (data.resultList.length > 0) {
          $("#urllist").show();
          $("#urllist").html(buffer);
        } else {
          $("#urllist").hide();
          $("#urllist").html("");
        }
      })
      .error(function(data) {
        console.log(data);
        $("#responsesize").html(
          "<p>Error: Couldn't connect to the server...</p>"
        );
      });
  });
});
