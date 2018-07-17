/**
 * Cross-browser object to convert an XML DOM to XHTML through an XSL transform.
 */
function Transformer() {

	this.transform = function(xmlSource, xslSource, elementID) {

		xmlDOM = this.loadXML(xmlSource);
		xslDOM = this.loadXML(xslSource);

		if (window.ActiveXObject) {
			// IE
			xslOut = xmlDOM.transformNode(xslDOM);
			document.getElementById(elementID).innerHTML = "";
			document.getElementById(elementID).innerHTML = xslOut;

		} else {
			try {
				// Mozilla
				this.processor = new XSLTProcessor();
				this.processor.importStylesheet(xslDOM);

				var fragment = this.processor.transformToFragment(xmlDOM, document);
				document.getElementById(elementID).innerHTML = "";
				document.getElementById(elementID).appendChild(fragment);
			} catch (e) {
				alert(e);
			}
		}
	};

	this.loadXML = function(source) {
		var xmlDoc;
		if (window.ActiveXObject) {
			// IE
			xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		} else if (document.implementation
				&& document.implementation.createDocument) {
			// Mozilla
			xmlDoc = document.implementation.createDocument("", "", null);
		}
		try {
			xmlDoc.async = false;
			xmlDoc.load(source);
		} catch (e) {
			var xmlhttp = new window.XMLHttpRequest();
			xmlhttp.open("GET", source, false);
			xmlhttp.send(null);
			xmlDoc = xmlhttp.responseXML.documentElement;
		}
		return xmlDoc;
	};

}

Transformer.prototype = new Transformer();
