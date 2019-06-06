function login(){
    document.getElementById("admin").style.display="none";
    document.getElementById("hidden").style.display="none";

    var xhr = new XMLHttpRequest();
    var url = "login";
    xhr.onreadystatechange = function () {
        if(xhr.status==200 && xhr.readyState==4){
            var reply = xhr.responseText;
            console.log("reply "+reply);
            if(reply=="OK"){
                window.location.href="searchtrain.html";
            }
            else if(reply=="admin"){
                console.log("ADMIN DASHBOARD NOT DESIGNED");
                document.getElementById("admin").style.color = "orange";
                document.getElementById("admin").style.display = "block";
            }
            else{
                document.getElementById("hidden").style.color = "orange";
                document.getElementById("hidden").style.display = "block";
            }
        }
    }
    var pass1 = document.getElementsByName("pass")[0].value;
    var hashObj = new jsSHA("SHA-512","TEXT",{numRounds:1});
    hashObj.update(pass1);
    var hash = hashObj.getHash("HEX");
    // pass1 = hash;
    // alert(hash);
    xhr.open("POST",url,true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("mail="+document.getElementsByName("mail")[0].value+"&pass="+hash);
}

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

function logOut() {
    window.location.href="logout";
}

function setMail() {
    var mail = getCookie("mail");
    document.getElementById("blogout").innerHTML+="<br>"+mail;
}

function checkCookie() {
    var user = getCookie("mail");
    var url = window.location.href;
    var dir = url.split('/').pop();
    console.log("checking cookie "+user);
    console.log("this url "+dir);
    if (user == "" || user=='""' ) {
        console.log("user not logged in : ");
        if(dir=='index.html' || dir=='""' || dir==""){
            // window.location.href = "index.html";
        }
        else if(dir!='index.html' && dir!='""' && dir!="/" && dir!='pnrstatus.html'){
            window.location.href = "index.html";
        }
        return false;
    } else {
        console.log("user already logged in : " + user);
        if(dir == "" || dir == "index.html" || dir == "index.html?"){
            window.location.href = "searchtrain.html";
        }
        return true;
    }
}

function validateRegistration() {
    var pass1 = $("input[name=pass1]").val();
    if(pass1.length<8){
        return;
    }
    else{
        register();
    }
}

function register() {
    document.getElementById("hidden").style.display="none";
    var uname = document.getElementsByName("user")[0].value;
    var email = document.getElementsByName("email")[0].value;
    var phno  = document.getElementsByName("phno")[0].value;
    var gender = $("input[name=gender]:checked").val();
    var pass1 = $("input[name=pass1]").val();
    console.log("INPUTS RIGHT");
    var url = "Registration";
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function (ev) {
        if(xhr.readyState==4 && xhr.status==200){
            var reply = xhr.responseText;
            if(reply=="SUCCESS"){
                document.getElementById("regform").style.display="none";
                var page = '<div id="success" class="box">' +
                    '<h1>ACCOUNT CREATED SUCCESSFULLY</h1>' +
                    '<br>' +
                    '<h1><i class="fa fa-thumbs-up"></h1></i>' +
                    '</div>';
                document.getElementById("msg").innerHTML = page;
                document.getElementById("success").style.width="60%";
                setTimeout(function () {
                    window.location.href='index.html';
                }, 1500);

            }
            else if(reply=="Mail ID already registered"){
                document.getElementById("hidden").style.color="orange";
                document.getElementById("hidden").style.display="block";
            }
        }
    }
    var hashObj = new jsSHA("SHA-512","TEXT",{numRounds:1});
    hashObj.update(pass1);
    var hash = hashObj.getHash("HEX");
    // pass1 = hash;
    // alert(hash);
    xhr.open("POST",url,true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("user="+uname+"&email="+email+"&phno="+phno+"&gender="+gender+"&pass1="+hash);
}

function resultpage(traindetails) {
    changeCSS("css/ticketpage.css",0);

    var trains ;
    var emptyFlag=false;
    console.log("train details key length"+ Object.keys(traindetails).length);
    console.log("traindetails.length" + jQuery.isEmptyObject(traindetails));
    console.log("traindetails " + traindetails);
    if(traindetails=="" || traindetails.length==2 ){
        console.log("No trains");
        emptyFlag = true;
    }else {
        trains = JSON.parse(traindetails);
        console.log("traindetails after parse" + traindetails);
        console.log("traindetails.length" + traindetails.length);
        console.log("traindetails " + traindetails);
        sessionStorage.setItem("traindetails",traindetails);
    }

    var page ='<div id="form"class="box">'+
        '<h1>SEARCH RESULTS</h1>'+

        '        <center><a id="hidden" style="color: #e67e22">CHOOSE A TRAIN TO CONTINUE !</a></center>' +
        '<table class="trains">' +
        '<tr>' +
        '<th class="namebox width33">' +
        '<label class="heading"> Train Name </label>' +
        '</th>' +
        '<th class="width33">' +
        '<label class="heading"> Departure </label>' +
        '</th>' +
        '<th class="width33">' +
        '<label class="heading"> Arrival </label>' +
        '</th>' +
        '</tr>' +
        '</table>' +
        '';
    for(train in trains){
        console.log("trainid" + "  " +trains[train]["trainid"]);
        var traininfo = JSON.stringify(trains[train]);
        console.log("tinfo  " + traininfo);
        page+=  '<div class="boxed">' +
            '<table class="trains">' +
            '<tr class="trains">' +
            '<td class="width33 left">' +
            '<label class="text30">' + trains[train]["trainname"] +'</label>' +'<br> <a style="color: gray">' + trains[train]["trainnumber"] +'</a>' +
            '</td>';
        page+=  '<td class="width33">' +
            '<a class="text25">' + trains[train]["sourcetime"] +'</a>' + '<br><a style="color: gray ;">' + trains[train]["source"] +'</a>' +
            '</td>';
        page+=  '<td class="width33">' +
            '<a class="text25">' + trains[train]["desttime"] +'</a>' + ' <br><a style="color: gray ; ">' + trains[train]["dest"] +'</a>' +
            '</td>' +
            '</tr>' +
            '</table>';
        page+= '<br>';
        page+= '<div class="left">' +
            '<label>ROUTE</label>'
        for(var routeno = 0 ;routeno<trains[train]["route"].length;routeno++){
            var location = trains[train]["route"][routeno];
            page+= ' &nbsp; ' + '<a> '+ location +'</a>' ;
            if(routeno!=trains[train]["route"].length-1){
                page+= '<a>  &rarrtl;  </a>';
            }
        }
        page+='</div>';
        page+="<br>" ;
        page+= '<label style="font-size: 20px">SEATS AVAILABLE</label>'
        page+= '<br><br>';
        for(seat in trains[train]["seats"]){
            var seatcount = trains[train]["seats"][seat]["seatcount"];
            if(seatcount<=0){
                seatcount = "WL "+(Math.abs(seatcount)+1);
            }
            page+= ' &nbsp; ' +'<button id="seatbtn" onclick="passengerInfo(this)" value="'+trains[train]["trainid"]+'/'+trains[train]["seats"][seat]["seattype"] +'">'+trains[train]["seats"][seat]["seattype"] + ' &nbsp; ' + '<a class="seatcount"> '+ seatcount +'</a>' + '</button>';
        }
        page+= '<br><br>' +
            "</div>" +
            "<br>";
        // page+="<hr>";
    }
    if(emptyFlag){
        page+="<h1 style='color:#2ecc71;'>NO TRAINS FOUND IN THE SELECTED ROUTE</h1>"
    }

    page+="</div>";

    document.getElementById("searchresult").innerHTML += page;
    document.getElementById("form").style.width = "90%";
    document.getElementById("form").style.marginTop="-5%";
}

function passengerInfo(selection) {

    var selectioninfo = selection.value.split("/");
    console.log("selection : "+selectioninfo)
    console.log("selection : "+selectioninfo[0]);
    console.log("selection : "+selectioninfo[1]);

    var trainchosen = selectioninfo[0];
    var seatchosen  = selectioninfo[1];

    sessionStorage.setItem("trainchosen",trainchosen);
    sessionStorage.setItem("seatchosen",seatchosen);

    console.log("In passenger train chosen " + trainchosen);

    var passinfopage = ' <div id="passform" style="width: 90%" class="box" >\n' +

        '<h3 id="fillseat" style="display: none; color: orange">SELECT THE SEAT TYPE</h3>' +
        '<br>' +
        '   <h1 style="color: white" >Enter Passenger Details</h1>' +
        '<center><a id ="hidden" style="color: orange">ONLY SIX PASSENGERS CAN TRAVEL PER TICKET</a></center>' +
        '<center><a class="hidden" id="child" style="color: orange">CHILDREN BELOW THE AGE OF 5 DONT NEED TICKET</a></center>' +
        '<h3 id="fillinfo" style="display: none; color: orange">PLEASE FILL ALL THE NECESSARY INFO</h3>' +
        '<center><a id="missingdetails" style="color: orange;display: none;">Enter the necessary details</a></center>' +
        '<input id="addbtn" type="button" onclick="addpassenger()" value="Add Passenger"/>' +
        '                        <div id="addpassenger"></div>\n' +
        '<br>' +
        '<button id="subbutton" onclick="validatePassengerInfo()">SUBMIT</button>' +
        '            </div>\n';

    document.getElementById("searchresult").innerHTML = passinfopage;
    addpassenger();
    }

    var passcount = 0;

    function addpassenger() {
        changeCSS("css/ticketpage.css",0);
        passcount++;
        if(passcount>6){
            passcount--;
            document.getElementById("hidden").style.display = "block";
            return true;
        }
        var passenger = document.createElement("div");
        passenger.innerHTML = '<label>Traveller - '+passcount+'</label>' +
            '<br>' +
            '<table id="t01">\n' +
            '      <tr>\n' +
            '          <th>Name</th>\n' +
            '          <th>Age</th>\n' +
            '          <th>Gender</th>\n' +
            '      </tr>' +
            // '<table align="center" >
                    '<tr>' +
                        '<td><input style="color: #191919" type="text" name="pname" placeholder="Name of Traveller ' + passcount +' " maxlength="25" /></td> &emsp;' +
                        '<td><input style="color: #191919" type="text" name="page" placeholder="Age of Traveller '+ passcount+'" maxlength="2" pattern="[0-9]{1,2}"/></td>' +
                        '<td> <input  name="gender'+passcount+'" type="radio" Value="Male" required><a style="color: #191919">MALE</a>'
                    +   '<input name="gender'+passcount+'" type="radio" Value="Female" required><a style="color: #191919">FEMALE</a>'
                    +   '<input name="gender'+passcount+'" type="radio" Value="Others" required> <a style="color: #191919">OTHERS</a> </td> ' +
                    '</tr>' +
            '</table>' +
            '<br>' +
            '';
        document.getElementById("addpassenger").appendChild(passenger);
    }

    function validatePassengerInfo() {
        document.getElementById("fillseat").style.display="none";
        document.getElementById("fillinfo").style.display="none";
        document.getElementById("child").style.display="none";

        var infoflag = false;
        var childflag = false;
        var pnames = document.getElementsByName("pname");
        var pages = document.getElementsByName("page");
        for(var i =0;i<pnames.length;i++){
            if(pnames[i].value ==""){
                infoflag = true;
                break;
            }
            if(pages[i].value <5 && pages[i].value >0){
                childflag = true;
            }
            if(pages[i].value=="" || isNaN(pages[i].value) || pages[i].value<1 || (pages[i].value % 1 !== 0) ){
                infoflag = true;
                break;
            }
        }
        var genderflag = true;
        for(var j=1;j<=passcount;j++){
            genderflag = true;
            var genders = document.getElementsByName("gender"+j);
            for(var i=0;i<genders.length;i++){
                if(genders[i].checked){
                    genderflag=false;
                    break;
                }
            }
            if(genderflag){
                break;
            }
        }
        if (genderflag || infoflag){
            document.getElementById("fillinfo").style.display = "block";
        }
        if(childflag){
            document.getElementById("child").style.display="block";
        }
        if(infoflag==false && genderflag==false && childflag==false){
            paymentPage();
        }

    }

    function paymentPage() {

        changeCSS("css/style.css",0);

        console.log("passcount" + passcount);
        var index = 1;
        for(var j=1;j<=passcount;j++){
            var genders = document.getElementsByName("gender"+j);
            // var genders = "";
            for(var i=0;i<genders.length;i++){
                if(genders[i].checked){
                    sessionStorage.setItem("gender"+index,genders[i].value);
                    index++;
                    break;
                }
            }
        }


        sessionStorage.setItem("passcount",passcount);
        var pnames = document.getElementsByName("pname");
        var pages  = document.getElementsByName("page");

        for(var i=1;i<=pnames.length;i++){
            if(pnames[i-1]=="" || pages[i-1]==""){
                console.log("SOMETHING EMPTY");
                document.getElementById("hidden").style.display="none";
                document.getElementById("missingdetails").style.display="block";
                return true;
            }
        }
        for(var i=1;i<=pnames.length;i++){
            sessionStorage.setItem("pname"+i,pnames[i-1].value);
            sessionStorage.setItem("page"+i,pages[i-1].value);

            console.log("pname"+i +" "+sessionStorage.getItem("pname"+i));
            console.log("page"+i +" "+sessionStorage.getItem("page"+i));
        }

        document.getElementById("passform").style.display="none";

        var xhr = new XMLHttpRequest();
        var singlefare = 1;
        var totalfare = 1;
        xhr.onreadystatechange = function () {
            if(xhr.status==200 && xhr.readyState==4){
                var fareJSON = xhr.responseText;
                var obj = JSON.parse(fareJSON);
                singlefare = obj["fare"];
                totalfare = passcount*singlefare;
                console.log("Single fare "+singlefare +"Total Fare  "+totalfare);
                sessionStorage.setItem("singlefare",singlefare);
                sessionStorage.setItem("totalfare",totalfare);
                modeOfPayment();
            }
        }
        var trains = JSON.parse(sessionStorage.getItem("traindetails"));
        var trainchosen = sessionStorage.getItem("trainchosen");
        var seatchosen = sessionStorage.getItem("seatchosen");
        var srcstopno ;
        var deststopno ;

        for (train in trains){
            if(trains[train]["trainid"]==trainchosen){
                srcstopno = trains[train]["srcstop"];
                deststopno = trains[train]["dststop"];
            }
        }

        sessionStorage.setItem("srcstopno",srcstopno);
        sessionStorage.setItem("dststopno",deststopno);

        console.log(trainchosen + seatchosen +srcstopno +deststopno+passcount);

        var url = "passengerinfo?trainid="+ trainchosen;
        url+="&seatcount="+passcount;
        url+="&seattype="+seatchosen;
        url+="&srcstopno="+srcstopno+"&deststopno="+deststopno+"&";
        for(var i=1;i<=pnames.length;i++){
            url+= "pass"+i+"="+pnames[i-1].value+"&";
            url+= "gender"+i+"="+sessionStorage.getItem("gender"+i)+"&";
            url+= "page"+i+"="+pages[i-1].value;
            if(i!=pnames.length){
                url+="&";
            }
        }
        console.log("URL IS "+url);
        xhr.open("GET",url,true);
        xhr.send();
    }
    
    function modeOfPayment() {
        document.getElementById("passform").style.display="none";
        var mode = '<div id="paymentform" class="box" action="PaymentGateWay">\n' +
            '    <h1>TICKET FARE </h1>\n' +
            '    <h1>' + sessionStorage.getItem("passcount") + ' X ' + sessionStorage.getItem("singlefare") + ' = &#8377;' +sessionStorage.getItem("totalfare") +'</h1>' +
            // '    <h3 id="green"> TOTAL AMOUNT</h3> <h2 id="white"> </h2>\n' +
            '    <h4 id="green">SELECT YOUR MODE OF PAYMENT</h4>\n' +
            '    <input name="submit" type="button" onclick="'+ 'ticketConfirmation(this)'+'" value="Credit Card">\n' +
            '    <input name="submit" type="button" onclick="'+ 'ticketConfirmation(this)'+'" value="Debit Card">\n' +
            '    <input name="submit" type="button" onclick="'+ 'ticketConfirmation(this)'+'" value="Net Banking">\n' +
            '</div>';
        document.getElementById("searchresult").innerHTML = mode;
    }

    function ticketConfirmation(mode) {
            var xhr = new XMLHttpRequest();
            var url="PaymentGateWay?";
            xhr.onreadystatechange = function () {
                if(xhr.status==200 && xhr.readyState==4){
                    var ticket= xhr.responseText;
                    if(ticket==""){
                        window.location.href="logout";
                        return;
                    }
                    console.log(ticket);
                    sessionStorage.setItem("ticket",ticket);
                    console.log(JSON.parse(ticket));
                    ticketInfo();
                }
            }
            url+="mode="+mode.value+"&trainid="+sessionStorage.getItem("trainchosen");
            url+="&date="+sessionStorage.date+"&from="+sessionStorage.getItem("from");
            url+="&to="+sessionStorage.getItem("to")+"&seattype="+sessionStorage.getItem("seatchosen");
            url+="&fare="+sessionStorage.getItem("totalfare")+"&seatcount="+sessionStorage.getItem("passcount");
            url+="&srcstopno="+sessionStorage.getItem("srcstopno");

            url+="&deststopno="+sessionStorage.getItem("dststopno");

        console.log("url :"+url);
            xhr.open("GET",url,true);
            xhr.send();
    }

    function ticketInfo() {
        // changeCSS("css/ticketpage.css",0);
        var ticket = sessionStorage.getItem("ticket");
        ticket = JSON.parse(ticket);
        document.getElementById("paymentform").style.display="none";
        var ticketPage='' +
            '        <div>\n' +
            '            <form action="index.html">\n' +
            '                <input type="submit" id="home" class="submit-button" value="HOME">\n' +
            '            </form>\n' +
            '        </div>' ;
         // if(checkCookie()){
         //     ticketPage+='  <div>\n' +
         //         '                <button type="submit" id="blogout" onclick="logOut()" class="submit-button" >LOG OUT <a><i class="fa fa-sign-out"></i> </a></button>\n' +
         //         '        </div>';
         // }
         ticketPage+= '<div style="width: 90%" class="box">\n' +
            '  <h1 style="color: #2ecc71">TICKET INFO <i class="fa fa-ticket" aria-hidden="true"></i> </h1>\n' +
            '    <h2 style="color: #2ecc71;text-align: left;">TRAIN INFO : <i class="fa fa-subway" aria-hidden="true"></i> </h2>\n' +
            '  <table id="t01">\n' +
            '    <tr>\n' +
            '      <th>PNR NUMBER</th>\n' +
            '      <th>DATE OF TRAVEL</th>\n' +
            '      <th>TRAIN NAME</th>\n' +
            '      <th>TRAIN NUMBER</th>\n' +
            '      <th>SOURCE</th>\n' +
            '      <th>DESTINATION</th>\n' +
            '      <th>SOURCE TIME</th>\n' +
            '      <th>DESTINATION TIME</th>\n' +
            '      <th>SEAT TYPE</th>\n' +
            '      <th>TICKET FARE</th>\n' +
            '    </tr>\n' +
            '    <tr>\n' +
            '      <td>  '+ ticket["pnr"] +' </td>\n' +
            '      <td> '+ ticket["dateoftravel"] +' </td>\n' +
            '      <td>  '+ ticket["trainname"] +' </td>\n' +
            '      <td> '+ ticket["trainnumber"] +' </td>\n' +
            '      <td> '+ ticket["source"] +' </td>\n' +
            '      <td> '+ ticket["dest"] +' </td>\n' +
            '      <td> '+ ticket["stime"] +' </td>\n' +
            '      <td> '+ ticket["dtime"] +' </td>\n' +
            '      <td> '+ ticket["seattype"] +' </td>\n' +
            '      <td> Rs. '+ ticket["fare"] +'</td>\n' +
            '    </tr>\n' +
            '\n' +
            '  </table>'

        ticketPage+='  <h2 style="color: #2ecc71;text-align: left;">PASSENGERS INFO : <i class="fa fa-users" area-hidden="true"></i> </h2>\n' +
            '\n' +
            '  <table id="t01">\n' +
            '      <tr>\n' +
            '        <th>S.NO</th>\n' +
            '        <th>PASSENGER NAME</th>\n' +
            '        <th>AGE</th>\n' +
            '        <th>GENDER</th>\n' +
            '        <th>SEAT NUMBER</th>\n' +
            '        <th>TICKET STATUS</th>\n' +
            '      </tr>'

        var passenger = ticket["passengers"].split(",");
        var age       = ticket["ages"].split(",");
        var gender    = ticket["genders"].split(",");
        var seat      = ticket["seatnos"].split(",");
        var status    = ticket["status"].split(",");

        for(var i=0;i<ticket["seatcount"];i++){

            if(seat[i]<=0){
                seat[i]="NOT ASSIGNED";
            }

            ticketPage+='      <tr>\n' +
                '        <td> '+ (i+1) +' </td>\n' +
                '        <td>  '+passenger[i]+' </td>\n' +
                '        <td>  '+age[i]+'  </td>\n' +
                '        <td>  '+gender[i]+' </td>\n' +
                '        <td>  '+seat[i]+'  </td>\n' +
                '        <td>  '+status[i]+' </td>\n' +
                '      </tr>';
        }

        ticketPage+= '</table>' +
            '<input type="button" id="cbtn" onclick="printTicket()" value="PRINT TICKET">\n' +
            '<br>' +
            '  <label>*Please note the PNR number for future reference</label>'
            '</div>';
        
        document.getElementById("searchresult").innerHTML=ticketPage;
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
    var url = location.href;
    var from = getURLParameter("from",url);
    var to = getURLParameter("to",url);
    var date = getURLParameter("date",url);
    sessionStorage.setItem("from",from);
    sessionStorage.setItem("to",to);
    sessionStorage.setItem("date",date);
    console.log("Params retrieved from url "+date+"  "+from +"  "+to);
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if(xhr.status==200 && xhr.readyState==4){
            console.log(xhr.responseText);
            var traindetails = xhr.responseText;
            if(traindetails==""){
                console.log("No trains");
            }
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


function  searchInputValidation() {
    var form = document.getElementById("searchform");
    var from = document.getElementById("from").value;
    var to   = document.getElementById("to").value;

    if(from=="" || to==""){
        document.getElementById("hidden").style.color="orange";
        document.getElementById("hidden").style.display="block";
        return;
    }
    document.getElementById("hidden").style.display="none";
    var inputdate = document.getElementById("date").value;
    var idate = new Date(inputdate);
    var today = new Date();

    // var maxmm = today.setMonth(today.getMonth()+2);
    var yyyy = today.getFullYear();

    var maxdate = today.getFullYear()+'-'+(today.getMonth()+2)+'-'+today.getDate();
    maxdate = new Date(maxdate);
    maxmonth = maxdate.getMonth();

    today = new Date();

    var imm = idate.getMonth();
    var iyyyy = idate.getFullYear();

    console.log("today"+today+"inpu"+ idate +" ldate \n" + maxdate);

    if(idate>=today && (imm<=maxmonth) && iyyyy==yyyy ){
        document.getElementById("datewarn").style.display = "none";

        form.submit();
        // listTrains();
    }
    else{
        document.getElementById("datewarn").style.display = "block";
    }
}

function pnrCheck() {
    var xhr = new XMLHttpRequest();
    var url = 'pnrcheck?pnr='+document.getElementById("pnr").value;
    xhr.onreadystatechange = function () {
        if(xhr.status==200 && xhr.readyState==4){
            console.log(xhr.responseText);
            if(xhr.responseText=="INVALID"){
                console.log("pnr not found");
                document.getElementById("nopnr").style.display = "block";
                return;
            }
            else {
                var ticket = xhr.responseText;
                sessionStorage.setItem("ticket",ticket);
            }
            ticketInfo();
        }
    }
    console.log(url);
    xhr.open("GET",url,true);
    xhr.send();
}

function printTicket() {
    window.print();
}

function bookedTickets(lowerlimit,upperlimit) {
    var xhr = new XMLHttpRequest();
    var url = "bookedtickets?";
    xhr.onreadystatechange = function () {
        if(xhr.status=200 && xhr.readyState==4){
            var tickets = xhr.responseText;
            if(tickets==""){
                window.location.href="logout";
                return;
            }
            sessionStorage.setItem("tickets",tickets);
            console.log("tickets json pared = "+JSON.parse(tickets));
            console.log("tickets json no parse = "+tickets);
            bookedTicketsPage(lowerlimit,upperlimit);
        }
    }
    url+= 'lowerlimit='+lowerlimit+'&upperlimit='+upperlimit;
    console.log("inside booked Tickets "+url)
    xhr.open("GET",url,true);
    xhr.send();
}

function bookedTicketsPage(lowerlimit,upperlimit) {

        document.getElementById("home").onclick = function () {
            window.location.href = "searchtrain.html";
        }

        document.getElementById("home").innerHTML="HOME";

    window.scrollTo(0, 0);
    document.getElementById("searchform").style.display = "none";
    document.getElementById("bhistory").style.display = "none";
    changeCSS('css/ticketpage.css', 0);
    var tickets = sessionStorage.getItem("tickets");

    var page = '<div id="tickform" class="box" >';
    console.log("in booked"+tickets);
    if (tickets=="[]") {
        console.log("JSON IS empty");
        page += '    <h1> NO TICKETS BOOKED !</h1>';
        page += '</div>'
        document.getElementById("bookedtickets").innerHTML = page;
        return;
    }

    tickets = JSON.parse(tickets);

    var hide = [];

    for(var ticket = 0;ticket<tickets.length;ticket++){
        console.log("Ticket "+ticket);
        console.log("passid"+tickets[ticket]["passengerid"]);
        var tpnr = tickets[ticket]["pnr"];
        page += '    <h1 >TICKET INFO <i class="fa fa-ticket" aria-hidden="true"></i> </h1>\n' +
            '    <h2 >TRAIN INFO  <i class="fa fa-subway" aria-hidden="true"></i>  : </h2>';
        page += '\n' +
            '<div>\n' +
            '<table id="t01">\n' +
            '        <tr>\n' +
            '            <th>PNR NUMBER</th>\n' +
            '            <th>DATE OF TRAVEL</th>\n' +
            '            <th>TRAIN NAME</th>\n' +
            '            <th>TRAIN NUMBER</th>\n' +
            '            <th>SOURCE</th>\n' +
            '            <th>DESTINATION</th>\n' +
            '            <th>SOURCE TIME</th>\n' +
            '            <th>DESTINATION TIME</th>\n' +
            '            <th>SEAT TYPE</th>\n' +
            '            <th>TICKET FARE</th>\n' +
            '        </tr>\n' +
            '\n' +
            '        <tr>\n' +
            '            <td>  '+tickets[ticket]["pnr"]+' </td>\n' +
            '            <TD>'+tickets[ticket]["dateoftravel"]+'</TD>\n' +
            '            <td> '+tickets[ticket]["trainname"]+' </td>\n' +
            '            <td>  '+tickets[ticket]["trainnumber"]+' </td>\n' +
            '            <td> '+tickets[ticket]["from"]+' </td>\n' +
            '            <td> '+tickets[ticket]["to"]+' </td>\n' +
            '            <td> '+tickets[ticket]["stime"]+' </td>\n' +
            '            <td> '+tickets[ticket]["dtime"]+' </td>\n' +
            '            <td> '+tickets[ticket]["seattype"]+' </td>\n' +
            '            <td> Rs. '+tickets[ticket]["ticketfare"]+'</td>\n' +
            '        </tr>\n' +
            '\n' +
            '    </table>\n' +
            '</div>' +
            '    <br>\n' +
            '    <br>\n' +
            '\n' +
            '    <h2 >PASSENGERS INFO <i class="fa fa-users" area-hidden="true"></i>  :</h2>\n' +
            '\n' +
            '    <table id="t01">\n' +
            '        <tr>\n' +
            '            <th>S.NO</th>\n' +
            '            <th>PASSENGER NAME</th>\n' +
            '            <th>GENDER</th>\n' +
            '            <th>AGE</th>\n' +
            '            <th>SEAT NUMBER</th>\n' +
            '            <th>STATUS</th>\n' +
            '            <th name="cantab'+tpnr+'">CANCELLATION</th>\n' +
            '        </tr>';
        console.log("in t "+"cantab"+tpnr);
            var p = 1;

            var canflag = 0;
            var ticketpasscount = 0;
            do{
                if(tickets[ticket]["pnr"]!=tpnr){
                    break;
                }
                console.log(ticket);
                var pname = tickets[ticket]["passenger"];
                var gender = tickets[ticket]["gender"];
                var age = tickets[ticket]["age"];
                var seatno = tickets[ticket]["seatno"];
                var passid = tickets[ticket]["passengerid"];
                var status = tickets[ticket]["ticketstatus"];
                var cancelbtn = "CANCEL THIS TICKET";
                if(status=="CANCELLED" || status=="REFUNDED"){
                    cancelbtn = "CANCELLED SUCCESSFULLY";
                }

                if(seatno<=0){
                    seatno = "NOT ASSIGNED";
                }

                ticket++;

                page+='        <tr>\n' +
                    '            <td> '+(p++)+' </td>\n' +
                    '            <td> '+pname+' </td>\n' +
                    '            <td> '+gender+' </td>\n' +
                    '            <td> '+age+' </td>\n' +
                    '            <td> '+seatno+' </td>\n' +
                    '            <td id="'+passid+'" > '+status+' </td>\n' ;

                if(status=="CONFIRMED"){

                page+=' <td>\n' +
                    '                <input id="passid" name="passengerid" type="hidden" value="">\n' +
                    '                <button id="canbtn" name="'+passid+'" onclick="cancelSeat(this)" type="button" ';
                        if(status=="CANCELLED" || status=="REFUNDED") {
                            page += ' disabled ';
                        }
                        page += ' value="'+passid+'">'+cancelbtn+'</button>\n' +
                            '            </td>\n' ;
                        ticketpasscount++;
                }else {
                        page+='<td name="cantab'+tpnr+'">' +
                            ' ' +
                            '</td>'
                        canflag++;
                         ticketpasscount++;
                }
                    '        </tr>'    ;

                if(ticket>=Object.keys(tickets).length){
                    break;
                }
                var newpnr = tickets[ticket]["pnr"];

            }while (newpnr===tpnr);

        page+='</table>\n' +
            '\n' +
            '    <br><br>\n' +
            '    <hr color="#2ecc71">\n' +
            '    <br><br>';
        ticket--;

        console.log("thispasscount "+ticketpasscount+ "  canflag "+canflag);

        if(canflag==ticketpasscount){
            console.log("in t "+"cantab"+tpnr);
            hide.push("cantab"+tpnr);
        }

    }
    page += '</div>';

    page+='<span>';
    if(lowerlimit!=0){
        page += '<button style="float: left; margin-left: 10px" id="subbutton" onclick="bookedTickets('+(lowerlimit-10)+','+(10)+')"> PREVIOUS PAGE</button>';
    }
    page += '<button style="float: right;margin-right: 10px" id="subbutton" onclick="bookedTickets('+(lowerlimit+10)+','+(10)+')"> NEXT PAGE </button>';
    page +='</span>'
    document.getElementById("bookedtickets").innerHTML = page;
    // document.getElementById("bookedtickets").innerHTML += page;

    console.log("TO HIDE : length "+hide.length)
    for(var i =0;i<hide.length;i++){
        var e = document.getElementsByName(hide[i]);
        e[0].style.display="none";
        e[1].style.display="none";
        console.log("hidden "+ hide[i])
    }
}

function CancelServletCall(passid) {
    console.log("INSIDE AJAX CALL "+passid);
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if(xhr.status == 200 && xhr.readyState==4){
            // alert(xhr.responseText);
            if(xhr.responseText=="CANCELLED"){
                console.log("Cancelled");
                document.getElementById(passid).innerHTML = "CANCELLED";
                document.getElementsByName(passid)[0].innerHTML = "CANCELLED SUCCESSFULLY";
                document.getElementsByName(passid)[0].disabled = true;
            }
            else{
                console.log(xhr.responseText);
            }
        }
    }
    xhr.open("POST","cancelseat",true);
    xhr.setRequestHeader("content-type","application/x-www-form-urlencoded");
    xhr.send("passengerid="+passid);
}

function cancelSeat(el) {
    var passid = document.getElementById("passid").value = el.value;
    console.log("CLICK MADE TO CANCEL " + passid);
    CancelServletCall(passid);
}

function changeCSS(cssFile, cssLinkIndex) {

    var oldlink = document.getElementsByTagName("link").item(cssLinkIndex);

    var newlink = document.createElement("link");
    newlink.setAttribute("rel", "stylesheet");
    newlink.setAttribute("type", "text/css");
    newlink.setAttribute("href", cssFile);

    document.getElementsByTagName("head").item(0).replaceChild(newlink, oldlink);
}