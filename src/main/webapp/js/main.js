
var display = new Display();

var dateHelper = new DateHelper();
var currentTab = null;

var TRANS_URI		= "transactions/";
var REPORTS_URI		= "reports/";
var CATEGORIES_URI	= "categories/";

var url = "";

var undoinnerHTML = "";
var openItemId = null;

/**
 * Called when the main page loads
 */
function doOnLoad() {    
    display.init();
    doSearch();
    renderTab(0);
}

/**
 * Builds a search query String and loads search results into list div. 
 */
function doSearch() {
    openItemId = null;
    var searchValue = document.getElementById("search_value").value;
    if (searchValue.length > 0) {
    	if (endsWith(searchValue, "grouped")) {
    		searchValue = searchValue.substring(0,searchValue.indexOf("grouped"));
    		url = TRANS_URI + "?start=" + dateHelper.getYearStart() + "&end=" + dateHelper.getYearEnd() + "&category=" + searchValue +  "&groupby=month";    	
    		display.load(url, display.MONTHLY_XSL, display.SEARCH_LIST);
    	} else { 
    		url = TRANS_URI + "?" + searchValue;
    	    display.load(url, display.LIST_XSL, display.SEARCH_LIST);
    	}
    } else if (searchValue.length == 0) {
       url = TRANS_URI + dateHelper.getCurrentDateSlashes();
       display.load(url, display.LIST_XSL, display.SEARCH_LIST);
    }
}

function endsWith(str, suffix) {
    return str.indexOf(suffix, str.length - suffix.length) !== -1;
}

/**
 * Moves forward one transaction period.
 */
function forward() {
    if (dateHelper.getDay(dateHelper.movingDate) == 1) {
      dateHelper.movingDate.setDate(15);
    } else { 
      dateHelper.movingDate.setDate(1);
      dateHelper.movingDate.setMonth(dateHelper.movingDate.getMonth() + 1);
    }
    goTo(dateHelper.getMovingDateSlashes());
    renderTab(currentTab);
}

/**
 * Moves back one transaction period.
 */
function back() {
    if (dateHelper.getDay(dateHelper.movingDate) == 15) {
      dateHelper.movingDate.setDate(1);
    } else { 
      dateHelper.movingDate.setDate(15);
      dateHelper.movingDate.setMonth(dateHelper.movingDate.getMonth() - 1);
    }
    goTo(dateHelper.getMovingDateSlashes());
    renderTab(currentTab);
}

/**
 * Moves the list of search results to the given date.
 */
function goTo(dateStr) {
    url = TRANS_URI + dateStr;    
    display.load(url, display.LIST_XSL, display.SEARCH_LIST);
}

/**
 * Renders a tab by tabindex.
 */
function renderTab(tabindex) {
    var url = "";
    var xsl = "";
    switch(tabindex) {
        case 0:
             url = TRANS_URI + dateHelper.getMovingDateSlashes();
             xsl = display.SUM_XSL;
            break;
        case 1:
             url = REPORTS_URI + "?" + getStartEndDates();
            xsl = display.TOTAL_XSL_FIX;        
            break;
        case 2:
            url = REPORTS_URI + "?" + getStartEndDates();
           xsl = display.TOTAL_XSL;        
           break;
        case 3:
            url = CATEGORIES_URI + "?moving=" + dateHelper.getMoving();
            xsl = display.ADD_XSL;
            break;
    }

    display.load(url, xsl, display.TAB_CONTENTS);
    setTabStyle("tab-selected", document.getElementById('tab' + tabindex));
    
    currentTab = tabindex;
}

function searchByMoving(category) {
	openItemId = null;
    url = TRANS_URI + "?" + getStartEndDates();
    url += "&category=" + category;
    display.load(url, display.LIST_XSL, display.SEARCH_LIST);
}

function searchYears(category) {
	openItemId = null;
    url = TRANS_URI + "?" + getStartEndYears();
    url += "&category=" + category;
    display.load(url, display.LIST_XSL, display.SEARCH_LIST);
}

function getStartEndDates() {
    return "start=" + dateHelper.getYearStart() +  "&end=" + dateHelper.getMoving();
}

function getStartEndYears() {
	if (document.getElementById('start-date') == null) {
	    return "start=" + dateHelper.getYearStartRoll(1) +  "&end=" + dateHelper.getMoving();
	}
    return "start=" + document.getElementById('start-date').value +  "&end=" + document.getElementById('end-date').value;
}

/**
 * Gets one item by ID from the /transactions service, uses the detail xsl stylesheet 
 * to generate the detail form using the item xml as input source. 
 */
