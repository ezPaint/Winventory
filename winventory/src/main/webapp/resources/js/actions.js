'use strict';

var itemsHTML = "<div class=\"main\"><div class=\"panel-heading\"><h5>Items in use<h5></div>" +
    "<table class=\"table\"><tr><td><b>ID</b></td><td><b>Type</b></td><td><b>Info</b></td><td><b>Cost</b></td><td><b>DOP</b>        </td>" +
    "<td><b>Condition</b></td><td><b>Owner</b></td></tr>" +
    "<tr><td>L01</td><td>Laptop</td><td>MacbookPro 13 inch</td><td>$1,600</td><td>6/12/2013</td>" +
    "<td>Fair</td><td>Jack Smith</td></tr>" +
    "<tr><td>M03</td><td>Mouse</td><td>Apple Magic Mouse</td><td>$70</td><td>3/14/15</td>" +
    "<td>Good</td><td>Jack Smith</td></tr>" +
    "<tr><td>L014</td><td>Laptop</td><td>MacbookPro 15 inch</td><td>$2,100</td><td>4/28/12</td>" +
    "<td>Good</td><td>Larry David</td></tr>" +
    "<tr><td>HDM01</td><td>HD Monitor</td><td>Resolution 1080p</td><td>$400</td><td>12/4/10</td>" +
    "<td>Slight Wear</td><td>Melissa Jackson</td></table>" +
    "<div class=\"panel-heading\"><h5>Items in storage</h5></div>" +
    "<table class=\"table\"><tr><td><b>ID</b></td><td><b>Type</b></td><td><b>Info</b></td><td><b>Cost</b></td><td><b>DOP</b>        </td><td><b>Condition</td><td><b>Owner</b></td></tr>" +
    "<tr><td>L02</td><td>Laptop</td><td>MacbookPro 13 inch</td><td>$1,600</td><td>2/2/2011</td>" +
    "<td>Poor</td><td>Matt Smack</td></tr>" +
    "<tr><td>D2</td><td>Desk</td><td>Dimensions: 200 x 200</td><td>$150</td><td>7/16/2001</td>" +
    "<td>Slight Wear</td><td>Barry O'Donald</td></tr></table></div>";

var addItemHTML = "<div class=\"main\"><h2>Add Item to the Winventory</h2>" +
                    "<form>" +
                    "<div class=\"form-horizontal\">" +
                        "<label>ID </label>" +
                        "<input class=\"form-control\" placeholder=\"L014\"><br>" +
                        "<label>Type </label>" +
                        "<input type=\"text\" class=\"form-control\"placeholder=\"Laptop\"><br>" +
                        "<label>Cost </label>" +
                        "<input type=\"text\" class=\"form-control\" placeholder=\"$70\"><br>" +
                        "<label>DOP </label>" +
                        "<input type=\"date\" class=\"form-control\" placeholder=\"DOP\"><br>" +
                        "<label>Condition </label>" +
                        "<input type=\"text\" class=\"form-control\" placeholder=\"Fair\"><br>" +
                        "<label>Owner </label>" +
                        "<input type=\"text\" class=\"form-control\" placeholder=\"John Smith\"><br>" +
                    "</div>" +
                    "<button type=\"submit\" class=\"btn btn-default\">Add</button> " +
                    "<button type=\"reset\" class=\"btn btn-default\">Clear</button>" +
                    "</form></div>";

