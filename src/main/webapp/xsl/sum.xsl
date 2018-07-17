<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="html"/>

  <xsl:template match="/">

	<div class="underline">Current</div>

    <xsl:variable name="previous" select="rsp/@previous"/>
    <xsl:variable name="credits" select="rsp/@credits"/>
    <xsl:variable name="debits" select="rsp/@debits"/>
    <xsl:variable name="balance" select="rsp/@ytd-balance"/>

		<table cellpadding="10px" cellspacing="0" width="30%" align="center">
			<tr>
				<td class="tb-cell-right">Begin:</td>
				<td class="tb-cell-right"><font color="#000000" style="font-weight:normal">
					<xsl:value-of select="format-number($previous, '$###,###,###.00')"/>
					</font>
				</td>
				<td>&#160;</td>
			</tr>
			<tr class="tb-row-odd">
				<td class="tb-cell-right">Credits:</td>
				<td class="tb-cell-right"><font color="#228b22" style="font-weight:normal">
					<xsl:value-of select="format-number($credits, '+$###,###,###.00')"/>
					</font>
				</td>
				<td>&#160;</td>
			</tr>
			<tr>
				<td class="tb-cell-right">Debits:</td>
				<td class="tb-cell-right"><font color="#800000" style="font-weight:normal">
				<xsl:value-of select="format-number($debits, '-$###,###,###.00')"/>
					</font>
				</td>
			</tr>
			<tr class="tb-row-odd">
				<td class="tb-cell-right">YTD Balance:</td>
				<td class="tb-cell-right"><font color="#000000" style="font-weight:normal">
				<xsl:value-of select="format-number($balance, '$###,###,###.00')"/>
					</font>
				</td>
				<td>&#160;</td>
			</tr>
		</table>

	</xsl:template>
	
</xsl:stylesheet>