function selectItem(source) {
    if (openItemId != null && openItemId != source.id) {
        document.getElementById(openItemId).innerHTML = undoinnerHTML;
        openItemId = null;
    }
    setStyle("tb-row-selected", source);
}

/**
 * Opens a selected row in the list for editing. 
 */
function editItem(source) {
	undoinnerHTML = source.innerHTML;
	openItemId = source.id;
    display.load(TRANS_URI + source.id, display.EDIT_XSL, source.id);
}

/**
 * Sends a request to Delete one item by ID. 
 */
function deleteItem(source) {
    currentItemID = source.parentNode.parentNode.id;
    if (confirm("Are you sure you want to delete this item?")) {
      var webRequest = new WebRequest(goToURL, renderCurrentTab);
      var url = TRANS_URI + currentItemID;
      webRequest.sendRequest(url, webRequest.DELETE);    
    }
}

/**
 * Saves the items in the add form and POSTs new values to the /transactions service.
 */
function doAdd() {
    var webRequest = new WebRequest(goToURL);
    webRequest.sendRequest(TRANS_URI, webRequest.POST, getItemsXML());    
    renderTab(3);
}

function renderCurrentTab() {
	renderTab(currentTab);
}

// refreshes the list, re-loads the last search (url)
function goToURL() {
    display.load(url, display.LIST_XSL, display.SEARCH_LIST);
}

/**
 * Saves the item in the detail form and POSTs new values to the /transactions service.
 */
function doSave(itemID) {
    var webRequest = new WebRequest(goToURL, renderCurrentTab);
    webRequest.sendRequest(TRANS_URI, webRequest.PUT, getItemXML(itemID));
}

/**
 * Generates XML tags representing multiple entries to add.
 */
function getItemsXML() {
    var elements = document.forms[0].elements;
    var id = null;
    var xml = '<rqst><entry>';
    for (var i =0; i < elements.length; i++) {
        if (elements[i].type != 'button') {
            id = elements[i].id;
            xml += '<' + id + '>' + elements[i].value + '</' + id + '>';
            if (id == 'date') {
              xml += '</entry>';
              if (elements[i+1].type != 'button') xml += '<entry>';
            }
        } else {
            break;
        }
    }
    xml += '</rqst>';
    return xml;
}

/**
 * Generates XML tags from the fields in the detail form. 
 */
function getItemXML(itemID) {
    var xml = "<rqst><entry>";

	var date = document.getElementById("date").value;    
	var category = document.getElementById("category").value;    
	var amount = document.getElementById("amount").value;    
	var description = document.getElementById("description").value;    

    xml += "<id>" + itemID + "</id>";
	xml += "<date>" + date + "</date>";
	xml += "<category>" + category + "</category>";
	xml += "<description>" + description + "</description>";
	xml += "<amount>" + amount + "</amount>";

    xml += "</entry></rqst>";
    return xml;
}

var lastSet = null;
var lastStyle = null;
var lastTabStyle = null;
var lastTabSet = null;

/**
 * Toggles the row style for the list of search results (onmouseover).
 */
function setStyle(style, element) {
    if (lastSet != null) {
        lastSet.className = lastStyle;
    }
    lastSet = element;
    lastStyle = element.className;

    element.className = style;    
}

/**
 * Toggles the tab style: selected or not
 */
function setTabStyle(style, element) {
    if (lastTabSet != null) {
        lastTabSet.className = 'tab';
    }
    lastTabSet = element;

    element.className = style;
}

// used to highlight a tab onmouseover
function highlight(element, style) {
    if (element.className != style) {
        lastTabStyle = element.className;
        element.className = style;
    }
}

// used to restore a tab onmouseout
function restore(element) {
	if (element.className != 'tab-selected') {
	    element.className = lastTabStyle;
	}
}

/**
 * Checks for escape and enter keys in a form field. Calls 'func'. 
 */
function checkkey(itemId, event) {
    var key = null;
    if (event) {
        if (window.ActiveXObject) {
            key = event.keyCode;
        } else {
            key = event.which;
        }
        switch (key) {
        	case 0: // esc key
                document.getElementById(itemId).innerHTML = undoinnerHTML;        
        	    break;
            case 13:
                doSave(itemId);
                break;        	
        }
    }
}

/**
 * Checks for enter key in a form field. Calls 'func'. 
 */
function kpress(element, event, func) {
    var key = null;
    if (event) {
        if (window.ActiveXObject) {
            key = event.keyCode;
        } else {
            key = event.which;
        }
        if (key == 13) { // enter key
            func.call(this);
        }
    }
}



