var membersHTML = "<div class=\"main\"><div class=\"panel-heading\">Members</div>" +
    "<table class=\"table\"><tr><td><b>Name</b></td><td><b>Team</b></td><td><b>Location</b></td><td><b>Items Owned</b></td>         </tr>" +
    "<tr><td>Jack Smith</td><td>SEACATS</td><td>1035 Shawnee Road</td><td>???</td></tr>" +
    "<tr><td>Larry David</td><td>SEACATS</td><td>1035 Shawnee Road</td><td>???</td></tr>" +
    "<tr><td>Barry Jackson</td><td>DHS</td><td>891 Fountain Circle </td><td>???</td></tr>" +
    "<tr><td>Melissa Jones</td><td>SEACATS</td><td>1035 Shawnee Road</td><td>???</td></tr>" +
    "<tr><td>Art Vanderbeld</td><td>DHS</td><td>891 Fountain Circle</td><td>???</td></tr>" +
    "<tr><td>Li Samsung</td><td>SEACATS</td><td>1035 Shawnee Road</td><td>???</td></tr>" +
    "<tr><td>Simon Computing</td><td>BIOTECH</td><td>1337 Coders St</td><td>???</td></tr>" +
    "<tr><td>Ronald Regan</td><td>BIOTECH</td><td>1337 Coders St</td><td>???</td></tr></table>";

var addMemberHTML = "<div class=\"main\"><h2>Add Member to the Winventory</h2>" +
                    "<form>" +
                    "<div class=\"form-horizontal\">" +
                        "<label>Name </label>" +
                        "<input class=\"form-control\" placeholder=\"Jerry Seinfeld\"><br>" +
                        "<label>Team </label>" +
                        "<input type=\"text\" class=\"form-control\"placeholder=\"SEACATS\"><br>" +
                        "<label>Location </label>" +
                        "<input type=\"text\" class=\"form-control\" placeholder=\"1035 Shawnee Road\"><br>" +
                    "</div>" +
                    "<button type=\"submit\" class=\"btn btn-default\">Add</button> " +
                    "<button type=\"reset\" class=\"btn btn-default\">Clear</button>" +
                    "</form></div>";

var softwareHTML = "<div class=\"main\"><div class=\"panel-heading\">Software</div>" +
    "<table class=\"table\"><tr><td><b>Name</b><td><b>Publisher</b></td><td><b>Version</b></td><td><b>Cost</b></td><td><b>DOP</b></td><td><b>Installed on</b></td></tr>" +
    "<tr><td>Word</td><td>Microsoft</td><td>2015</td><td>$100</td><td>1/1/2015</td><td>???</td></tr>" +
    "<tr><td>Excel</td><td>Microsoft</td><td>2015</td><td>$100</td><td>1/1/2015</td><td>???</td></tr>" +
    "<tr><td>PowerPoint</td><td>Microsoft</td><td>2015</td><td>$100</td><td>1/1/2015</td><td>???</td></tr>" +
    "<tr><td>Paint</td><td>Microsoft</td><td>2015</td><td>Free</td><td>9/1/2015</td><td>???</td></tr>" +
    "<tr><td>PhotoShop</td><td>Adobe</td><td>CS6</td><td>$500</td><td>5/7/2014</td><td>???</td></tr>" +
    "<tr><td>Eclipse</td><td>Google</td><td>2014</td><td>Free</td><td>1/30/2015</td><td>???</td></tr>" +
    "<tr><td>Chrome</td><td>Google</td><td>2012</td><td>Free</td><td>3/1/2012</td><td>???</td></tr></table></div>";

var addSoftwareHTML = "<div class=\"main\"><h2>Add Software to the Winventory</h2>" +
                    "<form>" +
                    "<div class=\"form-horizontal\">" +
                        "<label>Name </label>" +
                        "<input class=\"form-control\" placeholder=\"Word\"><br>" +
                        "<label>Publisher </label>" +
                        "<input type=\"text\" class=\"form-control\"placeholder=\"Microsoft\"><br>" +
                        "<label>Version </label>" +
                        "<input type=\"text\" class=\"form-control\" placeholder=\"2015\"><br>" +
                        "<label>Cost </label>" +
                        "<input type=\"text\" class=\"form-control\" placeholder=\"$100\"><br>" +
                        "<label>DOP </label>" +
                        "<input type=\"date\" class=\"form-control\" placeholder=\"\"><br>" +
                    "</div>" +
                    "<button type=\"submit\" class=\"btn btn-default\">Add</button> " +
                    "<button type=\"reset\" class=\"btn btn-default\">Clear</button>" +
                    "</form></div>";

