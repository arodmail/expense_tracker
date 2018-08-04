<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="html"/>

  <xsl:template match="/rsp/entry">

      <xsl:apply-templates select="date"/>
      <xsl:apply-templates select="category"/>
      <xsl:apply-templates select="description"/>
      <xsl:apply-templates select="amount"/>

  </xsl:template>

  <xsl:template match="date">
    <xsl:variable name="attrVal" select="."/>
    <xsl:variable name="id" select="../@id"/>
    <div class="tb-cell">
      <input type="text" size="10" id="date" value="{$attrVal}" onkeypress="javascript:checkkey('{$id}', event);"/>
    </div>
  </xsl:template>

  <xsl:template match="category">
    <xsl:variable name="attrVal" select="."/>
    <xsl:variable name="id" select="../@id"/>
    <div class="tb-cell">
      <input type="text" size="20" id="category" value="{$attrVal}" onkeypress="javascript:checkkey('{$id}', event);"/>
    </div>
  </xsl:template>

  <xsl:template match="description">
    <xsl:variable name="attrVal" select="."/>
    <xsl:variable name="id" select="../@id"/>
    <div class="tb-cell">
    <input type="text" size="30" id="description" value="{$attrVal}" onkeypress="javascript:checkkey('{$id}', event);"/>
    </div>
  </xsl:template>

  <xsl:template match="amount">
    <xsl:variable name="attrVal" select="."/>
    <xsl:variable name="id" select="../@id"/>
    <div class="tb-cell-right">
    <input class="currency" type="text" size="10" id="amount" value="{$attrVal}" onkeypress="javascript:checkkey('{$id}', event);"/>&#160;
      <img onclick="deleteItem(this);" src="images/tiny_icon_x.png"/>
    </div>
  </xsl:template>

</xsl:stylesheet>
