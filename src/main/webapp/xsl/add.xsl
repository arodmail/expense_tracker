<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="html"/>

  <xsl:template match="/rsp">

    <form id="detailform" name="sourceForm">
          <table border="0" width="50%" align="center">
            <tr>
              <td>
		       <table width="100%" cellpadding="5px" cellspacing="0">
		         <tr>
			    <td class="tb-head-left">
			      &#160;&#160;&#160;Amount
			    </td>
			    <td class="tb-head-left">
			      Description
			    </td>
			    <td class="tb-head-right">
			      Category
			    </td>
			    <td class="tb-head-center">
			      Date
			    </td>
		         </tr>
		       </table>
		          <table width="100%" cellpadding="5px" cellspacing="0">
					<xsl:call-template name="add-row"/>
					<xsl:call-template name="add-row-odd"/>
					<xsl:call-template name="add-row"/>
					<xsl:call-template name="add-row-odd"/>
					<xsl:call-template name="add-row"/>
				</table>
             </td>
             <td align="right" colspan="3">
                <input type="button" value="Save All" onClick="javascript:doAdd();"/>
             </td>                  
          </tr>
        </table>
    </form>
  
  </xsl:template>

  <xsl:template name="add-row">
    <tr class="tb-row">
    	<xsl:call-template name="tb-cells"/>
    </tr>
  </xsl:template>

  <xsl:template name="add-row-odd">
    <tr class="tb-row-odd">
    	<xsl:call-template name="tb-cells"/>
    </tr>
  </xsl:template>

  <xsl:template name="tb-cells">
   	<td class="tb-cell-right">&#160;</td>
   	<td class="tb-cell-right">$&#160;<input type="text" size="10" id="amount"/></td>
   	<td class="tb-cell-right"><input type="text" size="30" id="description"/></td>
   	<td class="tb-cell-right"><select id="category"><xsl:apply-templates select="category/name"/></select></td>
   	<td class="tb-cell-right"><select id="date"><xsl:apply-templates select="select/option"/></select></td>
   	<td class="tb-cell-right">&#160;</td>
  </xsl:template>
  
  <xsl:template match="category/name">
    <xsl:variable name="attrVal" select="@name"/>
  	<option value="{$attrVal}"><xsl:value-of select="."/></option>
  </xsl:template>

  <xsl:template match="select/option">
	<xsl:copy-of select="."/>
  </xsl:template>
  
</xsl:stylesheet>
  
        