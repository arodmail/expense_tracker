<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="html"/>

  <xsl:template match="/">
     <xsl:apply-templates select="rsp"/>   

       <div id="table-rows">
           <table class="search-results" cellpadding="5px" cellspacing="0" align="center">
             <xsl:apply-templates select="rsp/entry"/>
           </table>
       </div>
       
      <table class="search-results-bot" cellpadding="10px" cellspacing="0">
          <tr>
		    <xsl:variable name="total" select="rsp/@total"/>
          	<td class="bot-row-left">&#160;
          		<xsl:if test="rsp/@total != '0'">
          			<xsl:value-of select="format-number($total, '$###,###,###.00')"/>
          		</xsl:if>
          	</td>
          	<td class="bot-row-center">
          	  <a href="javascript:back();">&lt;&lt; Prev</a>&#160;
              <a href="javascript:forward();">Next &gt;&gt;</a>          	
          	</td>
            <td class="bot-row-right">&#160;
            	Displaying results 1-<xsl:value-of select="rsp/@count"/> of <xsl:value-of select="rsp/@count"/>
            </td>

          </tr>
      </table>
  </xsl:template>

  <xsl:template match="rsp">
        <table class="search-results-top" cellpadding="5px" cellspacing="0">
          <tr>
		    <td class="tb-head">
		      Date
		    </td>
		    <td class="tb-head">
		      Category
		    </td>
		    <td class="tb-head">
		      Description
		    </td>
		    <td class="tb-head-right">
		      Amount
		    </td>
          </tr>
        </table>
  </xsl:template>

  <xsl:template match="rsp/entry">
    <xsl:variable name="id" select="@id"/>
    <xsl:variable name="class">
      <xsl:choose>
        <xsl:when test="(position() mod 2) = 0">
          tb-row
        </xsl:when>
        <xsl:otherwise>
          tb-row-odd
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <tr id="{$id}" class="{$class}" onClick="javascript:selectItem(this)" ondblclick="javascript:editItem(this);">
      <xsl:apply-templates select="date"/>
      <xsl:apply-templates select="category"/>
      <xsl:apply-templates select="description"/>
      <xsl:apply-templates select="amount"/>
    </tr>
  </xsl:template>
  
  <xsl:template match="amount">
    <xsl:variable name="amt" select="."/>
    <td class="tb-cell-right">
    	<xsl:value-of select="format-number($amt, '$###,###,###.00')"/>&#160;
    	<img onclick="deleteItem(this);" src="images/tiny_icon_x.png" title="Delete"/>
    </td>
  </xsl:template>

  <xsl:template match="date">
    <td class="tb-cell">
    	&#160;&#160;<xsl:value-of select="."/>
    </td>
  </xsl:template>

  <xsl:template match="category | description">
    <td class="tb-cell">
    	<xsl:value-of select="."/>
    </td>
  </xsl:template>
        
</xsl:stylesheet>