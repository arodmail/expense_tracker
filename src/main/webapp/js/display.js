/**
 * Cross-browser object to control screen transitions. 
 */
function Display() {

    // div IDs
    this.HEADER_DIV    = "header";
    this.FOOTER_DIV    = "footer";
    this.SEARCH_DIV    = "search";
    this.SEARCH_LIST   = "list";
    this.DETAIL_DIV    = "detail";
    this.TAB_CONTENTS  = "tab-contents";
    this.EDIT_DIV	   = "edit";
        
    // to hold the xsl stylesheet paths
    this.HEAD_XSL    = "xsl/header.xsl";
    this.FOOT_XSL    = "xsl/footer.xsl";
    this.SEARCH_XSL  = "xsl/search.xsl";
    this.LIST_XSL    = "xsl/list.xsl";
    this.EDIT_XSL    = "xsl/edit.xsl";
    this.ADD_XSL     = "xsl/add.xsl";
    this.TABS_XSL    = "xsl/tabs.xsl";
    this.TOTAL_XSL   = "xsl/totals.xsl";
    this.SUM_XSL     = "xsl/sum.xsl";
    this.EDIT_XSL    = "xsl/edit.xsl";
    this.MONTHLY_XSL = "xsl/monthly.xsl";
    this.TOTAL_XSL_FIX   = "xsl/totalsfix.xsl";

    /**
     * Initializes the UI by loading default content into default panels. 
     */                
    this.init=function() {
        this.load(this.FOOT_XSL, this.FOOT_XSL, this.FOOTER_DIV);
        this.load(this.SEARCH_XSL, this.SEARCH_XSL, this.SEARCH_DIV);
        this.load(this.TABS_XSL, this.TABS_XSL, this.DETAIL_DIV);
    };

    /**
     * Loads content into a div using the given xml source and xsl to transform.  
     */
    this.load=function(xmlSource, xslSource, elementID) {
        var transformer = new Transformer();
        transformer.transform(xmlSource, xslSource, elementID);
    };
    
}

Display.prototype = new Display();
