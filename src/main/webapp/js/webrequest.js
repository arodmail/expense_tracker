/**
 * Cross-browser object to fetch an XML response from a server-side url. Calls 
 * the function specified by 'onload' when the response from the server is 
 * complete and passes the response to the function. 
 *
 * @param onload the name of a function to call. A callback. 
 */
function WebRequest(onload, onload2) {

    this.RESPONSE_COMPLETE = 4;
    var LOADING_DIV = "loading";

    // properties to toggle div visibility
    this.VISIBLE     = "visible";
    this.HIDDEN      = "hidden";
    
    // Common HTTP Status Codes
    this.OK     = "200";

    this.GET    = "GET";
    this.POST   = "POST";
    this.PUT    = "PUT";
    this.DELETE	= "DELETE";
    
    this.req = null;
    this.onload = onload;
    this.onload2 = onload2;
    this.url = null;
    
    this.sendRequest=function(url, method, payload) {
        this.url = url;        
        if (window.XMLHttpRequest) {
            this.req = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            this.req = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if (this.req) {
            try {
                var loader = this;
                this.req.onreadystatechange=function() {
                    loader.onReadyState.call(loader);
                };
                this.req.open(method, url, true);
                if (method == this.GET || method == this.DELETE) {
                    this.req.send(null);
                } else {
                    this.req.send(payload);
                }
            } catch (e) {
                this.onError.call(this);
            }
        }    
    };
    this.onReadyState=function() {
        document.getElementById(LOADING_DIV).style.visibility = this.VISIBLE;                    
        if (this.req.readyState == this.RESPONSE_COMPLETE) {
            if (this.req.status == this.OK) {
                document.getElementById(LOADING_DIV).style.visibility = this.HIDDEN;
                if (this.onload) {
                    this.onload.call(this);
                }
                if (this.onload2) {
                    this.onload2.call(this);                
                }
            } else {
                document.getElementById(LOADING_DIV).style.visibility = this.HIDDEN;
                this.onError.call(this);
                if (this.onload) {
                    this.onload.call(this);
                }
                if (this.onload2) {
                    this.onload2.call(this);                
                }
            }
        }
    };
    this.onError=function() {
        alert("Error fetching data. "
        + "\n\nurl: " + this.url
        + "\nreadyState: " + this.req.readyState
        + "\nstatus: " + this.req.status 
        + "\nheaders: " + this.req.getAllResponseHeaders());
    };
};

WebRequest.prototype = new WebRequest();
