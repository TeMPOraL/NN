function timetable(id, fullName, URL, coordsX, coordsY) {
    this.id = id;
    this.fullName = fullName;
    this.URL = URL;
    this.coordsX = coordsX;
    this.coordsY = coordsY;
}


var timetables = [new timetable(1, "4 (Urzędnicza -> Wzgórza Krzesławickie)", "http://rozklady.mpk.krakow.pl/aktualne/0004/0004t039.htm", 50.071994, 19.919183),
                  new timetable(2, "4 (AWF -> Bronowice Małe)", "http://rozklady.mpk.krakow.pl/aktualne/0004/0004t014.htm", 50.073996, 19.99902),
                  new timetable(3, "102 (Mazowiecka -> Zakamycze)", "http://rozklady.mpk.krakow.pl/aktualne/0102/0102t005.htm", 50.077960, 19.917692),
                  new timetable(4, "102 (Chełm -> Krowodrza Górka)", "http://rozklady.mpk.krakow.pl/aktualne/0102/0102t026.htm", 50.067883, 19.846343),
                  new timetable(5, "130 (Urząd Marszałkowski -> Dworzec Główny Zachód)", "http://rozklady.mpk.krakow.pl/aktualne/0130/0130t006.htm", 50.078858, 19.922319),
                  new timetable(6, "130 (Dworzec Główny Zachód -> Azory)", "http://rozklady.mpk.krakow.pl/aktualne/0130/0130t011.htm", 50.064885, 19.94495),
                  new timetable(7, "15 (Dworzec Główny -> Pleszów)", "http://rozklady.mpk.krakow.pl/aktualne/0015/0015t039.htm", 50.064885, 19.94495),
                  new timetable(8, "15 (AWF -> Cichy Kącik)", "http://rozklady.mpk.krakow.pl/aktualne/0015/0015t017.htm", 50.073996, 19.99902),
                  new timetable(9, "152 (Dworzec Główny -> Olszanica)", "http://rozklady.mpk.krakow.pl/aktualne/0152/0152t020.htm", 50.064885, 19.944959),
                  new timetable(10, "152 (Chełm -> Aleja Przyjaźni)", "http://rozklady.mpk.krakow.pl/aktualne/0152/0152t042.htm", 50.067883, 19.846343),
                 ];


function displayTimetable(timetable) {
    $("#mpk-timetable").attr('src', timetable.URL);
}

function drawListOfTimetables() {
    $("#links-to-available-timetables").append("<ul></ul>");
    $.each(timetables, function(i, v) {
        $("#links-to-available-timetables ul").append("<li><a href='#' onclick='loadTimetable(" + i + "); return false;'>" + v.fullName + "</a></li>");
    });
}

function loadTimetable(timetableId) {
    //TODO learn NN
    displayTimetable(timetables[timetableId]);
}

jQuery(document).ready(function() {
    drawListOfTimetables();
    //init by guessing a timetable
});
