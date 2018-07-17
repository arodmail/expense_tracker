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
		    <xsl:variable name="average" select="rsp/@average"/>
            <td class="bot-row-left">&#160;
            	Monthly Average: <xsl:value-of select="rsp/@average"/>
            </td>
          	<td class="bot-row-center">
				&#160;
          	</td>
		    <xsl:variable name="total" select="rsp/@total"/>
          	<td class="bot-row-right">&#160;
          		<xsl:if test="rsp/@total != '0'">
          			Total: <xsl:value-of select="format-number($total, '$###,###,###.00')"/>
          		</xsl:if>
          	</td>
          </tr>
      </table>

  </xsl:template>

  <xsl:template match="rsp">
        <table class="search-results-top" cellpadding="5px" cellspacing="0">
          <tr>
		    <td class="tb-head">
		      Month
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
    <tr id="{$id}" class="{$class}" onClick="javascript:selectItem(this);">
      <xsl:apply-templates select="month"/>
      <xsl:apply-templates select="amount"/>
    </tr>
  </xsl:template>
  
  <xsl:template match="amount">
    <xsl:variable name="amt" select="."/>
    <td class="tb-cell-right">
    	<xsl:value-of select="format-number($amt, '$###,###,###.00')"/></td>
  </xsl:template>

  <xsl:template match="month">
    <td class="tb-cell">
    	&#160;&#160;<xsl:value-of select="."/>
    </td>
  </xsl:template>
        
</xsl:stylesheet>