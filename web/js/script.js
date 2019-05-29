function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function checkCookie() {
    var user = getCookie("mail");
    console.log("checking cookie "+user);
    if (user == "" || user=='""') {
        console.log("user not logged in : ");
    } else {
        console.log("user already logged in : " + user);
        window.location.href = "searchtrain.html";
    }
}

function resultpage(traindetails) {
    var trains = JSON.parse(traindetails);
    var page ='<form id="form"class="box" action="booking" method="GET">'+
        '<h1>SEARCH RESULTS</h1>'+
        '<hr> ' +
        '        <center><a id="hidden" style="color: #e67e22">CHOOSE A TRAIN TO CONTINUE !</a></center>';
    for(train in trains){
        console.log("trainid" + "  " +trains[train]["trainid"]);
        var traininfo = JSON.stringify(trains[train]);
        console.log("tinfo  " + traininfo);
        page+= '<input id="rad" name="trainchosen" type="radio" value="'+trains[train]["trainid"]+'" required/> ';
        page+= '<label> Train Number </label>' + '<a>' + trains[train]["trainnumber"] +'</a>';
        page+= '<label> Train Name </label>' + '<a>' + trains[train]["trainname"] +'</a>';
        page+= '<label> Source </label>' + '<a>' + trains[train]["source"] +'</a>';
        page+= '<label> Destination </label>' + '<a>' + trains[train]["dest"] +'</a>';
        page+= '<label> Arrival Time </label>' + '<a>' + trains[train]["sourcetime"] +'</a>';
        page+= '<label> Reaching Time </label>' + '<a>' + trains[train]["desttime"] +'</a>';
        page+= '<br><br>';
        page+= '<label>SEATS AVAILABLE</label>'
        page+= '<br><br>';
        for(seat in trains[train]["seats"]){
            var seatcount = trains[train]["seats"][seat]["seatcount"];
            if(seatcount<=0){
                seatcount = "WL "+(Math.abs(seatcount)+1);
            }
            page+= ' &nbsp; ' +'<label>'+trains[train]["seats"][seat]["seattype"] +'</label>'+ ' &nbsp; ' + '<a> '+ seatcount +'</a>';
        }
        page+="<br><br>";
        page+="<hr>";
    }
    page+='<input type="button" value="BOOK TICKET" onclick="passengerInfo()"/>';
    page+="</form>";
    // document.getElementById("searchform").innerHTML = "";
    // document.getElementById("searchform").style.display="none";
    document.getElementById("searchresult").innerHTML += page;
    document.getElementById("form").style.width = "90%";
}

function passengerInfo() {
    var trains = document.getElementsByName("trainchosen");
    var flag = 0;
    var trainchosen;
    for(var i=0;i<trains.length;i++){
        console.log("checking checkeeD  "+ trains[i].value);
        if(trains[i].checked){
            trainchosen = trains[i].value;
            flag = 1;
        }
    }
    sessionStorage.setItem("trainchosen",trainchosen);
    if(flag==0){
        document.getElementById("hidden").style.display = "block";
        document.getElementById("hidden").style.color = "#2ecc7 !important";
        return;
    }
    console.log("In passenger train chosen " + trainchosen);
    // passcount = 0;
    var passinfopage = ' <form id="passform" style="width: 90%" class="box" method="GET">\n' +
        '<input id="addbtn" type="button" onclick="addpassenger()" value="Add Passenger"/>' +
        '<center><a id ="hidden" style="color: orange">ONLY SIX PASSENGERS CAN TRAVEL PER TICKET</a></center>' +
        '                <table align="center" border="2">\n' +
        '                    <tr>\n' +
        '                        <th><label>NAME</label></th>\n' +
        '                        <th><label>AGE</label></th>\n' +
        '                    </tr>\n' +
        '               <tbody id="addpassenger"></tbody>\n' +
        '<br><br>' +
        '                </table>\n' +
        '<button onclick="paymentPage()">SUBMIT</button>' +
        '            </form>\n';
    // document.getElementById("form").style.width = "90%";
    document.getElementById("searchresult").innerHTML = passinfopage;
    addpassenger();
    }

    var passcount = 0;

    function addpassenger() {
        passcount++;
        if(passcount>6){
            document.getElementById("hidden").style.display = "block";
            return;
        }
        var passenger =  '<tr>' +
            '<td><input type="text" name="pname" placeholder="NAME" maxlength="25" /></td>' +
            '<td><input type="text" name="page" placeholder="AGE" maxlength="2" pattern="[0-9]{1,2}"/></td>' +
            '</tr>';
        document.getElementById("addpassenger").innerHTML += passenger;
    }

    function paymentPage() {
        sessionStorage.setItem("passengers",passcount);
        var pnames = document.getElementsByName("pname");
        var pages  = document.getElementsByName("page");
        for(var i=1;i<=pnames.length;i++){
            sessionStorage.setItem("pname"+i,pnames[i-1].value);
            sessionStorage.setItem("page"+i,pages[i-1].value);
        }
        document.getElementById("passform").style.display="none";

    }

function getURLParameter(name,url) {
    if (!url) url = location.href;
    name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
    var regexS = "[\\?&]"+name+"=([^&#]*)";
    var regex = new RegExp( regexS );
    var results = regex.exec( url );
    return results == null ? null : results[1];
}

function listTrains() {
    // var from = document.getElementById("from").value;
    // var to   = document.getElementById("to").value;
    // var date = document.getElementById("date").value;
    var url = location.href;
    var from = getURLParameter("from",url);
    var to = getURLParameter("to",url);
    var date = getURLParameter("date",url);
    console.log("Params retrieved from url "+date+"  "+from +"  "+to);
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if(xhr.status==200 && xhr.readyState==4){
            console.log(xhr.responseText);
            var traindetails = xhr.responseText;
            resultpage(traindetails);
        }
    }
    xhr.open("GET","search?from="+from+"&"+"to="+to+"&"+"date="+date,true);
    xhr.send();
}

function swapText(){
        var from = document.getElementById("from");
        var to = document.getElementById("to");
        var temp = from.value;
        from.value = to.value;
        to.value   = temp;
}


function  dateValidation() {
    var form = document.getElementById("searchform");
    // var from = document.getElementById("from").value;
    // var to   = document.getElementById("to").value;
    var inputdate = document.getElementById("date").value;
    var idate = new Date(inputdate);
    var today = new Date();
    var limitdate = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
    var ldate = new Date(limitdate);
    today = new Date();

    console.log("tt"+ idate +" ldate \n" + ldate);

    if(idate>=today){
        document.getElementById("datewarn").style.display = "none";

        form.submit();
        // listTrains();
    }
    else{
        document.getElementById("datewarn").style.display = "block";
    }
}