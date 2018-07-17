<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="html"/>

  <xsl:template match="/">

    <div class="subtitle">Expense Tracker</div>
    
    <P/>

    <table class="search-filter-tab" cellpadding="15px">
        <tbody>
            <tr>
                <td class="search_box">Find Transactions:&#160;

	                <input id="search_value" value="" type="text" size="60" onkeypress="javascript:kpress(this, event, doSearch);"/>&#160;
               </td>
            </tr>
        </tbody>
    </table>

  </xsl:template>
 
</xsl:stylesheet>
        