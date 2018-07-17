<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="html"/>

  <xsl:template match="/rsp">
	
    <xsl:variable name="id" select="entry/@id"/>
    <xsl:variable name="date" select="entry/date"/>
    <xsl:variable name="category" select="entry/category"/>
    <xsl:variable name="description" select="entry/description"/>
    <xsl:variable name="amount" select="entry/amount"/>
      <td class="tb-cell"><input type="text" size="10" id="date" value="{$date}" onkeypress="javascript:checkkey('{$id}', event);"/></td>
      <td class="tb-cell"><input type="text" size="20" id="category" value="{$category}" onkeypress="javascript:checkkey('{$id}', event);"/></td>
      <td class="tb-cell"><input type="text" size="30" id="description" value="{$description}" onkeypress="javascript:checkkey('{$id}', event);"/></td>
      <td class="tb-cell-right"><input class="currency" type="text" size="10" id="amount" value="{$amount}" onkeypress="javascript:checkkey('{$id}', event);"/>&#160;<img onclick="deleteItem(this);" src="images/tiny_icon_x.png"/></td>
  
  </xsl:template>

</xsl:stylesheet>
