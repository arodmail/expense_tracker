/**
 * Cross-browser object to evaluate XPath expressions against a DOM Document. 
 */
function MyXPathEvaluator() {

    this.TYPE_ANY = 9;
    
    this.evaluate=function(expression, domDoc) {
	    var nodes = null;
	    try {
	        if (document.evaluate) {
                var _xpe = new XPathEvaluator();
                var xpr = _xpe.createNSResolver(domDoc);
                nodes = _xpe.evaluate(expression, domDoc, xpr, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
            } else {
	            domDoc.setProperty("SelectionLanguage", "XPath");
		        nodes = domDoc.documentElement.selectNodes(expression);
	 	    }
	    } catch (e) {
	        this.onError.call(e);
	    }
	    return nodes;
    };

    this.getNodeValue=function(xPathExpression, xmlDom) {

       var nodeList = this.evaluate(xPathExpression, xmlDom);

       if (window.ActiveXObject) {
            var node = nodeList.nextNode(); // assume a single-node match
            return node.text;
       } else {
	       for(var i = 0; i < nodeList.snapshotLength; i++) {
	           var node = nodeList.snapshotItem(i);
	           return node.textContent;
	       }
       }
    };

    this.onError=function(e) {
        alert("XPath error: " + e);        
    };


}

MyXPathEvaluator.prototype = new MyXPathEvaluator();