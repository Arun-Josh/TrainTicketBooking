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
    sessionStorage.setItem("traindetails",traindetails);
    var page ='<form id="form"class="box">'+
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

    var trainsJSON = JSON.parse(sessionStorage.getItem("traindetails"));
    var seats= "";

    // for(seat in trains[train]["seats"]){
    //     var seatcount = trains[train]["seats"][seat]["seatcount"];
    //     if(seatcount<=0){
    //         seatcount = "WL "+(Math.abs(seatcount)+1);
    //     }
    //     page+= ' &nbsp; ' +'<label>'+trains[train]["seats"][seat]["seattype"] +'</label>'+ ' &nbsp; ' + '<a> '+ seatcount +'</a>';
    // }

    for(train in trainsJSON){
        if(trainsJSON[train]["trainid"]==trainchosen){
            for(seat in trainsJSON[train]["seats"]){
                seats+='<input name="seattype" type="radio" value="'+ trainsJSON[train]["seats"][seat]["seattype"] +'"/><label>'+trainsJSON[train]["seats"][seat]["seattype"]+'</label>&emsp;';
            }
        }
    }

    // passcount = 0;
    var passinfopage = ' <div id="passform" style="width: 90%" class="box" >\n' +
        '   <h3 style="color: WHITE">SELECT SEAT TYPE</h3>' +
        seats
        +
        '<br>' +
        '   <h1 style="color: white" >Enter Passenger Details</h1>' +
        '<center><a id ="hidden" style="color: orange">ONLY SIX PASSENGERS CAN TRAVEL PER TICKET</a></center>' +
        '<center><a id="missingdetails" style="color: orange;display: none;">Enter the necessary details</a></center>' +

        '<input id="addbtn" type="button" onclick="addpassenger()" value="Add Passenger"/>' +
        '                        <div id="addpassenger"></div>\n' +
        '<br>' +
        '<button id="subbutton" onclick="paymentPage()">SUBMIT</button>' +
        '            </div>\n';
    // document.getElementById("form").style.width = "90%";
    document.getElementById("searchresult").innerHTML = passinfopage;
    addpassenger();
    }

    var passcount = 0;

    function addpassenger() {
        changeCSS("css/ticketpage.css",0);
        passcount++;
        if(passcount>6){
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
            // '          <th>Gender</th>\n' +
            '      </tr>' +
            // '<table align="center" >
                    '<tr>' +
                        '<td><input style="color: #191919" type="text" name="pname" placeholder="Name of Traveller ' + passcount +' " maxlength="25" /></td> &emsp;' +
                        '<td><input style="color: #191919" type="text" name="page" placeholder="Age of Traveller '+ passcount+'" maxlength="2" pattern="[0-9]{1,2}"/></td>' +
                    '</tr>' +
            '</table>' +
            '<br>' +
            '';
        document.getElementById("addpassenger").appendChild(passenger);
    }

    function paymentPage() {
        changeCSS("css/style.css",0);
        var seattypes = document.getElementsByName("seattype");
        var seatchosen;

        for(var i=0;i<seattypes.length;i++){
            if(seattypes[i].checked==true){
                seatchosen=seattypes[i].value;
                sessionStorage.setItem("seatchosen",seatchosen);
                console.log("seatchosen "+seatchosen)
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
            url+= "gender"+i+"="+"NOT SPECIFIED"+"&";
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
        var mode = '<div id="paymentform" class="box" action="PaymentGateWay" method="POST">\n' +
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
            url+="&=deststopno"+sessionStorage.getItem("dststopno");

        console.log("url :"+url);
            xhr.open("GET",url,true);
            xhr.send();
    }

    function ticketInfo() {
        var ticket = sessionStorage.getItem("ticket");
        ticket = JSON.parse(ticket);
        document.getElementById("paymentform").style.display="none";
        var ticketPage='<div style="width: 90%" class="box">\n' +
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
    // var from = document.getElementById("from").value;
    // var to   = document.getElementById("to").value;
    // var date = document.getElementById("date").value;
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

function pnrCheck() {
    var xhr = new XMLHttpRequest();
    var url = 'pnrcheck?pnr='+document.getElementById("pnr").value;
    xhr.onreadystatechange = function (ev) {
        if(xhr.status==200 && xhr.readyState==4){
            console.log(xhr.responseText);
            var ticket = xhr.responseText;
            sessionStorage.setItem("ticket",ticket);
            try{
                console.log("in pnr check "+JSON.parse(ticket))
            }catch (e) {
                console.log("pnr not found");
                document.getElementById("nopnr").style.display = "block";
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

function bookedTickets() {
    var xhr = new XMLHttpRequest();
    var url = "bookedtickets";
    xhr.onreadystatechange = function () {
        if(xhr.status=200 && xhr.readyState==4){
            var tickets = xhr.responseText;
            sessionStorage.setItem("tickets",tickets);
            console.log("tickets json pared = "+JSON.parse(tickets));
            console.log("tickets json no parse = "+tickets);
            bookedTicketsPage();
        }
    }
    console.log("inside booked Tickets "+url)
    xhr.open("GET",url,true);
    xhr.send();
}

function bookedTicketsPage() {
    document.getElementById("searchform").style.display = "none";
    document.getElementById("bhistory").style.display = "none";
    changeCSS('css/ticketpage.css', 0);
    var tickets = sessionStorage.getItem("tickets");

    var page = '<div id="tickform" class="box" >';

    if (Object.keys(tickets).length==0) {
        console.log("JSON IS empty");
        page += '    <h1> NO TICKETS BOOKED !</h1>';
        page += '</div>'
        document.getElementById("bookedtickets").innerHTML = page;
    }

    tickets = JSON.parse(tickets);


    for(ticket in tickets){
        console.log("Ticket "+ticket);
        console.log(tickets[ticket]["passengerid"]);
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
            '            <th>CANCELLATION</th>\n' +
            '        </tr>';
            var p = 1;
            var tpnr = tickets[ticket]["pnr"];
            do{

                if(tickets[ticket]["pnr"]!=tpnr){
                    break;
                }
                console.log(ticket);
                var pname = tickets[ticket]["passenger"];
                var gender = tickets[ticket]["gender"];
                var age = tickets[ticket]["gender"];
                var seatno = tickets[ticket]["seatno"];
                var passid = tickets[ticket]["passengerid"];
                var status = tickets[ticket]["ticketstatus"];
                var cancelbtn = "CANCEL THIS TICKET";
                if(status=="CANCELLED"){
                    cancelbtn = "CANCELLED SUCCESSFULLY";
                }
                var btnstatus = "";

                if(status=="CANCELLED") {
                    btnstatus = '"disabled"';
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
                    '            <td id="'+passid+'" > '+status+' </td>\n' +
                    '            <td>\n' +
                    '                <input id="passid" name="passengerid" type="hidden" value="">\n' +
                    '                <button id="canbtn" name="<%=passid%>" onclick="refresh(this)" type="button" '+ btnstatus +'value="'+passid+'">'+cancelbtn+'</button>\n' +
                    '            </td>\n' +
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
    }
    page += '</div>'
    document.getElementById("bookedtickets").innerHTML = page;
}

function ajaxCall(passid) {
    console.log("INSIDE AJAX CALL "+passid);
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if(xhr.status == 200 && xhr.readyState==4){
            // alert(xhr.responseText);
            if(xhr.responseText=="CANCELLED"){
                document.getElementById(passid).innerHTML = "CANCELLED";
                document.getElementsByName(passid)[0].innerHTML = "CANCELLED SUCCESSFULLY";
                document.getElementsByName(passid)[0].disabled = true;
            }
        }
    }
    xhr.open("POST","cancelseat",true);
    xhr.setRequestHeader("content-type","application/x-www-form-urlencoded");
    xhr.send("passengerid="+passid);
}

function refresh(el) {
    var passid = document.getElementById("passid").value = el.value;
    console.log("CLICK MADE TO CANCEL " + passid);
    // var form = document.getElementById("tickform");


    // $.confirm({
    //     // theme:'supervan',
    //     useBootstrap : false,
    //     closeIcon: true,
    //     icon: 'glyphicon glyphicon-heart',
    //     columnClass: 'small',
    //     title: 'Do you sure want to cancel the Ticket ?',
    //     content: '',
    //     autoClose: 'NO|10000',
    //     buttons: {
    //         YES: {
    //             text: 'YES',
    //             action: function () {
                    ajaxCall(passid);
    //                 $.alert('Ticket Successfully Cancelled');
    //             }
    //         },
    //         NO:{
    //             text:'NO',
    //             action : function () {
    //                 $.alert('Ticket Not Cancelled');
    //             }
    //         }
    //     }
    // });
    // form.submit();
}

function changeCSS(cssFile, cssLinkIndex) {

    var oldlink = document.getElementsByTagName("link").item(cssLinkIndex);

    var newlink = document.createElement("link");
    newlink.setAttribute("rel", "stylesheet");
    newlink.setAttribute("type", "text/css");
    newlink.setAttribute("href", cssFile);

    document.getElementsByTagName("head").item(0).replaceChild(newlink, oldlink);
}