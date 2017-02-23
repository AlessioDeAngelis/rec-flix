<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width"/>
    <link rel="stylesheet" type="text/css" href="owlCarousel/owl.theme.default.min.css">
    <link rel="stylesheet" type="text/css" href="css/movie-recommender.css">
    <link rel="stylesheet" type="text/css" href="owlCarousel/owl.carousel.min.css">
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <script src="owlCarousel/owl.carousel.min.js"></script>
</head>
<body>
<script>
    $(document).ready(function () {
        jQuery.ajax({
            url: 'http://localhost:8080/recommend',
            type: 'GET',
            crossOrigin: true,
            success: function (recommendations) {
                console.log(recommendations);
                for (var recommenderId = 0; recommenderId < recommendations.length; recommenderId++) {
                    var recommendation = recommendations[recommenderId];
                    //add the recommendation title
                    $("#recommenders").append("<div class='row'><h1>" + recommendation.name + "</h1></div>");
                    //add the carousel for the current recommender
                    $("#recommenders").append("<div class='row'><div id='recommenderCarousel" + recommenderId + "' class='owl-carousel owl-theme'></div></div>");
                    //adding the recommended movies
                    var movies = recommendation.movies;
                    for (var i = 0; i < movies.length; i++) {
                        var movie = movies[i];
                        $('#recommenderCarousel' + recommenderId).append("<div class='item'><img src ='" + movie.poster + "' ></div>");
                    }
                    $('#recommenderCarousel' + recommenderId).owlCarousel({
                        margin: 10,
//                    nav: true,
                        loop: true,
                        responsive: {
                            0: {
                                items: 1
                            },
                            600: {
                                items: 3
                            },
                            1000: {
                                items: 5
                            }
                        }
                    });
                }
            }
        });
    });
</script>
<div class="container">
    <div id="recommenders"></div>
</div>
</body>
</html>