function showItems() {
    document.getElementById("change").innerHTML = itemsHTML;
}

function addItem() {
    document.getElementById("change").innerHTML = addItemHTML;
}

function showMembers() {
    document.getElementById("change").innerHTML = membersHTML;
}


function addMember() {
    document.getElementById("change").innerHTML = addMemberHTML;
}

function showSoftware() {
    document.getElementById("change").innerHTML = softwareHTML;
}

function addSoftware() {
    document.getElementById("change").innerHTML = addSoftwareHTML;
}

/**
 * Validates that a numeric value is entered into a cost field.
 * @returns {Boolean}
 */
function validateCost() {
	var costA = document.forms["advSearchForm"]["minCost"].value;
	var costB = document.forms["advSearchForm"]["maxCost"].value;
	var regex = /^(\d+(\.\d+)?)$/;
	if ((!costA.match(regex) && costA != "")
		|| (!costB.match(regex) && costB != "")) {
		document.getElementById("costValidate").innerHTML = "Please enter a non-negative numeric value!";
		return false;
	} else {
		document.getElementById("costValidate").innerHTML = "";

	}
	return true;
}
/* 
 * Validates dates by checking that the format is correct: YYYY-MM-DD
 * Validates dates by checking that the date is valid (no Feb 31st, for instance).
 * Pop-up boxes will occur if a date is invalid.
 * Checks for leap years (ex 2012-02-29 is valid, but 2013-02-29 is not)
 * PRECONDITIONS:  The submission form must have 'myform' as its id.
 * 				   The date's ids from the form must be 'purchasedDate' and 'expirationDate'
 */
function validateDates() {
    var pDate = document.forms["myform"]["purchasedDate"].value;
    var regex = /^(\d{4}-\d{2}-\d{2})$/;
    if (!pDate.match(regex)) {
        alert("Purchase Date must be in format YYYY-MM-DD");
        return false;
    } else {
         var pSplit = pDate.split("-");
         var pYear = pSplit[0];
         var pMonth = pSplit[1] - 1; 
         var pDay = pSplit[2];     
            
         //Number of days in each month where 0=January, 1=Feb, etc.
         var days = [31,28,31,30,31,30,31,31,30,31,30,31];
          
        //Check if leap year -- switch array if leap year
        if (pMonth === 1){
             if (pYear % 400 == 0){
            	 days[1] = 29;
             }
             else if (pYear % 100 == 0){
            	 days[1] = 28;
             } 
             else if (pYear % 4 == 0){
            	 days[1] = 29;
             }else{
            	 days[1] = 28;
             }
        }    
         
        if (days[pMonth] < pDay){ 
         alert(pDate + " is not a valid date.");
         return false;
        }
    
    }
        
    //Validate Expiration date
    var eDate = document.forms["myform"]["expirationDate"].value;
    if (!eDate.match(regex)) {
        alert("Expiration Date must be in format YYYY-MM-DD");
        return false;
    }else{
	
    	var eSplit = eDate.split("-");
        var eYear = eSplit[0];
        var eMonth = eSplit[1] - 1; 
        var eDay = eSplit[2];     
           
        //Number of days in each month where 0=January, 1=Feb, etc.
        var days = [31,28,31,30,31,30,31,31,30,31,30,31];
         
       //Check if leap year -- switch array if leap year
       if (eMonth === 1){
            if (eYear % 400 == 0){
                days[1] = 29;
            }
            else if (eYear % 100 == 0){
                days[1] = 28;
            } 
            else if (eYear % 4 == 0){
                days[1] = 29;
            }else{
                days[1] = 28;
            }
       }    
        
       if (days[eMonth] < pDay){ 
        alert(eDate + " is not a valid date.");
        return false;
       }
    }
    return true; 
}