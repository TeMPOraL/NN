var server="http://localhost:8080/";

var urlAsk = server + "getTimetables";
var urlLearn = server + "rememberTimetable";



function timetable(fullName, URL) {
    this.fullName = fullName;
    this.URL = URL;
}

var timetables = [new timetable("4 (Urzędnicza -> Wzgórza Krzesławickie)", "http://rozklady.mpk.krakow.pl/aktualne/0004/0004t039.htm"),
                  new timetable("4 (AWF -> Bronowice Małe)", "http://rozklady.mpk.krakow.pl/aktualne/0004/0004t014.htm"),
                  new timetable("102 (Mazowiecka -> Zakamycze)", "http://rozklady.mpk.krakow.pl/aktualne/0102/0102t005.htm"),
                  new timetable("102 (Chełm -> Krowodrza Górka)", "http://rozklady.mpk.krakow.pl/aktualne/0102/0102t026.htm"),
                  new timetable("130 (Urząd Marszałkowski -> Dworzec Główny Zachód)", "http://rozklady.mpk.krakow.pl/aktualne/0130/0130t006.htm"),
                  new timetable("130 (Dworzec Główny Zachód -> Azory)", "http://rozklady.mpk.krakow.pl/aktualne/0130/0130t011.htm"),
                  new timetable("15 (Dworzec Główny -> Pleszów)", "http://rozklady.mpk.krakow.pl/aktualne/0015/0015t039.htm"),
                  new timetable("15 (AWF -> Cichy Kącik)", "http://rozklady.mpk.krakow.pl/aktualne/0015/0015t017.htm"),
                  new timetable("152 (Dworzec Główny -> Olszanica)", "http://rozklady.mpk.krakow.pl/aktualne/0152/0152t020.htm"),
                  new timetable("152 (Chełm -> Aleja Przyjaźni)", "http://rozklady.mpk.krakow.pl/aktualne/0152/0152t042.htm"),
                 ];

var locations = {};
locations['home'] = [50.071994, 19.919183];
locations['work'] = [50.073996, 19.99902];
locations['gf'] = [50.067883, 19.846343];
locations['center'] = [50.064885, 19.94495];


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
    notifyTimetableSelected(timetableId);
    displayTimetable(timetables[timetableId]);
}

function setDefaultInputValues() {
    jQuery("#ctl-time").val("16:00");
}

function getRecommendations() {
    var data = grabInputData();

    console.debug("get recommendations");
    console.debug(data);
    
    var requestUrl = urlAsk + '?day=' + data[0] + '&time=' + data[1] + '&locX=' + data[2] + '&locY=' + data[3];
    console.debug(requestUrl);

    $.ajax({
        url : requestUrl,
        dataType : "jsonp",
        success : function(data) {
            console.debug(data);
        }
        });
}

function notifyTimetableSelected(timetableId) {
    var data = grabInputData();
    console.debug("loading TBL: " + timetableId);
    console.debug(data);
    //TODO Do AJAX call here.
}

function grabInputData() {
    var time = jQuery("#ctl-time").val().split(':').join('.');
    var day = jQuery("#ctl-day-of-the-week").val();
    var loc = jQuery("#ctl-location").val();
    var locX = locations[loc][0];
    var locY = locations[loc][1];

    return [day, time, locX, locY];
}

function bindHandlers() {
    $("select,input").change(function() { getRecommendations(); });
}

jQuery(document).ready(function() {
    setDefaultInputValues();
    bindHandlers();
    drawListOfTimetables();
    getRecommendations();
    //init by guessing a timetable
});
